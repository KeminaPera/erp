package com.itheima.erp.entity;
/**
 * 商品实体类
 * @author KeminaPera
 *
 */
public class Goods {

	private Long uuid;        //商品编号
	private String name;      //商品名称
	private String origin;    //商品产地
	private String producer;  //商品厂家
	private String unit;      //计量单位（斤、公斤）
	private Double inprice;   //进货价格
	private Double outprice;  //销售价格
	private GoodsType goodsType;  //商品类型

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
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Double getInprice() {
		return inprice;
	}
	public void setInprice(Double inprice) {
		this.inprice = inprice;
	}
	public Double getOutprice() {
		return outprice;
	}
	public void setOutprice(Double outprice) {
		this.outprice = outprice;
	}
	public GoodsType getGoodsType() {
		return goodsType;
	}
	public void setGoodsType(GoodsType goodsType) {
		this.goodsType = goodsType;
	}
	
}
