package com.itheima.erp.biz;

import com.itheima.erp.entity.Emp;
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

	void updatePwd(Long uuid, String oldPwd, String newPwd);
}
