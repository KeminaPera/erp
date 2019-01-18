package com.itheima.erp.biz.impl;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.itheima.erp.biz.IEmpBiz;
import com.itheima.erp.dao.impl.EmpDao;
import com.itheima.erp.entity.Emp;
import com.itheima.erp.exception.ErpException;
/**
 * biz层实现类
 * @author KeminaPera
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private final int HASH_ITERATIONS = 2;
	
	//注入empDao
	private EmpDao empDao;

	public void setEmpDao(EmpDao empDao) {
		this.empDao = empDao;
		super.setBaseDao(this.empDao);
	}

	@Override
	/**
	 * 用户登录
	 */
	public Emp login(String username, String pwd) {
		String password = encrypt(pwd,username);
		System.out.println(password);
		return empDao.login(username, password);
	}

	@Override
	/**
	 * 重写添加用户
	 */
	public void add(Emp emp) {
		//设置默认初始密码
		emp.setPwd("123456");
		String pwd = encrypt(emp.getPwd(), emp.getUsername());
		//添加加密后的密码
		emp.setPwd(pwd);
		empDao.save(emp);
	}

	@Override
	/**
	 * 修改密码
	 */
	public void updatePwd(Long uuid, String oldPwd, String newPwd) {

		Emp emp = findById(uuid);
		//原密码是否正确
		String pwd = encrypt(oldPwd, emp.getUsername());
		System.out.println(pwd);
		System.out.println(emp.getPwd());
		if(pwd.equals(emp.getPwd())) {
			//输入的原密码正确，修改密码
			empDao.updatePwd(uuid, encrypt(newPwd,emp.getUsername()));
		}else {
			//输入的原密码不正确，不让修改密码
			throw new ErpException("原密码不正确！");
		}
	}
	
	/**
	 * 对新密码进行加密
	 * @param source
	 * @param salt
	 * @return
	 */
	private String encrypt(String source, String salt) {
		Md5Hash md5 = new Md5Hash(source, salt, HASH_ITERATIONS);
		return md5.toString();
	}


}
