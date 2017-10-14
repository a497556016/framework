package com.framework.dao;

import com.framework.model.SysIcons;
import com.framework.model.SysIconsExample;
import com.framework.model.SysIconsKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysIconsMapper {
    int countByExample(SysIconsExample example);

    int deleteByExample(SysIconsExample example);

    int deleteByPrimaryKey(SysIconsKey key);

    int insert(SysIcons record);

    int insertSelective(SysIcons record);

    List<SysIcons> selectByExampleWithBLOBs(SysIconsExample example);

    List<SysIcons> selectByExample(SysIconsExample example);

    SysIcons selectByPrimaryKey(SysIconsKey key);

    int updateByExampleSelective(@Param("record") SysIcons record, @Param("example") SysIconsExample example);

    int updateByExampleWithBLOBs(@Param("record") SysIcons record, @Param("example") SysIconsExample example);

    int updateByExample(@Param("record") SysIcons record, @Param("example") SysIconsExample example);

    int updateByPrimaryKeySelective(SysIcons record);

    int updateByPrimaryKeyWithBLOBs(SysIcons record);

    int updateByPrimaryKey(SysIcons record);
}