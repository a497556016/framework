package com.framework.service;

import java.util.List;
import java.util.Map;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.model.ActReProcdef;

public interface ActReProcdefService {

	void queryActReProcdefList(Page<ActReProcdef> page, Map<String,Object> params) throws Exception;

	ResultMessage<ActReProcdef> addActReProcdef(ActReProcdef actReProcdef) throws Exception;

	ResultMessage<?> deleteActReProcdefs(List<ActReProcdef> actReProcdefs) throws Exception;

	ResultMessage<?> updateActReProcdef(ActReProcdef actReProcdef) throws Exception;

	ActReProcdef getActReProcdef(ActReProcdef actReProcdef) throws Exception;

}
