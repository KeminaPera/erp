package com.itheima.erp.biz.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itheima.erp.biz.IStoreOperBiz;
import com.itheima.erp.dao.IEmpDao;
import com.itheima.erp.dao.IGoodsDao;
import com.itheima.erp.dao.IStoreDao;
import com.itheima.erp.dao.IStoreOperDao;
import com.itheima.erp.entity.PageModel;
import com.itheima.erp.entity.StoreOper;
/**
 * service层库存变更实现类
 * @author KeminaPera
 *
 */
public class StoreOperBiz extends BaseBiz<StoreOper> implements IStoreOperBiz {

	//注入IStoreOperDao 
	private IStoreOperDao storeOperDao;

	public void setStoreOperDao(IStoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
		super.setBaseDao(this.storeOperDao);
	}
	
	//注入IEmpDao
	private IEmpDao empDao;

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
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
	/**
	 * 根据分页查询
	 */
	public PageModel<StoreOper> findByPage(StoreOper storeOper1, StoreOper storeOper2, Integer currPage, Integer pageSize) {
		PageModel<StoreOper> pageModel = null;
		Map<Long, String> emps = new HashMap<>();
		Map<Long, String> stores = new HashMap<>();
		Map<Long, String> goods = new HashMap<>();
		try {
			pageModel = super.findByPage(storeOper1, storeOper2, currPage, pageSize);
			List<StoreOper> list = pageModel.getList();
			for (StoreOper storeOper : list) {
				storeOper.setEmpname(getEntityNameFromMap(emps, storeOper.getEmpuuid(), empDao));
				storeOper.setStorename(getEntityNameFromMap(stores, storeOper.getStoreuuid(), storeDao));
				storeOper.setGoodsname(getEntityNameFromMap(goods, storeOper.getGoodsuuid(), goodsDao));
			}
			pageModel.setList(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageModel;
	}
	
}
