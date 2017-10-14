package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.model.BasicInfo;

public interface BasicInfoService {

	void queryBasicInfoList(Page<BasicInfo> page, Map<String,Object> params) throws Exception;

	ResultMessage<BasicInfo> addBasicInfo(List<BasicInfo> basicInfos) throws Exception;

	ResultMessage<?> deleteBasicInfos(List<BasicInfo> basicInfos) throws Exception;

	ResultMessage<?> updateBasicInfo(List<BasicInfo> basicInfos) throws Exception;

	BasicInfo getBasicInfo(BasicInfo basicInfo) throws Exception;

	List<BasicInfo> getBasicTypes(Page<BasicInfo> page, Map<String, Object> params) throws Exception;

}
