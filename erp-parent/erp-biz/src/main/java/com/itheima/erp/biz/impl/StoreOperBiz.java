package com.itheima.erp.biz.impl;

import com.itheima.erp.biz.IStoreOperBiz;
import com.itheima.erp.dao.IStoreOperDao;
import com.itheima.erp.entity.StoreOper;
/**
 * service层库存变更实现类
 * @author KeminaPera
 *
 */
public class StoreOperBiz extends BaseBiz<StoreOper> implements IStoreOperBiz {

	//注入IStoreOperDao 
	private IStoreOperDao storeOperDao;

	public void setStoreOperDao(IStoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
		super.setBaseDao(this.storeOperDao);
	}
	
}
