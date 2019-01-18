package com.itheima.erp.web.action;

import com.itheima.erp.biz.IEmpBiz;
import com.itheima.erp.entity.Emp;
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
}
