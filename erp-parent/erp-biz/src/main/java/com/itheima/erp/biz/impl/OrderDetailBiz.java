package com.itheima.erp.biz.impl;

import java.util.Date;
import java.util.List;

import com.itheima.erp.biz.IOrderDetailBiz;
import com.itheima.erp.dao.IOrderDetailDao;
import com.itheima.erp.dao.IStoreDetailDao;
import com.itheima.erp.dao.IStoreOperDao;
import com.itheima.erp.entity.OrderDetail;
import com.itheima.erp.entity.Orders;
import com.itheima.erp.entity.StoreDetail;
import com.itheima.erp.entity.StoreOper;
import com.itheima.erp.exception.ErpException;
/**
 * service层订单明细实现类
 * @author KeminaPera
 *
 */
public class OrderDetailBiz extends BaseBiz<OrderDetail> implements IOrderDetailBiz {

	//注入IOrderDetailDao orderDetailDao
	private IOrderDetailDao orderDetailDao;

	public void setOrderDetailDao(IOrderDetailDao orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
		super.setBaseDao(this.orderDetailDao);
	}

	//注入IStoreDetailDao storeDetailDao
	private IStoreDetailDao storeDetailDao;
	
	public void setStoreDetailDao(IStoreDetailDao storeDetailDao) {
		this.storeDetailDao = storeDetailDao;
	}
	//注入IStoreOperDao storeOperDao
	private IStoreOperDao storeOperDao;

	public void setStoreOperDao(IStoreOperDao storeOperDao) {
		this.storeOperDao = storeOperDao;
	}


	/**
	 * 入库操作
	 */
	public void doInStore(Long orderDetailuuid, Long empuuid, Long storeuuid) {
		/**修改orderDetail**/
		//查询该订单项
		OrderDetail orderDetail = orderDetailDao.findById(orderDetailuuid);
		if(!orderDetail.getOrders().getState().equals(Orders.STATE_STATE)){
			throw new ErpException("该订单还没有确定，请先确定！");
		}
		//修改订单项状态位已入库
		orderDetail.setState(OrderDetail.STATE_IN);
		//设置ender
		orderDetail.setEnder(empuuid);
		//设置endtime
		orderDetail.setEndtime(new Date());
		//设置storeuuid
		orderDetail.setStoreuuid(storeuuid);
		/**修改仓库库存信息**/
		//查看该仓库是否存有该商品信息
		StoreDetail storeDetail = storeDetailDao.getStoreDetailByStoreuuidAndGoodsuuid(storeuuid, orderDetail.getGoodsuuid());
		if(storeDetail == null) {
			//没有，插入数据
			storeDetail = new StoreDetail();
			storeDetail.setStoreuuid(storeuuid);
			storeDetail.setGoodsuuid(orderDetail.getGoodsuuid());
			storeDetail.setNum(orderDetail.getNum());
			//存入数据库
			storeDetailDao.save(storeDetail);
		}else {
			//有，取出该商品存有的数量，并加上新增的数量，并写入数据库
			storeDetail.setNum(storeDetail.getNum() + orderDetail.getNum());
		}
		/**向库存变更表中插入变更的信息**/
		//创建StoreOper实例
		StoreOper storeOper = new StoreOper();
		//设置员工编号
		storeOper.setEmpuuid(empuuid);
		//设置操作日期
		storeOper.setOpertime(orderDetail.getEndtime());
		//设置仓库编号
		storeOper.setStoreuuid(storeuuid);
		//设置商品编号
		storeOper.setGoodsuuid(orderDetail.getGoodsuuid());
		//设置数量
		storeOper.setNum(orderDetail.getNum());
		//设置类型1：入库 2：出库
		storeOper.setType(StoreOper.TYPE_IN);
		//保存进数据库
		storeOperDao.save(storeOper);
		/**判断是否该订单的所有订单项都已经入库**/
		//定义boolean类型变量，true标志全部入库，false反之
		boolean allIn = true;
		//获取该订单的所有订单项，并遍历
		List<OrderDetail> orderDetails = orderDetail.getOrders().getOrderDetails();
		for (OrderDetail aOrderDetail : orderDetails) {
			if(aOrderDetail.getState().equals(OrderDetail.STATE_NOT_IN)) {
				//获取订单项状态如果state为0，修改boolean值，并break
				allIn = false;
				break;
			}
		}
		//判断boolean值
		if(allIn) {
			//如果为true，修改订单的状态为已入库
			orderDetail.getOrders().setState(Orders.STATE_END);
			//设置库管员
			orderDetail.getOrders().setEnder(empuuid);
			//设置订单完成时间
			orderDetail.getOrders().setEndtime(orderDetail.getEndtime());
		}
	}
		/**
		 * 出库操作
		 */
	public void doOutStore(Long orderDetailuuid, Long empuuid, Long storeuuid) {
		/**修改orderDetail**/
		//查询该订单项
		OrderDetail orderDetail = orderDetailDao.findById(orderDetailuuid);
		if(orderDetail.getOrders().getState().equals(Orders.STATE_OUT)){
			throw new ErpException("该订单已出库，不能重复操作！");
		}
		//修改订单项状态位已出库
		orderDetail.setState(OrderDetail.STATE_OUT);
		//设置ender
		orderDetail.setEnder(empuuid);
		//设置endtime
		orderDetail.setEndtime(new Date());
		//设置storeuuid
		orderDetail.setStoreuuid(storeuuid);
		/**修改仓库库存信息**/
		//查看该仓库是否存有该商品信息
		StoreDetail storeDetail = storeDetailDao.getStoreDetailByStoreuuidAndGoodsuuid(storeuuid, orderDetail.getGoodsuuid());
		if(storeDetail == null) {
			//没有，抛出异常
			throw new ErpException("该仓库没有该商品！");
		}else {
			//有，取出该商品存有的数量，并减去出库的数量，并写入数据库
			if(storeDetail.getNum() - orderDetail.getNum() < 0) {
				throw new ErpException("商品数量不够！");
			}
			storeDetail.setNum(storeDetail.getNum() - orderDetail.getNum());
		}
		/**向库存变更表中插入变更的信息**/
		//创建StoreOper实例
		StoreOper storeOper = new StoreOper();
		//设置员工编号
		storeOper.setEmpuuid(empuuid);
		//设置操作日期
		storeOper.setOpertime(orderDetail.getEndtime());
		//设置仓库编号
		storeOper.setStoreuuid(storeuuid);
		//设置商品编号
		storeOper.setGoodsuuid(orderDetail.getGoodsuuid());
		//设置数量
		storeOper.setNum(orderDetail.getNum());
		//设置类型1：入库 2：出库
		storeOper.setType(StoreOper.TYPE_OUT);
		//保存进数据库
		storeOperDao.save(storeOper);
		/**判断是否该订单的所有订单项都已经出库**/
		//定义boolean类型变量，true标志全部出库，false反之
		boolean allOut = true;
		//获取该订单的所有订单项，并遍历
		List<OrderDetail> orderDetails = orderDetail.getOrders().getOrderDetails();
		for (OrderDetail aOrderDetail : orderDetails) {
			if(aOrderDetail.getState().equals(OrderDetail.STATE_NOT_OUT)) {
				//获取订单项状态如果state为0，修改boolean值，并break
				allOut = false;
				break;
			}
		}
		//判断boolean值
		if(allOut) {
			//如果为true，修改订单的状态为已出库
			orderDetail.getOrders().setState(Orders.STATE_END);
			//设置库管员
			orderDetail.getOrders().setEnder(empuuid);
			//设置订单完成时间
			orderDetail.getOrders().setEndtime(orderDetail.getEndtime());
		}
	}
}
