package com.itheima.erp.biz;

import com.itheima.erp.entity.Menu;
/**
 * service层菜单接口
 * @author KeminaPera
 *
 */
public interface IMenuBiz extends IBaseBiz<Menu> {

	Menu findById(String uuid);
	
	/**
	 * 获取登录用户的权限
	 * @param uuid 登录用户ID
	 * @return
	 */
	Menu getMenusByEmpuuid(Long uuid);
	
}
