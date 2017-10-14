package com.framework.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysResourceTreeVo;
import com.framework.bean.vo.SysResourceVo;
import com.framework.dao.SysAuthResRelationMapper;
import com.framework.dao.SysResourceMapper;
import com.framework.exception.PrintMessageException;
import com.framework.model.SysAuthResRelation;
import com.framework.model.SysResource;
import com.framework.service.ResourceService;
import com.framework.util.PageFactory;

@Service
public class ResourceServiceImpl implements ResourceService {
	private static final Logger log = LoggerFactory.getLogger(ResourceServiceImpl.class);
	
	@Autowired
	private SysResourceMapper sysResourceMapper;
	@Autowired
	private SysAuthResRelationMapper sysAuthResRelationMapper;

	public void queryResList(Page<SysResource> page, SysResource resource) {
		com.baomidou.mybatisplus.plugins.Page<SysResource> mppage = PageFactory.createFrom(page);
		
		EntityWrapper<SysResource> wrapper = new EntityWrapper<>();
		wrapper.like("res_name", resource.getResName());
		List<SysResource> userInfos = sysResourceMapper.selectPage(mppage, wrapper);

		page.setRows(userInfos);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}

	@Override
	public SysResource selectResourceById(Integer id) {
		return sysResourceMapper.selectByPrimaryKey(id);
	}

	@Override
	public ResultMessage<SysResourceVo> addRes(SysResourceVo resource) {
		String authCodesStr = resource.getAuthCodes();
		String[] authCodes = authCodesStr.split(",");
		
		ResultMessage<SysResourceVo> rm = new ResultMessage<>();
		
		EntityWrapper<SysResource> wrapper = new EntityWrapper<>();
		wrapper.eq("res_code", resource.getResCode());
		int c = sysResourceMapper.selectCount(wrapper);
		if(c>0){
			throw new PrintMessageException("资源编码："+resource.getResCode()+"已经存在！");
		}
		
		sysResourceMapper.insert(resource);
		for (String authCode : authCodes) {
			SysAuthResRelation relation = new SysAuthResRelation();
			relation.setAuthCode(authCode);
			relation.setResCode(resource.getResCode());
			sysAuthResRelationMapper.insert(relation);
		}
		
		rm.setSuccess(true);
		rm.setModel(resource);
		return rm;
	}
	
	@Override
	public ResultMessage<SysResource> updateRes(SysResourceVo resource) {
		ResultMessage<SysResource> resultMessage = new ResultMessage<>();
		String[] authCodes = resource.getAuthCodes().split(",");
				
		sysResourceMapper.updateByPrimaryKeySelective(resource);
		
		EntityWrapper<SysAuthResRelation> entityWrapper = new EntityWrapper<>();
		entityWrapper.in("auth_code", authCodes).eq("res_code", resource.getResCode());
		sysAuthResRelationMapper.delete(entityWrapper);
		for (String authCode : authCodes) {
			SysAuthResRelation relation = new SysAuthResRelation();
			relation.setAuthCode(authCode);
			relation.setResCode(resource.getResCode());
			sysAuthResRelationMapper.insert(relation);
		}
		
		resultMessage.setSuccess(true);
		resultMessage.setModel(resource);
		return resultMessage;
	}

	@Override
	public List<SysResourceTreeVo> queryResListByPCode(String pCode) throws Exception {
		List<SysResourceTreeVo> resources = sysResourceMapper.queryResListByPCode(pCode);
		setResourceChild(resources);
		return resources;
	}

	private void setResourceChild(List<SysResourceTreeVo> resources) {
		for (SysResourceTreeVo sysResourceTreeVo : resources) {
			List<SysResourceTreeVo> children = sysResourceMapper.queryResListByPCode(sysResourceTreeVo.getId().toString());
			if(CollectionUtils.isNotEmpty(children)){
				sysResourceTreeVo.setChildren(children);
				setResourceChild(children);
			}else{
				sysResourceTreeVo.setLeaf(true);
			}
		}
	}

	@Override
	public boolean deleteResources(List<String> resCodes) throws Exception {
		for (String resCode : resCodes) {
			Map<String, Object> params = new HashMap<>();
			params.put("res_code", resCode);
			sysResourceMapper.deleteByMap(params);
			sysAuthResRelationMapper.deleteByMap(params);
		}
		return true;
	}

	@Override
	public List<String> getAuthCodes(String resCode) throws Exception {
		Map<String, Object> columnMap = new HashMap<>();
		columnMap.put("res_code", resCode);
		List<SysAuthResRelation> authResRelations = sysAuthResRelationMapper.selectByMap(columnMap);
		
		List<String> authCodes = new ArrayList<>();
		for(SysAuthResRelation authResRelation : authResRelations){
			authCodes.add(authResRelation.getAuthCode());
		}
		return authCodes;
	}

}
