package com.itheima.erp.web.action;

import java.util.List;

import com.itheima.erp.biz.IEmpBiz;
import com.itheima.erp.entity.Emp;
import com.itheima.erp.entity.TreeNode;
/**
 * action层实现类
 * @author KeminaPera
 *
 */
public class EmpAction extends BaseAction<Emp> {

	//注入EmpBiz
	private IEmpBiz empBiz;

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		super.setBaseBiz(this.empBiz);
	}
	private String checkedStr;
	
	public void setCheckedStr(String checkedStr) {
		this.checkedStr = checkedStr;
	}
	//修改密码用的参数
	private String oldPwd;
	private String newPwd;
	
	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd() {
		return newPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	//修改密码
	public void updatePwd() {
		try {
			Emp loginUser = getLoginUser();
			if(loginUser != null) {
				Long uuid = loginUser.getUuid();
				empBiz.updatePwd(uuid, oldPwd, newPwd);
				returnOptionMessage(true, "密码修改成功！");
			}else {
				returnOptionMessage(false, "请重新登录");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnOptionMessage(false, e.getMessage());
		}
	}
	
	/**
	 * 获取用户角色
	 */
	public void readEmpRoles() {
		List<TreeNode> empRoles = empBiz.readEmpRoles(getUuid());
		parseObjectAndWrite(empRoles);
	}
	
	/**
	 * 修改用户角色
	 */
	public void updateEmpRoles() {
		try {
			empBiz.updateEmpRoles(getUuid(), checkedStr);
			returnOptionMessage(true, "修改成功！");
		} catch (Exception e) {
			returnOptionMessage(false, "修改失败！");
			e.printStackTrace();
		}
	}
}
