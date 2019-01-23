package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

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
		if(goodsType1 != null) {
			String temp = null;
			if((temp = goodsType1.getName()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("name", temp, MatchMode.ANYWHERE));
			}
		}
		return detachedCriteria;
	}

}
