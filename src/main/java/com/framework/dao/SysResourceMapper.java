package com.framework.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.bean.vo.SysResourceTreeVo;
import com.framework.model.SysResource;

public interface SysResourceMapper extends BaseMapper<SysResource> {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysResource record);

    SysResource selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysResource record);

    int updateByPrimaryKeyWithBLOBs(SysResource record);

    int updateByPrimaryKey(SysResource record);

	List<SysResourceTreeVo> queryResListByPCode(String pCode);
}