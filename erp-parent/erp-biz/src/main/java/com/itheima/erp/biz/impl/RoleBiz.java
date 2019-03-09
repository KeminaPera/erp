package com.itheima.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

import com.itheima.erp.biz.IRoleBiz;
import com.itheima.erp.dao.IMenuDao;
import com.itheima.erp.dao.IRoleDao;
import com.itheima.erp.entity.Menu;
import com.itheima.erp.entity.Role;
import com.itheima.erp.entity.TreeNode;
/**
 * 角色BIZ层实现类
 * @author KeminaPera
 *
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {
	
	//根节点常量
	private final String ROOT_ID = "0";
	//注入IRoleDao
	private IRoleDao roleDao;

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		super.setBaseDao(this.roleDao);
	}
	
	//注入IMenuDao
	private IMenuDao menuDao;

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}
	
	/**
	 * 获取角色权限
	 * @param uuid 角色ID
	 */
	public List<TreeNode> readRoleMenus(Long uuid){
		//获取该角色
		Role role = roleDao.findById(uuid);
		//获取该角色拥有的权限
		List<Menu> menus = role.getMenus();
		List<TreeNode> treeNodes = new ArrayList<>();
		//拿到根菜单
		Menu rootMenu = menuDao.findById(ROOT_ID);
		//一级菜单
		for(Menu m1 : rootMenu.getMenus()) {
			//构建树节点
			TreeNode treeNode = new TreeNode();
			treeNode.setId(Long.valueOf(m1.getMenuid()));
			treeNode.setText(m1.getMenuname());
			//二级菜单
			for(Menu m2 : m1.getMenus()) {
				TreeNode treeNode2 = new TreeNode();
				treeNode2.setId(Long.valueOf(m2.getMenuid()));
				treeNode2.setText(m2.getMenuname());
				//如果该用户的权限列表中包含该权限则设置为true
				if(menus.contains(m2)) {
					System.out.println(menus.size());
					treeNode2.setChecked(true);
				}
				treeNode.getChildren().add(treeNode2);
			}
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}
	
	/**
	 * 更新角色权限
	 * @param roleuuid 角色ID
	 * @param checkedStr 该角色更新后的权限字符串
	 */
	public void updateRoleMenus(Long roleuuid, String checkedStr) {
		//通过ID获取该角色实体
		Role role = roleDao.findById(roleuuid);
		//创建一个新的权限集合
		ArrayList<Menu> menus = new ArrayList<>();
		//删除掉以前的权限
		role.setMenus(menus);
		//获取新的权限数组
		String[] menuIds = checkedStr.split(",");
		for (String menuId : menuIds) {
			Menu menu = menuDao.findById(menuId);
			menus.add(menu);
		}
		role.setMenus(menus);
	}

}
