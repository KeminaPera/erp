package com.itheima.erp.web.action;

import com.itheima.erp.biz.IGoodsTypeBiz;
import com.itheima.erp.entity.GoodsType;
/**
 * 商品类型Action
 * @author KeminaPera
 *
 */
public class GoodsTypeAction extends BaseAction<GoodsType> {

	//注入GoodsTypeBiz
	private IGoodsTypeBiz goodsTypeBiz;

	public void setGoodsTypeBiz(IGoodsTypeBiz goodsTypeBiz) {
		this.goodsTypeBiz = goodsTypeBiz;
		super.setBaseBiz(this.goodsTypeBiz);
	}
	
}
