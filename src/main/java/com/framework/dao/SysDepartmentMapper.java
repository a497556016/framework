package com.framework.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.framework.bean.vo.SysDepartmentTreeVo;
import com.framework.model.SysDepartment;

public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(SysDepartment record);

    SysDepartment selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDepartment record);

    int updateByPrimaryKey(SysDepartment record);

	List<SysDepartmentTreeVo> selectDepartMentByPCode(String code);
}