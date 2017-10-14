package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.model.ActReModel;

public interface ActReModelService {

	void queryActReModelList(Page<ActReModel> page, Map<String,Object> params) throws Exception;

	ResultMessage<ActReModel> addActReModel(ActReModel actReModel) throws Exception;

	ResultMessage<?> deleteActReModels(List<ActReModel> actReModels) throws Exception;

	ResultMessage<?> updateActReModel(ActReModel actReModel) throws Exception;

	ActReModel getActReModel(ActReModel actReModel) throws Exception;

}
