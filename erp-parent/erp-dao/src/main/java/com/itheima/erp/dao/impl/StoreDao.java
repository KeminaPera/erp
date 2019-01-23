package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.itheima.erp.dao.IStoreDao;
import com.itheima.erp.entity.Store;
/**
 * Dao层仓库实现类
 * @author KeminaPera
 *
 */
public class StoreDao extends BaseDao<Store> implements IStoreDao {

	@Override
	public DetachedCriteria addCriteria(Store store1, Store store2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Store.class);
		if(store1 != null) {
			String temp = null;
			if((temp = store1.getName()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.eq("name", temp));
			}
			Long uuid = null;
			if((uuid = store1.getEmpuuid()) != null) {
				detachedCriteria.add(Restrictions.eq("empuuid", uuid));
			}
		}
		return detachedCriteria;
	}

}
