package com.framework.service;

import java.util.List;

import com.framework.bean.common.Page;
import com.framework.bean.vo.SysAuthorityVo;
import com.framework.model.SysAuthority;

public interface AuthorityService {

	void queryAllAuthes(Page<SysAuthority> page, String resCode) throws Exception;

	void queryAuthList(Page<SysAuthority> page, SysAuthority authority) throws Exception;

	void addAuth(SysAuthority authority, String resourceCodes) throws Exception;

	void deleteAuths(List<String> authCodes) throws Exception;

	SysAuthorityVo getAuth(Integer id) throws Exception;

	void updateAuth(SysAuthority authority, String resourceCodes) throws Exception;

}
