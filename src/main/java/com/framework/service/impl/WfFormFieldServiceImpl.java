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
import com.framework.dao.WfFormFieldMapper;
import com.framework.model.WfFormField;
import com.framework.model.vo.WfFormFieldVO;
import com.framework.service.CommonService;
import com.framework.service.WfFormFieldService;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class WfFormFieldServiceImpl implements WfFormFieldService {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private WfFormFieldMapper wfFormFieldMapper;
	@Autowired
	private CommonService commonService;
	
	public void queryWfFormFieldList(Page<WfFormFieldVO> page, Map<String,Object> params) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<WfFormFieldVO> mppage = PageFactory.createFrom(page);
		
		List<WfFormFieldVO> list = wfFormFieldMapper.queryWfFormFieldList(mppage, params);

		page.setRows(list);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}


	public ResultMessage<WfFormFieldVO> addWfFormField(WfFormFieldVO wfFormField) throws Exception {
		ResultMessage<WfFormFieldVO> resultMessage = new ResultMessage<>();
		int i = wfFormFieldMapper.insertSelective(wfFormField);
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}


	public ResultMessage<?> deleteWfFormFields(List<WfFormFieldVO> wfFormFields) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int count = 0;
		for (WfFormFieldVO wfFormField : wfFormFields) {
			int i = wfFormFieldMapper.deleteById(wfFormField.getId());
			count += i;
		}
		if(count==wfFormFields.size()){
			resultMessage.setSuccess(true);
		}
		return resultMessage;
	}

	@Override
	public ResultMessage<?> updateWfFormField(WfFormFieldVO wfFormField) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int i = 0;
		if(null==wfFormField.getId()||wfFormField.getId()<=0){
			i += wfFormFieldMapper.insertSelective(wfFormField);
		}else{
			i += wfFormFieldMapper.updateByPrimaryKeySelective(wfFormField);
		}
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}

	@Override
	public WfFormFieldVO getWfFormField(WfFormFieldVO wfFormField) throws Exception {
		
		return wfFormFieldMapper.selectByPrimaryKey(wfFormField.getId());
	}


}
