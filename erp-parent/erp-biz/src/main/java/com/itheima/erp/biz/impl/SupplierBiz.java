package com.itheima.erp.biz.impl;

import java.util.List;

import com.itheima.erp.biz.ISupplierBiz;
import com.itheima.erp.dao.ISupplierDao;
import com.itheima.erp.entity.Supplier;
/**
 * Biz层供应商/客户实现类
 * @author KeminaPera
 *
 */
public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

	private ISupplierDao supplierDao;

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		super.setBaseDao(this.supplierDao);
	}

	@Override
	/**
	 * 根据类型查找
	 */
	public List<Supplier> findByType(String type) {
		return supplierDao.findByType(type);
	}
	
}
