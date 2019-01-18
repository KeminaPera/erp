package com.itheima.erp.biz;

import com.itheima.erp.entity.Menu;
/**
 * service层菜单接口
 * @author KeminaPera
 *
 */
public interface IMenuBiz extends IBaseBiz<Menu> {

	Menu findById(String uuid);
}
