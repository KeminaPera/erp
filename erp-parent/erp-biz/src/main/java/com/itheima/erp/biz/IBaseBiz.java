package com.itheima.erp.biz;

import java.util.List;
import java.util.Map;

import com.itheima.erp.dao.IBaseDao;
import com.itheima.erp.entity.PageModel;
/**
 * service层通用接口
 * @author KeminaPera
 *
 */
public interface IBaseBiz<T> {

	List<T> findAll(T t1, T t2);

	PageModel<T> findByPage(T t1, T t2, Integer currPage, Integer pageSize);	
	
	void add(T t);

	void delete(Long uuid);

	void update(T t);

	T findById(Long uuid);
	
	String getEntityNameFromMap(Map<Long, String> map, Long uuid, IBaseDao<?> baseDao) throws Exception;
}
