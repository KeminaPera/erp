package com.itheima.erp.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.itheima.erp.dao.IStoreDetailDao;
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
	public DetachedCriteria addCriteria(StoreDetail t1, StoreDetail t2) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StoreDetail.class);
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
}
