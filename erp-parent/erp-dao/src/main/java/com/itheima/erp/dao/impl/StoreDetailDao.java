package com.itheima.erp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.itheima.erp.dao.IStoreDetailDao;
import com.itheima.erp.entity.StoreAlert;
import com.itheima.erp.entity.StoreDetail;
/**
 * Dao层仓库库存实现类
 * @author KeminaPera
 *
 */
public class StoreDetailDao extends BaseDao<StoreDetail> implements IStoreDetailDao {

	@Override
	/**
	 * 添加条件
	 */
	public DetachedCriteria addCriteria(StoreDetail storeDetail1, StoreDetail storeDetail2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoreDetail.class);
		if(storeDetail1 != null) {
			Long temp = null;
			if((temp = storeDetail1.getStoreuuid()) != null) {
				detachedCriteria.add(Restrictions.eq("storeuuid", temp));
			}
			if((temp = storeDetail1.getGoodsuuid()) != null) {
				detachedCriteria.add(Restrictions.eq("goodsuuid", temp));
			}
		}
		return detachedCriteria;
	}

	/**
	 * 根据仓库编号和商品编号查询
	 */
	@SuppressWarnings("unchecked")
	public StoreDetail getStoreDetailByStoreuuidAndGoodsuuid(Long storeuuid, Long goodsuuid) {
		List<StoreDetail> storeDetails = (List<StoreDetail>) this.getHibernateTemplate().find("from StoreDetail where storeuuid = ? and goodsuuid = ?", storeuuid, goodsuuid);
		if(storeDetails.size() > 0) {
			return storeDetails.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 获取库存警告列表
	 */
	public List<StoreAlert> getStoreAlertList() {
		String hql = "from StoreAlert where storenum < outnum";
		return (List<StoreAlert>) this.getHibernateTemplate().find(hql);
	}
}
