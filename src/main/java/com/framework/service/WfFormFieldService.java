package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.model.WfFormField;
import com.framework.model.vo.WfFormFieldVO;

public interface WfFormFieldService {

	void queryWfFormFieldList(Page<WfFormFieldVO> page, Map<String,Object> params) throws Exception;

	ResultMessage<WfFormFieldVO> addWfFormField(WfFormFieldVO wfFormField) throws Exception;

	ResultMessage<?> deleteWfFormFields(List<WfFormFieldVO> wfFormFields) throws Exception;

	ResultMessage<?> updateWfFormField(WfFormFieldVO wfFormField) throws Exception;

	WfFormFieldVO getWfFormField(WfFormFieldVO wfFormField) throws Exception;

}
