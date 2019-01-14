package com.itheima.erp.biz.impl;

import com.itheima.erp.biz.IDepBiz;
import com.itheima.erp.dao.IDepDao;
import com.itheima.erp.entity.Dep;

/**
 * 部门service层实现类
 * @author KeminaPera
 *
 */
public class DepBiz extends BaseBiz<Dep> implements IDepBiz {

	//注入depDao
	private IDepDao depDao;
	
	public void setDepDao(IDepDao depDao) {
		this.depDao = depDao;
		super.setBaseDao(this.depDao);
	}

}
