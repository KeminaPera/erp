package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itheima.erp.dao.IDepDao;
import com.itheima.erp.entity.Dep;
/**
 * 部门DAO层实现类
 * @author KeminaPera
 *
 */
public class DepDao extends BaseDao<Dep> implements IDepDao {

	public DetachedCriteria addCriteria(Dep dep1, Dep dep2) {
		//创建离线查询条件
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Dep.class);
		if(dep1 != null) {
			if(dep1.getName() != null && dep1.getName().trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("name", dep1.getName(), MatchMode.ANYWHERE));
			}
			if(dep1.getTele() != null && dep1.getTele().trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("tele", dep1.getTele(), MatchMode.ANYWHERE));
			}
		}
		return detachedCriteria;
	}
}
