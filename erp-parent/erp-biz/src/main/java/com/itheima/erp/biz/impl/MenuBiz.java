package com.itheima.erp.biz.impl;

import com.itheima.erp.biz.IMenuBiz;
import com.itheima.erp.dao.IMenuDao;
import com.itheima.erp.entity.Menu;
/**
 * service层菜单实现类
 * @author KeminaPera
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {
	
	//注入IMenuBiz
	private IMenuDao menuDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		super.setBaseDao(this.menuDao);
	}

	@Override
	/**
	 * 根据String类型的Id查找
	 */
	public Menu findById(String uuid) {
		return menuDao.findById(uuid);
	}

}
