package com.itheima.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

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
	
	@Override
	/**
	 * 获取登录用户权限
	 * @param uuid : 登录用户ID
	 */
	public Menu getMenusByEmpuuid(Long uuid) {
		List<Menu> menus = menuDao.getMenusByEmpuuid(uuid);
		Menu menu0 = menuDao.findById("0");
		Menu m0 = createMenu(menu0);
		for(Menu menu1 : menu0.getMenus()) {
			Menu m1 = createMenu(menu1);
			for(Menu m2 : menu1.getMenus()) {
				if(menus.contains(m2)) {
					m1.getMenus().add(m2);
				}
			}
			if(m1.getMenus().size() > 0) {
				m0.getMenus().add(m1);
			}
		}
		return m0;
	}

	/**
	 * 克隆Menu
	 * @param menu 
	 * @return
	 * @param menu
	 * @return
	 */
	private Menu createMenu(Menu menu) {
		Menu m = new Menu();
		m.setIcon(menu.getIcon());
		m.setMenuid(menu.getMenuid());
		m.setMenuname(menu.getMenuname());
		m.setUrl(menu.getUrl());
		m.setMenus(new ArrayList<>());
		return m;
	}
}
