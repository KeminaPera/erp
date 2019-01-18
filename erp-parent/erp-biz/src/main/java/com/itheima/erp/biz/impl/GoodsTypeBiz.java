package com.itheima.erp.biz.impl;

import com.itheima.erp.biz.IGoodsTypeBiz;
import com.itheima.erp.dao.IGoodsTypeDao;
import com.itheima.erp.entity.GoodsType;
/**
 * service层商品种类实现类
 * @author KeminaPera
 *
 */
public class GoodsTypeBiz extends BaseBiz<GoodsType> implements IGoodsTypeBiz {

	//注入GoodsTypeDao
	private IGoodsTypeDao goodsTypeDao;

	public void setGoodsTypeDao(IGoodsTypeDao goodsTypeDao) {
		this.goodsTypeDao = goodsTypeDao;
		super.setBaseDao(this.goodsTypeDao);
	}
	
}
