package com.framework.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysAuthorityVo;
import com.framework.dao.SysAuthResRelationMapper;
import com.framework.dao.SysAuthorityMapper;
import com.framework.dao.SysRoleAuthRelationMapper;
import com.framework.model.SysAuthResRelation;
import com.framework.model.SysAuthority;
import com.framework.model.SysRoleAuthRelation;
import com.framework.service.AuthorityService;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class AuthorityServiceImpl implements AuthorityService {
	private static final Logger log = LoggerFactory.getLogger(AuthorityServiceImpl.class);
	
	@Autowired
	private SysAuthorityMapper sysAuthorityMapper;
	@Autowired
	private SysAuthResRelationMapper authResRelationMapper;
	@Autowired
	private SysRoleAuthRelationMapper authRelationMapper;
	
	private BeanCopier authModelToVo = BeanCopier.create(SysAuthority.class, SysAuthorityVo.class, false);

	@Override
	public void queryAllAuthes(Page<SysAuthority> page,String resCode) {
		Map<String, Object> params = new HashMap<>();
		params.put("resCode", resCode);
		params.put("offset", page.getOffset());
		params.put("limit", page.getLimit());
		List<SysAuthority> authorities = sysAuthorityMapper.queryAllAuthes(params);
		params.put("offset", null);
		params.put("limit", null);
		int count = sysAuthorityMapper.countAllAuthes(params);
		page.setRows(authorities);
		page.setTotal(count);
		page.setSuccess(true);
	}

	@Override
	public void queryAuthList(Page<SysAuthority> page, SysAuthority authority) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<SysAuthority> mppage = PageFactory.createFrom(page);
		Wrapper<SysAuthority> wrapper = new EntityWrapper<>();
		if(StringUtils.isNotEmpty(authority.getAuthName())){
			wrapper.like("auth_name", authority.getAuthName());
		}
		if(StringUtils.isNotEmpty(authority.getAuthCode())){
			wrapper.like("auth_code", authority.getAuthCode());
		}
		
		List<SysAuthority> authorities = sysAuthorityMapper.selectPage(mppage, wrapper);
		page.setRows(authorities);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}

	@Override
	public void addAuth(SysAuthority authority, String resourceCodes) throws Exception {
		for(String resCode : resourceCodes.split(",")){
			SysAuthResRelation authResRelation = new SysAuthResRelation();
			authResRelation.setAuthCode(authority.getAuthCode());
			authResRelation.setResCode(resCode);
			authResRelationMapper.insert(authResRelation);
		}
		authority.setCreateDate(new Date());
		authority.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
		sysAuthorityMapper.insert(authority);
	}

	@Override
	public void deleteAuths(List<String> authCodes) throws Exception {
		for (String authCode : authCodes) {
			Map<String, Object> params = new HashMap<>();
			params.put("auth_code", authCode);
			
			sysAuthorityMapper.deleteByMap(params);
			
			authResRelationMapper.deleteByMap(params);
			
			authRelationMapper.deleteByMap(params);
		}
	}

	@Override
	public SysAuthorityVo getAuth(Integer id) throws Exception {
		SysAuthority authority = sysAuthorityMapper.selectById(id);
		
		SysAuthorityVo authorityVo = new SysAuthorityVo();
		authModelToVo.copy(authority, authorityVo, null);
		
		List<String> resCodes = new ArrayList<>();
		
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("auth_code", authority.getAuthCode());
		List<SysAuthResRelation> authResRelations = authResRelationMapper.selectByMap(columnMap);
		for (SysAuthResRelation sysAuthResRelation : authResRelations) {
			resCodes.add(sysAuthResRelation.getResCode());
		}
		
		authorityVo.setResCodes(resCodes);
		return authorityVo;
	}

	@Override
	public void updateAuth(SysAuthority authority, String resourceCodes) throws Exception {
		//删除原有资源
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("auth_code", authority.getAuthCode());
		authResRelationMapper.deleteByMap(columnMap);
		//新增资源
		if(StringUtils.isNotEmpty(resourceCodes)){
			for(String resCode : resourceCodes.split(",")){
				SysAuthResRelation authResRelation = new SysAuthResRelation();
				authResRelation.setAuthCode(authority.getAuthCode());
				authResRelation.setResCode(resCode);
				authResRelationMapper.insert(authResRelation);
			}
		}
		//更新权限
		sysAuthorityMapper.updateById(authority);
		
	}

}
