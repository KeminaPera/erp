package com.itheima.erp.dao;

import java.util.List;

import com.itheima.erp.entity.Supplier;

/**
 * Dao层供应商/客户接口
 * @author KeminaPera
 *
 */
public interface ISupplierDao extends IBaseDao<Supplier> {

	List<Supplier> findByType(String type);

}
