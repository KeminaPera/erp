package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.erp.dao.IGoodsTypeDao;
import com.itheima.erp.entity.GoodsType;
/**
 * Dao层商品种类的实现类
 * @author KeminaPera
 *
 */
public class GoodsTypeDao extends BaseDao<GoodsType> implements IGoodsTypeDao {

	@Override
	public DetachedCriteria addCriteria(GoodsType goodsType1, GoodsType goodsType2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(GoodsType.class);
		return detachedCriteria;
	}

}
