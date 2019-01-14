package com.itheima.erp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.itheima.erp.dao.IDepDao;
import com.itheima.erp.entity.Dep;
/**
 * 部门DAO层实现类
 * @author KeminaPera
 *
 */
public class DepDao extends BaseDao<Dep> implements IDepDao {

	@SuppressWarnings("unchecked")
	@Override
	public Integer getTotalRecordCount(Dep dep) {
		DetachedCriteria detachedCriteria = addCriteria(dep);
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> rowCount = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		if(rowCount.size() > 0) {
			return rowCount.get(0).intValue();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dep> findByPage(Dep dep, int begin, Integer pageSize) {
		DetachedCriteria detachedCriteria = addCriteria(dep);
		return (List<Dep>) this.getHibernateTemplate().findByCriteria(detachedCriteria, begin, pageSize);
	}
	
	private DetachedCriteria addCriteria(Dep dep) {
		//创建离线查询条件
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Dep.class);
		if(dep != null) {
			if(dep.getName() != null && dep.getName().trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("name", dep.getName(), MatchMode.ANYWHERE));
			}
			if(dep.getTele() != null && dep.getTele().trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("tele", dep.getTele(), MatchMode.ANYWHERE));
			}
		}
		return detachedCriteria;
	}
}
