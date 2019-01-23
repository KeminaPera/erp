package com.itheima.erp.web.action;

import com.itheima.erp.biz.IOrderDetailBiz;
import com.itheima.erp.entity.Emp;
import com.itheima.erp.entity.OrderDetail;
import com.itheima.erp.exception.ErpException;

/**
 * action层订单详情实现类
 * @author KeminaPera
 *
 */
public class OrderDetailAction extends BaseAction<OrderDetail> {

	private IOrderDetailBiz orderDetailBiz;
	
	public void setOrderDetailBiz(IOrderDetailBiz orderDetailBiz) {
		this.orderDetailBiz = orderDetailBiz;
		super.setBaseBiz(this.orderDetailBiz);
	}
	
	private Long storeuuid;
	private Long orderDetailuuid;

	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}

	public void setOrderDetailuuid(Long orderDetailuuid) {
		this.orderDetailuuid = orderDetailuuid;
	}


	/**
	 * 订单项入库
	 */
	public void inStore() {
		Emp loginUser = getLoginUser();
		if(loginUser == null) {
			returnOptionMessage(false, "您还没有登录，请先登录！");
			return;
		}
		try {
			orderDetailBiz.doInStore(orderDetailuuid, loginUser.getUuid(), storeuuid);
			returnOptionMessage(true, "入库成功！");
		} catch(ErpException e) {
			returnOptionMessage(false, e.getMessage());
		} catch (Exception e) {
			returnOptionMessage(false, "入库失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 订单项出库
	 */
	public void doOutStore(){
		Emp loginUser = getLoginUser();
		if(loginUser == null) {
			returnOptionMessage(false, "您还没有登录，请先登录！");
			return;
		}
		try {
			orderDetailBiz.doOutStore(orderDetailuuid, loginUser.getUuid(), storeuuid);
			returnOptionMessage(true, "出库成功！");
		} catch (ErpException e) {
			e.printStackTrace();
			returnOptionMessage(false, e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			returnOptionMessage(false, "出库失败！");
		}
	}
}
