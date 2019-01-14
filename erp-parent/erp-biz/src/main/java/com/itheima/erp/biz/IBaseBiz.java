package com.itheima.erp.biz;

import java.util.List;

import com.itheima.erp.entity.PageModel;
/**
 * service层通用接口
 * @author KeminaPera
 *
 */
public interface IBaseBiz<T> {

	List<T> findAll();

	PageModel<T> findByPage(T t, Integer currPage, Integer pageSize);	
	
	void add(T t);

	void delete(Long uuid);

	void update(T t);

	T findById(Long uuid);
}
