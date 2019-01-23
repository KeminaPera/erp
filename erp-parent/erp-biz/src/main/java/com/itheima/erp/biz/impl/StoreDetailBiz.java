package com.itheima.erp.biz.impl;

import com.itheima.erp.biz.IStoreDetailBiz;
import com.itheima.erp.dao.IStoreDetailDao;
import com.itheima.erp.entity.StoreDetail;
/**
 * Service层仓库库存实现类
 * @author KeminaPera
 *
 */
public class StoreDetailBiz extends BaseBiz<StoreDetail> implements IStoreDetailBiz {

	//注入IStoreDetailDao storeDetailDao
	private IStoreDetailDao storeDetailDao;

	public void setStoreDetailDao(IStoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
		super.setBaseDao(this.storeDetailDao);
	}
	
}
