package com.itheima.erp.web.action;

import com.itheima.erp.biz.IStoreOperBiz;
import com.itheima.erp.entity.StoreOper;

/**
 * action层仓库变更实现类
 * @author KeminaPera
 *
 */
public class StoreOperAction extends BaseAction<StoreOper> {
	
	//注入IStoreOperBiz
	private IStoreOperBiz storeOperBiz;

	public void setStoreOperBiz(IStoreOperBiz storeOperBiz) {
		this.storeOperBiz = storeOperBiz;
		super.setBaseBiz(this.storeOperBiz);
	}
		
}
