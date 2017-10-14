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
import com.framework.dao.WfFormMapper;
import com.framework.model.WfForm;
import com.framework.model.vo.WfFormFieldVO;
import com.framework.model.vo.WfFormVO;
import com.framework.service.CommonService;
import com.framework.service.WfFormService;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class WfFormServiceImpl implements WfFormService {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private WfFormMapper wfFormMapper;
	@Autowired
	private WfFormFieldMapper formFieldMapper;
	@Autowired
	private CommonService commonService;
	
	public void queryWfFormList(Page<WfFormVO> page, Map<String,Object> params) throws Exception {
		com.baomidou.mybatisplus.plugins.Page<WfFormVO> mppage = PageFactory.createFrom(page);
		
		List<WfFormVO> list = wfFormMapper.queryWfFormList(mppage, params);

		page.setRows(list);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}


	public ResultMessage<WfFormVO> addWfForm(WfFormVO wfForm) throws Exception {
		ResultMessage<WfFormVO> resultMessage = new ResultMessage<>();
		int i = wfFormMapper.insertSelective(wfForm);
		List<WfFormFieldVO> fields = wfForm.getFields();
		for (WfFormFieldVO wfFormFieldVO : fields) {
			formFieldMapper.insertSelective(wfFormFieldVO);
		}
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}


	public ResultMessage<?> deleteWfForms(List<WfFormVO> wfForms) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int count = 0;
		for (WfFormVO wfForm : wfForms) {
			int i = wfFormMapper.deleteById(wfForm.getId());
			count += i;
		}
		if(count==wfForms.size()){
			resultMessage.setSuccess(true);
		}
		return resultMessage;
	}

	@Override
	public ResultMessage<?> updateWfForm(WfFormVO wfForm) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int i = 0;
		if(null==wfForm.getId()){
			i += wfFormMapper.insertSelective(wfForm);
		}else{
			i += wfFormMapper.updateByPrimaryKeySelective(wfForm);
		}
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}

	@Override
	public WfFormVO getWfForm(WfFormVO wfForm) throws Exception {
		
		return wfFormMapper.selectByPrimaryKey(wfForm.getId());
	}


}
