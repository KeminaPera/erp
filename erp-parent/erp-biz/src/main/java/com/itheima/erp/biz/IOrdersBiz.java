package com.itheima.erp.biz;

import com.itheima.erp.entity.Orders;
/**
 * service层订单接口
 * @author KeminaPera
 *
 */
public interface IOrdersBiz extends IBaseBiz<Orders> {

	void doCheck(Long checkeruuid, Long uuid);

	void doStart(Long starteruuid, Long uuid);

}
