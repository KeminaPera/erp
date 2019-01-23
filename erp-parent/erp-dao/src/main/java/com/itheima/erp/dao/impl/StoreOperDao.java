package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.erp.dao.IStoreOperDao;
import com.itheima.erp.entity.StoreOper;
/**
 * Dao层仓库变更实现类
 * @author KeminaPera
 *
 */
public class StoreOperDao extends BaseDao<StoreOper> implements IStoreOperDao {

	@Override
	/**
	 * 添加条件
	 */
	public DetachedCriteria addCriteria(StoreOper storeOper1, StoreOper storeOper2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoreOper.class);
		return detachedCriteria;
	}

}
