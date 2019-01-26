package com.itheima.erp.biz;

import java.util.List;

import com.itheima.erp.entity.StoreAlert;
import com.itheima.erp.entity.StoreDetail;

/**
 * service层仓库库存接口
 * @author KeminaPera
 *
 */
public interface IStoreDetailBiz extends IBaseBiz<StoreDetail> {

	/**
	 * 获取库存警告列表
	 * @return
	 */
	List<StoreAlert> getStoreAlertList();
}
