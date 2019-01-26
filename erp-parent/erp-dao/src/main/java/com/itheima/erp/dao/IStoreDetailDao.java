package com.itheima.erp.dao;

import java.util.List;

import com.itheima.erp.entity.StoreAlert;
import com.itheima.erp.entity.StoreDetail;

/**
 * Dao层仓库库存接口
 * @author KeminaPera
 *
 */
public interface IStoreDetailDao extends IBaseDao<StoreDetail> {

	StoreDetail getStoreDetailByStoreuuidAndGoodsuuid(Long storeuuid, Long goodsuuid);

	List<StoreAlert> getStoreAlertList();
}
