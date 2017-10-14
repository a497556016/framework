package com.framework.service;

import java.util.List;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysResourceTreeVo;
import com.framework.bean.vo.SysResourceVo;
import com.framework.model.SysResource;

public interface ResourceService {

	void queryResList(Page<SysResource> page, SysResource resource);

	SysResource selectResourceById(Integer id);

	ResultMessage<SysResourceVo> addRes(SysResourceVo resource);

	ResultMessage<SysResource> updateRes(SysResourceVo resource);

	List<SysResourceTreeVo> queryResListByPCode(String pCode) throws Exception;

	boolean deleteResources(List<String> resCodes) throws Exception;

	List<String> getAuthCodes(String resCode) throws Exception;

}
