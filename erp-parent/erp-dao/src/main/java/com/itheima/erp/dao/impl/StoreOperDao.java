package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

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
		if(storeOper1 != null) {
			Long uuid = null;
			if((uuid = storeOper1.getEmpuuid()) != null) {
				detachedCriteria.add(Restrictions.eq("empuuid", uuid));
			}
			if((uuid = storeOper1.getGoodsuuid()) != null) {
				detachedCriteria.add(Restrictions.eq("goodsuuid", uuid));
			}
			if((uuid = storeOper1.getStoreuuid()) != null) {
				detachedCriteria.add(Restrictions.eq("storeuuid", uuid));
			}
			String temp = null;
			if((temp = storeOper1.getType()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.eq("type", temp));
			}
			if(storeOper1.getOpertime() != null) {
				detachedCriteria.add(Restrictions.ge("opertime", storeOper1.getOpertime()));
			}
		}
		if(storeOper2 != null) {
			if(storeOper2.getOpertime() != null) {
				detachedCriteria.add(Restrictions.le("opertime", storeOper2.getOpertime()));
			}
		}
		return detachedCriteria;
	}

}
