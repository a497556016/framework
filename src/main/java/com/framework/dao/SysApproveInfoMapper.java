package com.framework.dao;

import com.framework.model.SysApproveInfo;
import com.framework.model.SysApproveInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysApproveInfoMapper {
    int countByExample(SysApproveInfoExample example);

    int deleteByExample(SysApproveInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysApproveInfo record);

    int insertSelective(SysApproveInfo record);

    List<SysApproveInfo> selectByExampleWithBLOBs(SysApproveInfoExample example);

    List<SysApproveInfo> selectByExample(SysApproveInfoExample example);

    SysApproveInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysApproveInfo record, @Param("example") SysApproveInfoExample example);

    int updateByExampleWithBLOBs(@Param("record") SysApproveInfo record, @Param("example") SysApproveInfoExample example);

    int updateByExample(@Param("record") SysApproveInfo record, @Param("example") SysApproveInfoExample example);

    int updateByPrimaryKeySelective(SysApproveInfo record);

    int updateByPrimaryKeyWithBLOBs(SysApproveInfo record);

    int updateByPrimaryKey(SysApproveInfo record);
}