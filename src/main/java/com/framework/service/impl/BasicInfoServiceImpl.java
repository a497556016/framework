package com.framework.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.dao.BasicInfoMapper;
import com.framework.model.BasicInfo;
import com.framework.service.CommonService;
import com.framework.service.BasicInfoService;
import com.framework.util.DictUtils;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class BasicInfoServiceImpl implements BasicInfoService {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private BasicInfoMapper basicInfoMapper;
	@Autowired
	private CommonService commonService;
	
	public void queryBasicInfoList(Page<BasicInfo> page, Map<String,Object> params) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<BasicInfo> mppage = PageFactory.createFrom(page);
		
		List<BasicInfo> list = basicInfoMapper.queryBasicInfoList(mppage, params);

		page.setRows(list);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}


	public ResultMessage<BasicInfo> addBasicInfo(List<BasicInfo> basicInfos) throws Exception {
		ResultMessage<BasicInfo> resultMessage = new ResultMessage<>();
		int i = 0;
		for (BasicInfo basicInfo : basicInfos) {
			basicInfo.setCreateDate(new Date());
			basicInfo.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
			i += basicInfoMapper.insert(basicInfo);
		}
		
		resultMessage.setSuccess(i==basicInfos.size());
		return resultMessage;
	}


	public ResultMessage<?> deleteBasicInfos(List<BasicInfo> basicInfos) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int count = 0;
		for (BasicInfo basicInfo : basicInfos) {
			EntityWrapper<BasicInfo> wrapper = new EntityWrapper<>();
			if(null!=basicInfo.getId()&&basicInfo.getId()>0){
				wrapper.eq("id", basicInfo.getId());
			}
			if(StringUtils.isNotEmpty(basicInfo.getType())){
				wrapper.eq("type", basicInfo.getType());
			}
			int i = basicInfoMapper.delete(wrapper);
			count += i;
		}
		if(count==basicInfos.size()){
			resultMessage.setSuccess(true);
		}
		return resultMessage;
	}

	@Override
	public ResultMessage<?> updateBasicInfo(List<BasicInfo> basicInfos) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		
		int i = 0;
		for (BasicInfo basicInfo : basicInfos) {
			if(null==basicInfo.getId()||basicInfo.getId()<=0){
				basicInfo.setCreateDate(new Date());
				basicInfo.setCreateBy(LoginUtils.getUserInfo().getPersonCode());
				i += basicInfoMapper.insert(basicInfo);
			}else{
				basicInfo.setModifyDate(new Date());
				basicInfo.setModifyBy(LoginUtils.getUserInfo().getPersonCode());
				i += basicInfoMapper.updateByPrimaryKeySelective(basicInfo);
			}
		}
		
		resultMessage.setSuccess(i==basicInfos.size());
		return resultMessage;
	}

	@Override
	public BasicInfo getBasicInfo(BasicInfo basicInfo) throws Exception {
		
		return basicInfoMapper.selectOne(basicInfo);
	}


	@Override
	public List<BasicInfo> getBasicTypes(Page<BasicInfo> page, Map<String, Object> params) throws Exception {
		
		return basicInfoMapper.getBasicTypes(PageFactory.createFrom(page),params);
	}


}
