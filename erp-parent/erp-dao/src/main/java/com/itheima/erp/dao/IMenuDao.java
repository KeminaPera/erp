package com.itheima.erp.dao;

import com.itheima.erp.entity.Menu;
/**
 * Dao层菜单的接口
 * @author KeminaPera
 *
 */
public interface IMenuDao extends IBaseDao<Menu> {

	Menu findById(String uuid);
}
