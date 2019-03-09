package com.itheima.erp.dao.impl;

import java.util.List;

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

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取登录用户权限
	 * @param uuid ：登录用户ID
	 */
	public List<Menu> getMenusByEmpuuid(Long uuid) {
		String hql = "select m from Emp e join e.roles r join r.menus m where e.uuid = ?";
		List<Menu> menuList = (List<Menu>) this.getHibernateTemplate().find(hql, uuid);
		return menuList;
	}
}
