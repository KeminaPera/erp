package com.itheima.erp.entity;
/**
 * 库存变更记录表
 * @author KeminaPera
 *
 */

import java.util.Date;

public class StoreOper {

	/**入库操作**/
	public static final String TYPE_IN = "1";
	/**出库操作**/
	public static final String TYPE_OUT = "2";
	private Long uuid;         //编号
	private Long empuuid;      //操作员工编号
	private String empname;    //操作员工姓名
	private Date opertime;     //操作日期
	private Long storeuuid;    //仓库编号
	private String storename;  //仓库名称
	private Long goodsuuid;    //商品编号
	private String goodsname;  //商品名称
	private Long num;          //数量
	private String type;       //操作类型 1：入库 2：出库
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Long getEmpuuid() {
		return empuuid;
	}
	public void setEmpuuid(Long empuuid) {
		this.empuuid = empuuid;
	}
	public Date getOpertime() {
		return opertime;
	}
	public void setOpertime(Date opertime) {
		this.opertime = opertime;
	}
	public Long getStoreuuid() {
		return storeuuid;
	}
	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}
	public Long getGoodsuuid() {
		return goodsuuid;
	}
	public void setGoodsuuid(Long goodsuuid) {
		this.goodsuuid = goodsuuid;
	}
	public Long getNum() {
		return num;
	}
	public void setNum(Long num) {
		this.num = num;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getStorename() {
		return storename;
	}
	public void setStorename(String storename) {
		this.storename = storename;
	}
	public String getGoodsname() {
		return goodsname;
	}
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname;
	}
	
}
