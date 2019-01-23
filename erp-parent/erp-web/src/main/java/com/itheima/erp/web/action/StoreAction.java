package com.itheima.erp.web.action;

import com.itheima.erp.biz.IStoreBiz;
import com.itheima.erp.entity.Store;

/**
 * action层仓库实现类
 * @author KeminaPera
 *
 */
public class StoreAction extends BaseAction<Store> {

	//注入IStoreBiz storeBiz
	private IStoreBiz storeBiz;

	public void setStoreBiz(IStoreBiz storeBiz) {
		this.storeBiz = storeBiz;
		super.setBaseBiz(this.storeBiz);
	}
	
}
