package com.itheima.erp.biz;

import java.util.List;

import com.itheima.erp.entity.Emp;
import com.itheima.erp.entity.TreeNode;
/**
 * biz层员工接口
 * @author KeminaPera
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp> {

	/**
	 * service层用户登录
	 */
	Emp login(String username, String pwd);

	/**
	 * 修改密码
	 * @param uuid 用户ID
	 * @param oldPwd 原始密码
	 * @param newPwd 新密码
	 */
	void updatePwd(Long uuid, String oldPwd, String newPwd);
	
	/**
	 * 获取所有角色
	 * @param uuid 用户ID
	 * @return
	 */
	List<TreeNode> readEmpRoles(Long uuid);
	
	/**
	 * 更新用户角色
	 * @param roleuuid 用户ID
	 * @param checkedStr 该用户更新后的角色字符串
	 */
	void updateEmpRoles(Long empuuid, String checkedStr);
}
