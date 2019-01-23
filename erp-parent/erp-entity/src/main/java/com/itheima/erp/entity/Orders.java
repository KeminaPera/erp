package com.itheima.erp.entity;
/**
 * 订单实体类
 * @author KeminaPera
 *
 */

import java.util.Date;
import java.util.List;

public class Orders {
	
	/** 订单类型：采购订单 */
	public static final String TYPE_IN = "1";
	/** 订单类型：销售订单 */
	public static final String TYPE_OUT = "2";
	/** 采购订单状态:未审核 */
	public static final String STATE_CREATE = "0";
	/** 采购订单状态：已审核 */
	public static final String STATE_CHECK = "1";
	/** 采购订单状态：已确认 */
	public static final String STATE_STATE = "2";
	/** 采购订单状态：已入库*/
	public static final String STATE_END = "3";
	/** 销售订单状态：未出库 */
	public static final String STATE_NOT_OUT = "0";
	/** 销售订单状态：已出库 */
	public static final String STATE_OUT = "1";
	
	private Long uuid;            //编号
	private Date createtime;      //生成时间
	private Date checktime;       //审核时间
	private Date starttime;       //确认时间
	private Date endtime;         //入库或出库时间
	private String type;          //订单类型 1：采购 2：销售
	private Long creater;         //下单员
	private String creatername;   //下单员名称(数据库表中没有该字段)
	private Long checker;         //审核员
	private String checkername;    //审核员名称(数据库表中没有该字段)
	private Long starter;         //采购员
	private String startername;   //采购员名称(数据库表中没有该字段)
	private Long ender;           //库管员
	private String endername;     //库管员名称(数据库表中没有该字段)
	private Supplier supplier;	  //供应商或客户
	private Double totalmoney;    //合计金额
	private String state;         //订单状态 采购(0：未审核 1：已审核 2：已确认 3：已入库) 销售( 0：未出库 1:已出库)
	private Long waybillsn;       //运单号
	
	private List<OrderDetail> orderDetails;   //该订单的所有明细
	
	public Long getUuid() {
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getChecktime() {
		return checktime;
	}
	public void setChecktime(Date checktime) {
		this.checktime = checktime;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getCreater() {
		return creater;
	}
	public void setCreater(Long creater) {
		this.creater = creater;
	}
	public String getCreatername() {
		return creatername;
	}
	public void setCreatername(String creatername) {
		this.creatername = creatername;
	}
	public Long getChecker() {
		return checker;
	}
	public void setChecker(Long checker) {
		this.checker = checker;
	}
	public String getCheckername() {
		return checkername;
	}
	public void setCheckername(String checkername) {
		this.checkername = checkername;
	}
	public Long getStarter() {
		return starter;
	}
	public void setStarter(Long starter) {
		this.starter = starter;
	}
	public String getStartername() {
		return startername;
	}
	public void setStartername(String startername) {
		this.startername = startername;
	}
	public Long getEnder() {
		return ender;
	}
	public void setEnder(Long ender) {
		this.ender = ender;
	}
	public String getEndername() {
		return endername;
	}
	public void setEndername(String endername) {
		this.endername = endername;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Double getTotalmoney() {
		return totalmoney;
	}
	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Long getWaybillsn() {
		return waybillsn;
	}
	public void setWaybillsn(Long waybillsn) {
		this.waybillsn = waybillsn;
	}
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
}
