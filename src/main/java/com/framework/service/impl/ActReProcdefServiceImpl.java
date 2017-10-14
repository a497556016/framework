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
import com.framework.dao.ActReProcdefMapper;
import com.framework.model.ActReProcdef;
import com.framework.service.CommonService;
import com.framework.service.ActReProcdefService;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class ActReProcdefServiceImpl implements ActReProcdefService {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private ActReProcdefMapper actReProcdefMapper;
	@Autowired
	private CommonService commonService;
	
	public void queryActReProcdefList(Page<ActReProcdef> page, Map<String,Object> params) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<ActReProcdef> mppage = PageFactory.createFrom(page);
		
		List<ActReProcdef> list = actReProcdefMapper.queryActReProcdefList(mppage, params);

		page.setRows(list);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}


	public ResultMessage<ActReProcdef> addActReProcdef(ActReProcdef actReProcdef) throws Exception {
		ResultMessage<ActReProcdef> resultMessage = new ResultMessage<>();
		int i = actReProcdefMapper.insertSelective(actReProcdef);
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}


	public ResultMessage<?> deleteActReProcdefs(List<ActReProcdef> actReProcdefs) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int count = 0;
		for (ActReProcdef actReProcdef : actReProcdefs) {
			int i = actReProcdefMapper.deleteById(actReProcdef.getId());
			count += i;
		}
		if(count==actReProcdefs.size()){
			resultMessage.setSuccess(true);
		}
		return resultMessage;
	}

	@Override
	public ResultMessage<?> updateActReProcdef(ActReProcdef actReProcdef) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int i = 0;
		if(StringUtils.isEmpty(actReProcdef.getId())){
			i += actReProcdefMapper.insertSelective(actReProcdef);
		}else{
			i += actReProcdefMapper.updateByPrimaryKeySelective(actReProcdef);
		}
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}

	@Override
	public ActReProcdef getActReProcdef(ActReProcdef actReProcdef) throws Exception {
		
		return actReProcdefMapper.selectByPrimaryKey(actReProcdef.getId());
	}


}
