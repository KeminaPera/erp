package com.itheima.erp.entity;

import java.util.List;

/**
 * 角色实体
 * @author KeminaPera
 *
 */
public class Role {

	private Long uuid;       //角色ID
	private String name;     //角色名称
	private List<Menu> menus;//该角色所关联的菜单
	
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
}
