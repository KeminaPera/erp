package com.itheima.erp.biz;

import com.itheima.erp.entity.OrderDetail;

/**
 * service层订单明细实现类
 * @author KeminaPera
 *
 */
public interface IOrderDetailBiz extends IBaseBiz<OrderDetail> {

	void doInStore(Long orderDetailuuid, Long empuuid, Long storeuuid);
	
	void doOutStore(Long orderDetailuuid, Long empuuid, Long storeuuid);
}
