package com.itheima.erp.biz;

import java.util.List;

import com.itheima.erp.entity.Role;
import com.itheima.erp.entity.TreeNode;
/**
 * 角色BIZ层接口
 * @author KeminaPera
 *
 */
public interface IRoleBiz extends IBaseBiz<Role> {

	/**
	 * 获取所有权限
	 * @param uuid 角色ID
	 * @return
	 */
	List<TreeNode> readRoleMenus(Long uuid);
	
	/**
	 * 更新角色权限
	 * @param roleuuid 角色ID
	 * @param checkedStr 该角色更新后的权限字符串
	 */
	void updateRoleMenus(Long roleuuid, String checkedStr);
}
