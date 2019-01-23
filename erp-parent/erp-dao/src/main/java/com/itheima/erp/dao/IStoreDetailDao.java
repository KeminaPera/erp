package com.itheima.erp.dao;

import com.itheima.erp.entity.StoreDetail;

/**
 * Dao层仓库库存接口
 * @author KeminaPera
 *
 */
public interface IStoreDetailDao extends IBaseDao<StoreDetail> {

	StoreDetail getStoreDetailByStoreuuidAndGoodsuuid(Long storeuuid, Long goodsuuid);

}
