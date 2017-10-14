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
import com.framework.dao.ActReModelMapper;
import com.framework.model.ActReModel;
import com.framework.service.CommonService;
import com.framework.service.ActReModelService;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class ActReModelServiceImpl implements ActReModelService {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ActReModelMapper actReModelMapper;
	@Autowired
	private CommonService commonService;
	
	public void queryActReModelList(Page<ActReModel> page, Map<String,Object> params) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<ActReModel> mppage = PageFactory.createFrom(page);
		
		List<ActReModel> list = actReModelMapper.queryActReModelList(mppage, params);

		page.setRows(list);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}


	public ResultMessage<ActReModel> addActReModel(ActReModel actReModel) throws Exception {
		ResultMessage<ActReModel> resultMessage = new ResultMessage<>();
		int i = actReModelMapper.insertSelective(actReModel);
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}


	public ResultMessage<?> deleteActReModels(List<ActReModel> actReModels) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int count = 0;
		for (ActReModel actReModel : actReModels) {
			int i = actReModelMapper.deleteById(actReModel.getId());
			count += i;
		}
		if(count==actReModels.size()){
			resultMessage.setSuccess(true);
		}
		return resultMessage;
	}

	@Override
	public ResultMessage<?> updateActReModel(ActReModel actReModel) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int i = 0;
		if(StringUtils.isNotEmpty(actReModel.getId())){
			i += actReModelMapper.insertSelective(actReModel);
		}else{
			i += actReModelMapper.updateByPrimaryKeySelective(actReModel);
		}
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}

	@Override
	public ActReModel getActReModel(ActReModel actReModel) throws Exception {
		
		return actReModelMapper.selectByPrimaryKey(actReModel.getId());
	}


}
