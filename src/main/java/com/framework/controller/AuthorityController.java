package com.framework.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysAuthorityVo;
import com.framework.model.SysAuthority;
import com.framework.service.AuthorityService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("auth")
public class AuthorityController extends BaseController {
	private static final Logger log = LoggerFactory.getLogger(AuthorityController.class);
	
	@Autowired
	private AuthorityService authorityService;
	
	@RequestMapping("toAuthorityManage")
	public String toAuthorityManage(){
		return "system/auth/authorityManage";
	}
	
	@RequestMapping("queryAllAuthes")
	@ResponseBody
	public Page<SysAuthority> queryAllAuthes(Page<SysAuthority> page,String resCode){
		try {
			authorityService.queryAllAuthes(page,resCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}
	
	@RequestMapping("queryAuthList")
	@ResponseBody
	public Page<SysAuthority> queryAuthList(Page<SysAuthority> page,SysAuthority authority){
		try {
			authorityService.queryAuthList(page,authority);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}
	

	@RequestMapping("/addAuth")
	@ResponseBody
	public ResultMessage<?> addAuth(SysAuthority authority,String resourceCodes){
		ResultMessage<?> message = new ResultMessage<>();
		try{
			authorityService.addAuth(authority,resourceCodes);
			message.setSuccess(true);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			message.setMessage(e.getMessage());
		}
		return message;
	}
	
	@RequestMapping("/updateAuth")
	@ResponseBody
	public ResultMessage<?> updateAuth(SysAuthority authority,String resourceCodes){
		ResultMessage<?> message = new ResultMessage<>();
		try{
			authorityService.updateAuth(authority,resourceCodes);
			message.setSuccess(true);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			message.setMessage(e.getMessage());
		}
		return message;
	}
	
	@RequestMapping("/deleteAuths")
	@ResponseBody
	public ResultMessage<?> deleteAuths(String authCodesJson){
		ResultMessage<?> message = new ResultMessage<>();
		try{
			List<String> authCodes = gson.fromJson(authCodesJson, new TypeToken<List<Integer>>(){}.getType());
			authorityService.deleteAuths(authCodes);
			message.setSuccess(true);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			message.setMessage(e.getMessage());
		}
		return message;
	}
	
	@RequestMapping("/getAuth")
	@ResponseBody
	public ResultMessage<SysAuthorityVo> getAuth(Integer id){
		ResultMessage<SysAuthorityVo> message = new ResultMessage<>();
		try{
			SysAuthorityVo authorityVo = authorityService.getAuth(id);
			message.setModel(authorityVo);
			message.setSuccess(true);
		}catch(Exception e){
			log.error(e.getMessage(), e);
			message.setMessage(e.getMessage());
		}
		return message;
	}
}
