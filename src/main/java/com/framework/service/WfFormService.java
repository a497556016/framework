package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.model.WfForm;
import com.framework.model.vo.WfFormVO;

public interface WfFormService {

	void queryWfFormList(Page<WfFormVO> page, Map<String,Object> params) throws Exception;

	ResultMessage<WfFormVO> addWfForm(WfFormVO wfForm) throws Exception;

	ResultMessage<?> deleteWfForms(List<WfFormVO> wfForms) throws Exception;

	ResultMessage<?> updateWfForm(WfFormVO wfForm) throws Exception;

	WfFormVO getWfForm(WfFormVO wfForm) throws Exception;

}
