package com.framework.service;

import java.util.List;

import com.framework.bean.common.JsTreeNodeBean;
import com.framework.bean.vo.SysDepartmentTreeVo;
import com.framework.model.SysDepartment;

public interface DepartmentService {

	JsTreeNodeBean getDeptWithUserJsTreeData() throws Exception;

	List<SysDepartmentTreeVo> queryMenuListByPCode(String pCode) throws Exception;

	void saveDepartment(List<SysDepartment> departments) throws Exception;

}
