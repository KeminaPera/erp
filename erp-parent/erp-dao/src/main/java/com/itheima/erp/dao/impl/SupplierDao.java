package com.itheima.erp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.itheima.erp.dao.ISupplierDao;
import com.itheima.erp.entity.Supplier;
/**
 * Dao层供应商/客户实现类
 * @author KeminaPera
 *
 */
public class SupplierDao extends BaseDao<Supplier> implements ISupplierDao {

	@Override
	/**
	 * 添加查询条件
	 */
	public DetachedCriteria addCriteria(Supplier supplier1, Supplier supplier2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Supplier.class);
		String temp = null;
		if(supplier1 != null) {
			if((temp = supplier1.getType()) != null) {
				detachedCriteria.add(Restrictions.eq("type", temp));
			}
		}
		return detachedCriteria;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 根据类型查找
	 */
	public List<Supplier> findByType(String type) {
		System.out.println(type+"-------------------------");
		return (List<Supplier>) this.getHibernateTemplate().find("from Supplier where type = ?", type);
	}

}
