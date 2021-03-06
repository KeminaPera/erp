package com.itheima.erp.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 员工实体类
 * @author KeminaPera
 *
 */
public class Emp {

	private Long uuid;          //员工编号
	private String username;    //登录名称
	@JSONField(serialize = false)
	private String pwd;         //密码
	private String name;        //姓名
	private Long gender;      //性别
	private String email;       //邮箱
	private String tele;        //员工电话
	private String address;     //员工地址
	private Date birthday;      //员工生日
	private Dep dep;            //所属部门
	private List<Role> roles;   //用户角色
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getGender() {
		return gender;
	}
	public void setGender(Long gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTele() {
		return tele;
	}
	public void setTele(String tele) {
		this.tele = tele;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Dep getDep() {
		return dep;
	}
	public void setDep(Dep dep) {
		this.dep = dep;
	}
	public List<Role> getRoles() {
		if(roles == null) {
			roles = new ArrayList<>();
		}
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
