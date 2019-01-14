package com.itheima.erp.dao;

import java.util.List;

public interface IBaseDao<T> {

	List<T> findAll();

	Integer getTotalRecordCount(T t);

	List<T> findByPage(T t, int begin, Integer pageSize);
	
	void save(T t);

	void delete(T t);

	T findById(Long uuid);

	void update(T t);
}
