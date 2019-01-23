package com.itheima.erp.web.action;

import java.util.List;

import com.itheima.erp.biz.ISupplierBiz;
import com.itheima.erp.entity.Supplier;

/**
 * 供应商/客户action层实现类
 * @author KeminaPera
 *
 */
public class SupplierAction extends BaseAction<Supplier> {

	private ISupplierBiz supplierBiz;

	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		super.setBaseBiz(this.supplierBiz);
	}

	/**
	 * 重写findAll方法
	 * 原因：由于供应商和客户存储在同一张表中，通过表中type字段来标识，由于父类中没有参数，无法实现该需求
	 */
	@Override
	public void findAll() {
		String type = this.getT1().getType();
		List<Supplier> list = supplierBiz.findByType(type);
		parseObjectAndWrite(list);
	}
	
	
}
