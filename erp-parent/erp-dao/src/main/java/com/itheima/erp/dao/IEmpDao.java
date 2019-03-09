package com.itheima.erp.dao;

import com.itheima.erp.entity.Emp;
/**
 * dao层员工接口
 * @author KeminaPera
 *
 */
public interface IEmpDao extends IBaseDao<Emp> {

	/**
	 * 用户登录
	 */
	Emp login(String username, String pwd);
	
}
