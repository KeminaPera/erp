package com.itheima.erp.web.action;

import com.itheima.erp.biz.IGoodsBiz;
import com.itheima.erp.entity.Goods;
/**
 * 商品的Action类
 * @author KeminaPera
 *
 */
public class GoodsAction extends BaseAction<Goods> {

	//注入IGoodsBiz
	private IGoodsBiz goodsBiz;

	public void setGoodsBiz(IGoodsBiz goodsBiz) {
		this.goodsBiz = goodsBiz;
		super.setBaseBiz(this.goodsBiz);
	}
	
}
