package com.itheima.erp.biz.impl;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.itheima.erp.biz.IBaseBiz;
import com.itheima.erp.dao.IBaseDao;
import com.itheima.erp.entity.PageModel;

public class BaseBiz<T> implements IBaseBiz<T> {

	//注入baseDao
	private IBaseDao<T> baseDao;
	
	public void setBaseDao(IBaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	/**
	 * service层查询所有实体
	 */
	public List<T> findAll() {
		return baseDao.findAll();
	}

	@Override
	/**
	 * service层按一定条件查询并以分页形式返回
	 */
	public PageModel<T> findByPage(T t1, T t2, Integer currPage, Integer pageSize) {
		PageModel<T> pageModel = new PageModel<>();
		if(currPage == null) {
			currPage = 1;
		}
		pageModel.setCurrPage(currPage);
		//调用dao层查询总记录数
		Integer totalRecordCount = baseDao.getTotalRecordCount(t1, t2);
		if(totalRecordCount == null) {
			totalRecordCount = 0;
		}
		pageModel.setTotalRecordCount(totalRecordCount);
		if(pageSize == null) {
			pageSize = totalRecordCount;
		}
		pageModel.setPageSize(pageSize);
		//计算总的页面数
		if(totalRecordCount != null) {
			Double temp = totalRecordCount.doubleValue();
			temp = Math.ceil(temp / pageSize);
			pageModel.setTotalPageCount(temp.intValue());
		}
		//计算开始位置
		int begin = (currPage - 1)*pageSize;
		//查询该页面的数据
		List<T> list = baseDao.findByPage(t1, t2, begin, pageSize);
		pageModel.setList(list);
		return pageModel;
	}

	@Override
	/**
	 * 添加实体
	 */
	public void add(T t) {
		baseDao.save(t);
	}

	@Override
	/**
	 * 删除实体
	 */
	public void delete(Long uuid) {
		T t = baseDao.findById(uuid);
		if(t != null) {
			baseDao.delete(t);
		}
	}

	@Override
	/**
	 * 修改实体
	 */
	public void update(T t) {
		if(t != null) {
			baseDao.update(t);
		}
	}

	@Override
	/**
	 * 根据Id查找
	 */
	public T findById(Long uuid) {
		return baseDao.findById(uuid);
	}

	@Override
	/**
	 * 查询实体的名称
	 */
	public String getEntityNameFromMap(Map<Long, String> map, Long uuid, IBaseDao<?> _baseDao) throws Exception {
		String name = map.get(uuid);
		if(name == null) {
			//获取传入进来Dao的字节码
			Class<?> clazz = _baseDao.getClass();
			Method method = clazz.getMethod("findById", Long.class);
			Object object = method.invoke(_baseDao, uuid);
			//获取实体类的字节码，再获取getName方法
			Type genericSuperclass = clazz.getGenericSuperclass();
			ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
			Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
			Class<?> cla = (Class<?>) actualTypeArguments[0];
			method = cla.getMethod("getName");
			name = (String) method.invoke(object);
			map.put(uuid, name);
			return name;
		}else {
			return name;
		}
	}
}
