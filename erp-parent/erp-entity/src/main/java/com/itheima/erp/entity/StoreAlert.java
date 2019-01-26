package com.itheima.erp.entity;
/**
 * 库存警告实体类 (对应)
 * @author KeminaPera
 *
 */
public class StoreAlert {

	private Long uuid;        //商品编号
	private String name;      //商品名称
	private Integer storenum; //库存量
	private Integer outnum;   //待出库量
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
	public Integer getStorenum() {
		return storenum;
	}
	public void setStorenum(Integer storenum) {
		this.storenum = storenum;
	}
	public Integer getOutnum() {
		return outnum;
	}
	public void setOutnum(Integer outnum) {
		this.outnum = outnum;
	}
	
}
