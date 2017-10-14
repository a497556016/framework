package com.ssa.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.bean.common.Page;
import com.framework.bean.common.ResultMessage;
import com.framework.controller.BaseController;
import com.ssa.model.Goods;
import com.framework.service.CommonService;
import com.ssa.service.GoodsService;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("goods")
public class GoodsController extends BaseController {
	private final Logger logger = Logger.getLogger(getClass());
	@Autowired
	private GoodsService goodsService;

	@RequestMapping("toGoodsManage")
	public String toGoodsManage(){
		return "ssa/goods/goodsManage";
	}

	@RequestMapping("queryGoodsList")
	@ResponseBody
	public Page<Goods> queryGoodsList(Page<Goods> page,Goods goods){
		try {
			goodsService.queryGoodsList(page,goods);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return page;
	}
	
	@RequestMapping("addGoods")
	@ResponseBody
	public ResultMessage<Goods> addGoods(Goods goods){
		ResultMessage<Goods> rm = new ResultMessage<>();
		try {
			rm = goodsService.addGoods(goods);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("updateGoods")
	@ResponseBody
	public ResultMessage<?> updateGoods(Goods goods){
		ResultMessage<?> rm = new ResultMessage<>();
		try {
			rm = goodsService.updateGoods(goods);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("deleteGoodss")
	@ResponseBody
	public ResultMessage<?> deleteGoodss(String goodssJson){
		ResultMessage<?> rm = null;
		try {
			List<Goods> goodss = gson.fromJson(goodssJson, new TypeToken<List<Goods>>(){}.getType());
			rm = goodsService.deleteGoodss(goodss);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			rm.setMessage(e.getMessage());
		}
		return rm;
	}
	
	@RequestMapping("getGoods")
	@ResponseBody
	public ResultMessage<Goods> getGoods(Goods goods){
		ResultMessage<Goods> resultMessage = new ResultMessage<>();
		try {
			Goods goods1 = goodsService.getGoods(goods);
			resultMessage.setModel(goods1);
			resultMessage.setSuccess(true);
		} catch (Exception e) {
			resultMessage.setMessage(e.getMessage());
		}
		return resultMessage;
	} 
}
