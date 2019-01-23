package com.itheima.erp.biz.impl;

import com.itheima.erp.biz.IStoreBiz;
import com.itheima.erp.dao.IStoreDao;
import com.itheima.erp.entity.Store;
/**
 * service层仓库的实现类
 * @author KeminaPera
 *
 */
public class StoreBiz extends BaseBiz<Store> implements IStoreBiz {

	//注入IStoreDao
	private IStoreDao storeDao;

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
		super.setBaseDao(this.storeDao);
	}
	
}
