package com.itheima.erp.web.action;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.itheima.erp.biz.IOrdersBiz;
import com.itheima.erp.entity.Emp;
import com.itheima.erp.entity.OrderDetail;
import com.itheima.erp.entity.Orders;
import com.itheima.erp.exception.ErpException;

/**
 * action层订单实现类
 * @author KeminaPera
 *
 */
public class OrdersAction extends BaseAction<Orders> {

	private IOrdersBiz ordersBiz;

	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		super.setBaseBiz(this.ordersBiz);
	}
	
	//用来封装前端数据表格中的数据
	private String gridData;

	public void setGridData(String gridData) {
		this.gridData = gridData;
	}

	@Override
	/**
	 * 添加采购订单
	 */
	public void add() {
		Emp loginUser = getLoginUser();
		if(loginUser == null) {
			returnOptionMessage(false, "您还没有登录，请先登录！");
			return ;
		}
		try {
			Orders order = getT1();
			System.out.println(gridData);
			List<OrderDetail> orderDetails = JSON.parseArray(gridData, OrderDetail.class);
			//创建订单实例
			order.setOrderDetails(orderDetails);
			order.setCreater(loginUser.getUuid());
			ordersBiz.add(order);
			returnOptionMessage(true, "添加订单成功！");
		} catch (Exception e) {
			e.printStackTrace();
			returnOptionMessage(false, "添加订单失败！");
		}
	}
	
	/**
	 * 审核订单
	 */
	public void doCheck() {
		Emp loginUser = getLoginUser();
		if(loginUser == null) {
			returnOptionMessage(false, "您还没有登录，请先登录！");
			return ;
		}
		try {
			ordersBiz.doCheck(loginUser.getUuid(), getUuid());
			returnOptionMessage(true, "审核成功！");
		}catch(ErpException e) {
			e.printStackTrace();
			returnOptionMessage(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			returnOptionMessage(false, "审核失败！");
		}
	}
	
	/**
	 * 确认订单
	 */
	public void doStart() {
		Emp emp = getLoginUser();
		if(emp == null) {
			returnOptionMessage(false, "您还没有登录，请先登录！");
			return;
		}
		try {
			ordersBiz.doStart(emp.getUuid(), uuid);
			returnOptionMessage(true, "确认成功！");
		}catch (ErpException e) {
			e.printStackTrace();
			returnOptionMessage(false, e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			returnOptionMessage(false, "确认失败！");
		}
	}
}
