package com.itheima.erp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单实体类
 * @author KeminaPera
 *
 */
public class Menu {

	private String menuid;
	private String menuname;
	private String icon;
	private String url;
	//通过自身递归的方式获取所有的菜单
	private List<Menu> menus = new ArrayList<>();
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	@Override
	public String toString() {
		return "Menu [menuid=" + menuid + ", menuname=" + menuname + ", icon=" + icon + ", url=" + url + ", menus="
				+ menus + "]";
	}
	
}
