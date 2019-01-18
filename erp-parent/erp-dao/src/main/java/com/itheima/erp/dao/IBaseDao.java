package com.itheima.erp.dao;

import java.util.List;

public interface IBaseDao<T> {

	List<T> findAll();

	Integer getTotalRecordCount(T t1, T t2);

	List<T> findByPage(T t1, T t2, int begin, Integer pageSize);
	
	void save(T t);

	void delete(T t);

	T findById(Long uuid);

	void update(T t);
}
