package com.itheima.erp.entity;
/**
 * 仓库实体类
 * @author KeminaPera
 *
 */
public class Store {
	
	private Long uuid;      //仓库编号
	private String name;    //仓库名称
	private Long empuuid;   //所属员工
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getEmpuuid() {
		return empuuid;
	}
	public void setEmpuuid(Long empuuid) {
		this.empuuid = empuuid;
	}
	
}
