package com.itheima.erp.web.action;

import com.itheima.erp.biz.IMenuBiz;
import com.itheima.erp.entity.Menu;
/**
 * 菜单的action类
 * @author KeminaPera
 *
 */
public class MenuAction extends BaseAction<Menu> {

	//注入IMenuBiz
	private IMenuBiz menuBiz;
	
	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		super.setBaseBiz(this.menuBiz);
	}

	/**
	 * 获取菜单树（所有的菜单）
	 */
	public void getMenutree() {
		Menu menu = menuBiz.findById("0");
		this.parseObjectAndWrite(menu);
	}
}
