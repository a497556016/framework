package com.framework.service;

import java.util.List;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.bean.vo.SysUserInfoListVo;
import com.framework.bean.vo.SysUserInfoVo;
import com.framework.model.SysUserInfo;

public interface UserService {

	void queryUserList(Page<SysUserInfoListVo> page, SysUserInfo userInfo) throws Exception;

	ResultMessage<SysUserInfoVo> addUser(SysUserInfoVo userInfo) throws Exception;

	ResultMessage<?> deleteUsers(List<SysUserInfoVo> sysUserInfoVos) throws Exception;

	void roleAssign(List<String> roleCodes, String personCode) throws Exception;

	List<String> getUserRoleCodes(String personCode) throws Exception;

	SysUserInfo getUserById(String id) throws Exception;

	ResultMessage<SysUserInfoVo> updateUser(SysUserInfoVo userInfo) throws Exception;

	void lockUser(Integer id, boolean locked) throws Exception;

	SysUserInfoVo getUser(SysUserInfo user) throws Exception;

}
