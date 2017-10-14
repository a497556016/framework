package com.framework.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.framework.bean.vo.SysUserInfoListVo;
import com.framework.model.SysRole;
import com.framework.model.SysUserInfo;

public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysUserInfo record);

    SysUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUserInfo record);

    int updateByPrimaryKey(SysUserInfo record);

	List<SysUserInfoListVo> queryUserList(Page<SysUserInfoListVo> mppage, SysUserInfo userInfo);

}