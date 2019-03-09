package com.itheima.erp.web.action;

import java.util.List;

import com.itheima.erp.biz.IRoleBiz;
import com.itheima.erp.entity.Role;
import com.itheima.erp.entity.TreeNode;
/**
 * action层角色实现类
 * @author KeminaPera
 *
 */
public class RoleAction extends BaseAction<Role> {

	private IRoleBiz roleBiz;

	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
		super.setBaseBiz(this.roleBiz);
	}
	
	/**
	 * 封装该角色权限ID转换成的字符串
	 */
	private String checkedStr;
	
	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}

	/**
	 * 获取该角色的权限列表
	 */
	public void readRoleMenus() {
		List<TreeNode> readRoleMenus = roleBiz.readRoleMenus(getUuid());
		parseObjectAndWrite(readRoleMenus);
	}
	
	/**
	 * 更新角色权限
	 */
	public void updateRoleMenus() {
		try {
			roleBiz.updateRoleMenus(getUuid(), checkedStr);
			returnOptionMessage(true, "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			returnOptionMessage(false, "修改失败！");
		}
	}
}
