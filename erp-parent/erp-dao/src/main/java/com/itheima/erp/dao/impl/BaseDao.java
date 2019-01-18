package com.itheima.erp.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.erp.dao.IBaseDao;

public abstract class BaseDao<T> extends HibernateDaoSupport implements IBaseDao<T> {

	private Class<T> clazz;
	@SuppressWarnings("unchecked")
	public BaseDao(){
		//获取带有泛型的直接父类
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		//将其转换成参数化类
		ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
		//获取实际参数数组
		Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
		Type type = actualTypeArguments[0];
		Class<T> clazz = (Class<T>) type;
		this.clazz = clazz;
	}
	//注入SessionFactory
	@SuppressWarnings("unused")
	private SessionFactory sessionFactory;

	/**
	 * 查询所有
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(){
		String name = clazz.getSimpleName();
		System.out.println(name);
		return (List<T>) this.getHibernateTemplate().find("from "+ name);
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取总记录条数
	 */
	public Integer getTotalRecordCount(T t1, T t2) {
		DetachedCriteria detachedCriteria = addCriteria(t1, t2);
		detachedCriteria.setProjection(Projections.rowCount());
		List<Long> rowCount = (List<Long>) this.getHibernateTemplate().findByCriteria(detachedCriteria);
		if(rowCount.size() > 0) {
			return rowCount.get(0).intValue();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 查询该页面的所有数据
	 */
	public List<T> findByPage(T t1, T t2, int begin, Integer pageSize) {
		DetachedCriteria detachedCriteria = addCriteria(t1, t2);
		return (List<T>) this.getHibernateTemplate().findByCriteria(detachedCriteria, begin, pageSize);
	}
	
	/**
	 * 添加实体
	 */
	public void save(T t) {
		this.getHibernateTemplate().save(t);
	}

	@Override
	/**
	 * 根据部门ID查询实体
	 */
	public T findById(Long uuid) {
		return this.getHibernateTemplate().get(clazz, uuid);
	}
	
	@Override
	/**
	 * 删除实体
	 */
	public void delete(T t) {
		this.getHibernateTemplate().delete(t);
	}

	@Override
	/**
	 * 修改实体
	 */
	public void update(T t) {
		this.getHibernateTemplate().update(t);
	}
	/**
	 * 添加离线查询条件
	 */
	public abstract DetachedCriteria addCriteria(T t1, T t2);
}
