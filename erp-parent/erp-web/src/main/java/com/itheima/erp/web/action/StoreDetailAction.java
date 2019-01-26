package com.itheima.erp.web.action;

import java.util.List;

import com.itheima.erp.biz.IStoreDetailBiz;
import com.itheima.erp.entity.StoreAlert;
import com.itheima.erp.entity.StoreDetail;

/**
 * action层仓库库存实现类
 * @author KeminaPera
 *
 */
public class StoreDetailAction extends BaseAction<StoreDetail> {

	private IStoreDetailBiz storeDetailBiz;

	public void setStoreDetailBiz(IStoreDetailBiz storeDetailBiz) {
		this.storeDetailBiz = storeDetailBiz;
		super.setBaseBiz(this.storeDetailBiz);
	}
	
	/**
	 * 获取库存警告列表
	 */
	public void getStoreAlertList() {
		List<StoreAlert> storeAlertList = storeDetailBiz.getStoreAlertList();
		parseObjectAndWrite(storeAlertList);
	}
}
