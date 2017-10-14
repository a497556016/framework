package com.ssa.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.ssa.dao.GoodsMapper;
import com.ssa.model.Goods;
import com.framework.service.CommonService;
import com.ssa.service.GoodsService;
import com.framework.util.LoginUtils;
import com.framework.util.PageFactory;

@Service
public class GoodsServiceImpl implements GoodsService {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private GoodsMapper goodsMapper;
	@Autowired
	private CommonService commonService;
	
	public void queryGoodsList(Page<Goods> page, Goods goods) throws Exception {
		
		com.baomidou.mybatisplus.plugins.Page<Goods> mppage = PageFactory.createFrom(page);
		
		EntityWrapper<Goods> wrapper = new EntityWrapper<>();
		if((null!=goods.getName())){
			wrapper.eq("name",goods.getName());
		}
		if((null!=goods.getCode())){
			wrapper.eq("code",goods.getCode());
		}
		
		List<Goods> list = goodsMapper.selectPage(mppage, wrapper);

		page.setRows(list);
		page.setTotal(mppage.getTotal());
		page.setSuccess(true);
	}


	public ResultMessage<Goods> addGoods(Goods goods) throws Exception {
		ResultMessage<Goods> resultMessage = new ResultMessage<>();
		int i = goodsMapper.insert(goods);
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}


	public ResultMessage<?> deleteGoodss(List<Goods> goodss) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int count = 0;
		for (Goods goods : goodss) {
			int i = goodsMapper.deleteById(goods.getId());
			count += i;
		}
		if(count==goodss.size()){
			resultMessage.setSuccess(true);
		}
		return resultMessage;
	}

	@Override
	public ResultMessage<?> updateGoods(Goods goods) throws Exception {
		ResultMessage<?> resultMessage = new ResultMessage<>();
		int i = goodsMapper.updateByPrimaryKeySelective(goods);
		resultMessage.setSuccess(i>0);
		return resultMessage;
	}

	@Override
	public Goods getGoods(Goods goods) throws Exception {
		
		return goodsMapper.selectOne(goods);
	}


}
