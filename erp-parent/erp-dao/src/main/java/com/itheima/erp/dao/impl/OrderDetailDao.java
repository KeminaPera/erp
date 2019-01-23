package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.erp.dao.IOrderDetailDao;
import com.itheima.erp.entity.OrderDetail;
/**
 * Dao层订单明细实现类
 * @author KeminaPera
 *
 */
public class OrderDetailDao extends BaseDao<OrderDetail> implements IOrderDetailDao {

	@Override
	/**
	 * 添加条件
	 */
	public DetachedCriteria addCriteria(OrderDetail t1, OrderDetail t2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(OrderDetail.class);
		return detachedCriteria;
	}


}
