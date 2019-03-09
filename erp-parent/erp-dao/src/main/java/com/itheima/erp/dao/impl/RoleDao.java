package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.erp.dao.IRoleDao;
import com.itheima.erp.entity.Role;
/**
 * 角色DAO层实现类
 * @author KeminaPera
 *
 */
public class RoleDao extends BaseDao<Role> implements IRoleDao {

	@Override
	/**
	 * 添加离线查询条件
	 */
	public DetachedCriteria addCriteria(Role t1, Role t2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
		return detachedCriteria;
	}

}
