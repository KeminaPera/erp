package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.erp.dao.IMenuDao;
import com.itheima.erp.entity.Menu;
/**
 * Dao层菜单的实现类
 * @author KeminaPera
 *
 */
public class MenuDao extends BaseDao<Menu> implements IMenuDao {

	@Override
	public Menu findById(String uuid) {
		System.out.println(uuid);
		return this.getHibernateTemplate().get(Menu.class, uuid);
	}

	@Override
	public DetachedCriteria addCriteria(Menu t1, Menu t2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Menu.class);
		return detachedCriteria;
	}

}
