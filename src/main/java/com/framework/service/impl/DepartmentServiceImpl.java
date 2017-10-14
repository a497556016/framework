package com.framework.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.framework.bean.common.JsTreeNodeBean;
import com.framework.bean.vo.SysDepartmentTreeVo;
import com.framework.dao.SysDepartmentMapper;
import com.framework.dao.SysUserInfoMapper;
import com.framework.model.SysDepartment;
import com.framework.model.SysUserInfo;
import com.framework.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SysDepartmentMapper sysDepartmentMapper;
	@Autowired
	private SysUserInfoMapper sysUserInfoMapper;

	@Override
	public JsTreeNodeBean<SysDepartment> getDeptWithUserJsTreeData() throws Exception {
		JsTreeNodeBean<SysDepartment> root = new JsTreeNodeBean<>();
		
		EntityWrapper<SysDepartment> entityWrapper = new EntityWrapper<>();
		entityWrapper.and().isNull("p_code").or().eq("p_code", "");
		List<SysDepartment> departments = sysDepartmentMapper.selectList(entityWrapper);
		if(CollectionUtils.isNotEmpty(departments)){
			root.setId(departments.get(0).getId());
			root.setText(departments.get(0).getName());
			root.setData(departments.get(0));
			root.createState().setOpened(true);
			findDeptAndUsers(root);
		}
		return root;
	}

	private void findDeptAndUsers(JsTreeNodeBean<SysDepartment> root) {
		root.setChildren(new ArrayList<>());
		EntityWrapper<SysDepartment> entityWrapper = new EntityWrapper<>();
		entityWrapper.eq("p_code", root.getData().getCode());
		List<SysDepartment> departments = sysDepartmentMapper.selectList(entityWrapper);
		if(CollectionUtils.isNotEmpty(departments)){
			List<JsTreeNodeBean<SysDepartment>> nodeBeans = convertDeptToTreeNodes(departments);
			for (JsTreeNodeBean<SysDepartment> jsTreeNodeBean : nodeBeans) {
				findDeptAndUsers(jsTreeNodeBean);//递归查询
			}
			root.getChildren().addAll(nodeBeans);
		}
		EntityWrapper<SysUserInfo> wrapper = new EntityWrapper<>();
		wrapper.eq("deptCode", root.getData().getCode());
		List<SysUserInfo> userInfos = sysUserInfoMapper.selectList(wrapper);
		if(CollectionUtils.isNotEmpty(userInfos)){
			List<JsTreeNodeBean<SysUserInfo>> nodeBeans = convertUserToTreeNodes(userInfos);
			root.getChildren().addAll(nodeBeans);
		}
	}

	private List<JsTreeNodeBean<SysDepartment>> convertDeptToTreeNodes(List<SysDepartment> departments) {
		List<JsTreeNodeBean<SysDepartment>> nodeBeans = new ArrayList<>();
		for (SysDepartment sysDepartment : departments) {
			JsTreeNodeBean<SysDepartment> jsTreeNodeBean = new JsTreeNodeBean<>();
			jsTreeNodeBean.setText(sysDepartment.getName());
			jsTreeNodeBean.setId(sysDepartment.getId());
			jsTreeNodeBean.setData(sysDepartment);
			jsTreeNodeBean.setIcon("fa fa-group");
			nodeBeans.add(jsTreeNodeBean);
		}
		return nodeBeans;
	}

	private List<JsTreeNodeBean<SysUserInfo>> convertUserToTreeNodes(List<SysUserInfo> userInfos) {
		List<JsTreeNodeBean<SysUserInfo>> nodeBeans = new ArrayList<>();
		for (SysUserInfo sysUserInfo : userInfos) {
			JsTreeNodeBean<SysUserInfo> jsTreeNodeBean = new JsTreeNodeBean<>();
			jsTreeNodeBean.setText(sysUserInfo.getLastName());
			jsTreeNodeBean.setId(sysUserInfo.getId());
			jsTreeNodeBean.setData(sysUserInfo);
			jsTreeNodeBean.setIcon("fa fa-user");
			nodeBeans.add(jsTreeNodeBean);
		}
		return nodeBeans;
	}

	@Override
	public List<SysDepartmentTreeVo> queryMenuListByPCode(String pCode) throws Exception {
		List<SysDepartmentTreeVo> departmentTreeVos = sysDepartmentMapper.selectDepartMentByPCode("0".equals(pCode)?"":pCode);
		setChildrenIfHave(departmentTreeVos);
		return departmentTreeVos;
	}

	private void setChildrenIfHave(List<SysDepartmentTreeVo> departmentTreeVos) {
		for (SysDepartmentTreeVo sysDepartmentTreeVo : departmentTreeVos) {
			List<SysDepartmentTreeVo> children = sysDepartmentMapper.selectDepartMentByPCode(sysDepartmentTreeVo.getCode());
			if(CollectionUtils.isNotEmpty(children)){
				sysDepartmentTreeVo.setChildren(children);
				setChildrenIfHave(children);
			}else{
				sysDepartmentTreeVo.setLeaf(true);
			}
		}
	}

	@Override
	public void saveDepartment(List<SysDepartment> departments) throws Exception {
		for (SysDepartment sysDepartment : departments) {
			if(sysDepartment.getId()<=0){
				sysDepartmentMapper.insert(sysDepartment);
			}else{
				sysDepartmentMapper.updateByPrimaryKeySelective(sysDepartment);
			}
		}
	}
}
