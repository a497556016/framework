package com.framework.shiro.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.framework.bean.common.LoginUserInfo;
import com.framework.bean.vo.SysUserInfoVo;
import com.framework.constant.CommonConstant;
import com.framework.constant.CommonConstant.FileBusinessType;
import com.framework.model.SysAuthority;
import com.framework.model.SysCommonFileInfo;
import com.framework.model.SysResource;
import com.framework.model.SysUserInfo;
import com.framework.service.CommonService;
import com.framework.shiro.bean.Menu;
import com.framework.shiro.bean.Resource;
import com.framework.shiro.bean.Role;
import com.framework.shiro.bean.UserInfo;
import com.framework.shiro.service.UserLoginService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AuthRealm extends AuthorizingRealm {
	private static final Logger log = LoggerFactory.getLogger(AuthRealm.class);
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private CommonService commonService;
	/** 
     * 为当前登录的Subject授予角色和权限 
     * @see  经测试:本例中该方法的调用时机为需授权资源被访问时 
     * @see  经测试:并且每次访问需授权资源时都会执行该方法中的逻辑,这表明本例中默认并未启用AuthorizationCache 
     * @see  个人感觉若使用了Spring3.1开始提供的ConcurrentMapCache支持,则可灵活决定是否启用AuthorizationCache 
     * @see  比如说这里从数据库获取权限信息时,先去访问Spring3.1提供的缓存,而不使用Shior提供的AuthorizationCache 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals){  
        //获取当前登录的用户名,等价于(String)principals.fromRealm(this.getName()).iterator().next()  
        String currentUsername = (String)super.getAvailablePrincipal(principals);  
	    List<String> roleList = new ArrayList<String>();  
	    List<String> permissionList = new ArrayList<String>();  
	    //从数据库中获取当前登录用户的详细信息  
	    List<UserInfo> userInfos = userLoginService.getUserInfoByPersonCode(currentUsername);
	    UserInfo userInfo = null;
	    if(!CollectionUtils.isEmpty(userInfos)){
	    	userInfo = userInfos.get(0);
	    }
	    if(null != userInfo){  
	        //实体类User中包含有用户角色的实体类信息  
	        if(!CollectionUtils.isEmpty(userInfo.getRoles())){  
	            //获取当前登录用户的角色  
	            for(Role role : userInfo.getRoles()){  
	                roleList.add(role.getRoleCode());  
	                //实体类Role中包含有角色权限的实体类信息  
	                if(!CollectionUtils.isEmpty(role.getAuthorities())){  
	                    //获取权限  
	                    for(SysAuthority authority : role.getAuthorities()){  
	                        if(!StringUtils.isEmpty(authority.getAuthCode())){  
	                            permissionList.add(authority.getAuthCode());  
	                        }  
	                    }  
	                }  
	            }  
	        }  
	    }else{  
	          throw new AuthorizationException();  
	    }  
	    //为当前用户设置角色和权限  
	    SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();  
	    simpleAuthorInfo.addRoles(roleList);  
	    simpleAuthorInfo.addStringPermissions(permissionList);  
        
        //若该方法什么都不做直接返回null的话,就会导致任何用户访问/admin/listUser.jsp时都会自动跳转到unauthorizedUrl指定的地址  
        //详见applicationContext.xml中的<bean id="shiroFilter">的配置  
        return simpleAuthorInfo;  
    }  
  
      
    /** 
     * 验证当前登录的Subject 
     * @see  经测试:本例中该方法的调用时机为LoginController.login()方法中执行Subject.login()时 
     */  
    @Override  
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {  
        //获取基于用户名和密码的令牌  
        //实际上这个authcToken是从LoginController里面currentUser.login(token)传过来的  
        //两个token的引用都是一样的
        UsernamePasswordToken token = (UsernamePasswordToken)authcToken;  
        log.info("验证当前Subject时获取到token为" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));  

        //从数据库中获取当前登录用户的详细信息  
	    List<UserInfo> userInfos = userLoginService.getUserInfoByPersonCode(token.getUsername());
	    UserInfo userInfo = null;
	    if(!CollectionUtils.isEmpty(userInfos)){
	    	userInfo = userInfos.get(0);
	    	//查询照片
			try {
				SysCommonFileInfo commonFileInfo = commonService.getCommonFileInfo(userInfo.getId().toString(), FileBusinessType.USER_PHOTO.type());
				userInfo.setUserPhoto(commonFileInfo);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    }
        if(null != userInfo){  
        	//筛选栏目资源
        	List<Menu> menus = userLoginService.getMenusByPersonCode(userInfo.getPersonCode());
        	
        	AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(userInfo.getPersonCode(), userInfo.getPassword(), getName());  
        	this.setSession(CommonConstant.LOGIN_USER_INFO, JSONObject.fromObject(userInfo).toString()); 
        	this.setSession(CommonConstant.LOGIN_USER_MENU, JSONArray.fromObject(menus).toString()); 
        	return authcInfo;  
        }else{  
        	return null;  
        }  
        //此处无需比对,比对的逻辑Shiro会做,我们只需返回一个和令牌相关的正确的验证信息  
        //说白了就是第一个参数填登录用户名,第二个参数填合法的登录密码(可以是从数据库中取到的,本例中为了演示就硬编码了)  
        //这样一来,在随后的登录页面上就只有这里指定的用户和密码才能通过验证  
        /*if("admin".equals(token.getUsername())){  
            AuthenticationInfo authcInfo = new SimpleAuthenticationInfo("admin", "admin", this.getName());  
            this.setSession("currentUser", "mike");  
            return authcInfo;  
        }*/
        //没有返回登录用户名对应的SimpleAuthenticationInfo对象时,就会在LoginController中抛出UnknownAccountException异常  
//        return null;  
    }  


	/** 
     * 将一些数据放到ShiroSession中,以便于其它地方使用 
     * @see  比如Controller,使用时直接用HttpSession.getAttribute(key)就可以取到 
     */  
    private void setSession(Object key, Object value){  
        Subject currentUser = SecurityUtils.getSubject();  
        if(null != currentUser){  
            Session session = currentUser.getSession();  
            System.out.println("Session默认超时时间为[" + session.getTimeout() + "]毫秒");  
            if(null != session){  
                session.setAttribute(key, value);  
            }  
        }  
    } 
}
