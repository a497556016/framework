package com.ssa.service;

import java.util.List;
import java.util.Map;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.ssa.model.Goods;

public interface GoodsService {

	void queryGoodsList(Page<Goods> page, Goods goods) throws Exception;

	ResultMessage<Goods> addGoods(Goods goods) throws Exception;

	ResultMessage<?> deleteGoodss(List<Goods> goodss) throws Exception;

	ResultMessage<?> updateGoods(Goods goods) throws Exception;

	Goods getGoods(Goods goods) throws Exception;

}
