package com.itheima.erp.biz.impl;

import com.itheima.erp.biz.IGoodsBiz;
import com.itheima.erp.dao.IGoodsDao;
import com.itheima.erp.entity.Goods;
/**
 * 商品的Biz层实现类
 * @author KeminaPera
 *
 */
public class GoodsBiz extends BaseBiz<Goods> implements IGoodsBiz {

	//注入GoodsDao
	private IGoodsDao goodsDao;

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
		super.setBaseDao(this.goodsDao);
	}
}
