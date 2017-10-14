package com.ssa.dao;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ssa.model.Goods;

public interface GoodsMapper extends BaseMapper<Goods> {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);
}