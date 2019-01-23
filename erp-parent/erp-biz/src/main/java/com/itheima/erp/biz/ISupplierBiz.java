package com.itheima.erp.biz;

import java.util.List;

import com.itheima.erp.entity.Supplier;

/**
 * Biz层供应商/客户接口
 * @author KeminaPera
 *
 */
public interface ISupplierBiz extends IBaseBiz<Supplier> {

	List<Supplier> findByType(String type);

}
