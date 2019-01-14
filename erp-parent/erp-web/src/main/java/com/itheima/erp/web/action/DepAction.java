package com.itheima.erp.web.action;

import com.itheima.erp.biz.IDepBiz;
import com.itheima.erp.entity.Dep;
/**
 * 部门的action类
 * @author KeminaPera
 *
 */
public class DepAction extends BaseAction<Dep> {

	//注入DepBiz
	private IDepBiz depBiz;

	public void setDepBiz(IDepBiz depBiz) {
		this.depBiz = depBiz;
		super.setBaseBiz(this.depBiz);
	}

}
