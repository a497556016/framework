package com.framework.dao;

import com.framework.model.SysCommonFileInfo;
import com.framework.model.SysCommonFileInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysCommonFileInfoMapper {
    int countByExample(SysCommonFileInfoExample example);

    int deleteByExample(SysCommonFileInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SysCommonFileInfo record);

    int insertSelective(SysCommonFileInfo record);

    List<SysCommonFileInfo> selectByExample(SysCommonFileInfoExample example);

    SysCommonFileInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SysCommonFileInfo record, @Param("example") SysCommonFileInfoExample example);

    int updateByExample(@Param("record") SysCommonFileInfo record, @Param("example") SysCommonFileInfoExample example);

    int updateByPrimaryKeySelective(SysCommonFileInfo record);

    int updateByPrimaryKey(SysCommonFileInfo record);
}