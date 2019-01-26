package com.itheima.erp.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itheima.erp.biz.IStoreDetailBiz;
import com.itheima.erp.dao.IGoodsDao;
import com.itheima.erp.dao.IStoreDao;
import com.itheima.erp.dao.IStoreDetailDao;
import com.itheima.erp.entity.PageModel;
import com.itheima.erp.entity.StoreAlert;
import com.itheima.erp.entity.StoreDetail;
/**
 * Service层仓库库存实现类
 * @author KeminaPera
 *
 */
public class StoreDetailBiz extends BaseBiz<StoreDetail> implements IStoreDetailBiz {

	//注入IStoreDetailDao storeDetailDao
	private IStoreDetailDao storeDetailDao;

	public void setStoreDetailDao(IStoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
		super.setBaseDao(this.storeDetailDao);
	}
	
	//注入IStoreDao 
	private IStoreDao storeDao;

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}
	
	//注入IGoodsDao
	private IGoodsDao goodsDao;

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	@Override
	public PageModel<StoreDetail> findByPage(StoreDetail storeDetail1, StoreDetail storeDetail2, Integer currPage, Integer pageSize) {
		PageModel<StoreDetail> pageModel = null;
		Map<Long, String> stores = new HashMap<>();
		Map<Long, String> goods = new HashMap<>();
		try {
			pageModel= super.findByPage(storeDetail1, storeDetail2, currPage, pageSize);
			List<StoreDetail> list = pageModel.getList();
			for (StoreDetail storeDetail : list) {
				storeDetail.setStorename(getEntityNameFromMap(stores, storeDetail.getStoreuuid(), storeDao));
				storeDetail.setGoodsname(getEntityNameFromMap(goods, storeDetail.getGoodsuuid(), goodsDao));
			}
			pageModel.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageModel;
	}

	@Override
	/**
	 * 获取库存警告列表
	 */
	public List<StoreAlert> getStoreAlertList() {
		return storeDetailDao.getStoreAlertList();
	}
	
	
}
