package com.itheima.erp.biz.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;

import com.itheima.erp.biz.IEmpBiz;
import com.itheima.erp.dao.impl.EmpDao;
import com.itheima.erp.dao.impl.RoleDao;
import com.itheima.erp.entity.Emp;
import com.itheima.erp.entity.Role;
import com.itheima.erp.entity.TreeNode;
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
	//注入roleDao
	private RoleDao roleDao;

	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
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

	/**
	 * 获取所有角色
	 * @param uuid 用户ID
	 * @return
	 */
	@Override
	public List<TreeNode> readEmpRoles(Long uuid) {
		List<TreeNode> nodes = new ArrayList<>();
		Emp emp = empDao.findById(uuid);
		List<Role> roles = emp.getRoles();
		List<Role> roleList = roleDao.findAll(null, null);
		TreeNode treeNode = null;
		for (Role role : roleList) {
			treeNode = new TreeNode();
			treeNode.setId(role.getUuid());
			treeNode.setText(role.getName());
			if(roles.contains(role)) {
				treeNode.setChecked(true);
			}
			nodes.add(treeNode);
		}
		return nodes;
	}

	/**
	 * 更新用户角色
	 * @param roleuuid 用户ID
	 * @param checkedStr 该用户更新后的角色字符串
	 */
	@Override
	public void updateEmpRoles(Long empuuid, String checkedStr) {
		Emp emp = empDao.findById(empuuid);
		emp.setRoles(new ArrayList<>());
		String[] ids = checkedStr.split(",");
		for (String id : ids) {
			Role role = roleDao.findById(Long.valueOf(id));
			emp.getRoles().add(role);
		}
	}

}
