package com.itheima.erp.dao;

import java.util.List;

import com.itheima.erp.entity.Menu;
/**
 * Dao层菜单的接口
 * @author KeminaPera
 *
 */
public interface IMenuDao extends IBaseDao<Menu> {

	/**
	 * 根据Id查询权限
	 * @param uuid
	 * @return
	 */
	Menu findById(String uuid);
	
	/**
	 * 获取登录用户的权限
	 * @param uuid 登录用户ID
	 * @return
	 */
	List<Menu> getMenusByEmpuuid(Long uuid);
}
