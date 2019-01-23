package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.itheima.erp.dao.IOrdersDao;
import com.itheima.erp.entity.Orders;
/**
 * Dao层订单实现类
 * @author KeminaPera
 *
 */
public class OrdersDao extends BaseDao<Orders> implements IOrdersDao {

	@Override
	public DetachedCriteria addCriteria(Orders orders1, Orders orders2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Orders.class);
		if(orders1 != null) {
			String temp = null;
			if((temp = orders1.getState()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.eq("state", temp));
			}
			if((temp = orders1.getType()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.eq("type", temp));
			}
		}
		return detachedCriteria;
	}

}
