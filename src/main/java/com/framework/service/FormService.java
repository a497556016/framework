package com.framework.service;

import java.util.List;

import com.framework.bean.FormProduceInfo;
import com.framework.bean.common.Page;
import com.framework.model.ProField;
import com.framework.model.ProForm;

public interface FormService {

	List<ProField> getTableFields(String tableName) throws Exception;

	void saveForm(ProForm proForm, List<ProField> fields) throws Exception;

	void queryFormList(Page<ProForm> page, ProForm proForm) throws Exception;

	ProForm getForm(Integer formId) throws Exception;

	List<ProField> getFormFields(Integer formId) throws Exception;

	void produceCodes(FormProduceInfo formProduceInfo) throws Exception;

}
