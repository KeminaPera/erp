package com.itheima.erp.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.itheima.erp.dao.IGoodsDao;
import com.itheima.erp.entity.Goods;
/**
 * 商品的Dao层实现类
 * @author KeminaPera
 *
 */
public class GoodsDao extends BaseDao<Goods> implements IGoodsDao {

	@Override
	/**
	 * 添加商品的离线查询条件
	 */
	public DetachedCriteria addCriteria(Goods goods1, Goods goods2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Goods.class);
		if(goods1 != null) {
			String temp = null;
			if((temp = goods1.getName()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("name", temp, MatchMode.ANYWHERE));
			}
			if((temp = goods1.getOrigin()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("origin", temp, MatchMode.ANYWHERE));
			}
			if((temp = goods1.getProducer()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("producer", temp, MatchMode.START));
			}
			if((temp = goods1.getUnit()) != null && temp.trim().length() > 0) {
				detachedCriteria.add(Restrictions.like("unit", temp, MatchMode.ANYWHERE));
			}
			Double price = null;
			if((price = goods1.getInprice()) != null) {
				detachedCriteria.add(Restrictions.ge("inprice", price));
			}
			if((price = goods1.getOutprice()) != null) {
				detachedCriteria.add(Restrictions.ge("outprice", price));
			}
			if(goods1.getGoodsType() != null && goods1.getGoodsType().getUuid() != null) {
				System.out.println(goods1.getGoodsType().getUuid()+"------------------------");
				detachedCriteria.add(Restrictions.eq("goodsType", goods1.getGoodsType()));
			}
		}
		if(goods2 != null) {
			Double price = null;
			if((price = goods2.getInprice()) != null) {
				System.out.println(price);
				detachedCriteria.add(Restrictions.le("inprice", price));
			}
			if((price = goods2.getOutprice()) != null) {
				System.out.println(price);
				detachedCriteria.add(Restrictions.le("outprice", price));
			}
		}
		return detachedCriteria;
	}

}
