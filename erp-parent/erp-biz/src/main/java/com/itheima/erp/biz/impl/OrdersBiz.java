package com.itheima.erp.biz.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itheima.erp.biz.IOrdersBiz;
import com.itheima.erp.dao.IEmpDao;
import com.itheima.erp.dao.IOrdersDao;
import com.itheima.erp.entity.Emp;
import com.itheima.erp.entity.OrderDetail;
import com.itheima.erp.entity.Orders;
import com.itheima.erp.entity.PageModel;
import com.itheima.erp.exception.ErpException;
/**
 * service层订单实现类
 * @author KeminaPera
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	private IEmpDao empDao;

	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		super.setBaseDao(this.ordersDao);
	}
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	@Override
	/**
	 * 分页查询订单
	 */
	public PageModel<Orders> findByPage(Orders t1, Orders t2, Integer currPage, Integer pageSize) {
		PageModel<Orders> pageModel = super.findByPage(t1, t2, currPage, pageSize);
		List<Orders> orders = pageModel.getList();
		//创建容器
		Map<Long, String> emps = new HashMap<>();
		for (Orders order : orders) {
			Long createruuid = order.getCreater();
			if(createruuid != null) {
				order.setCreatername(getEmpNameFromMap(emps,createruuid));
			}
			Long checkeruuid = order.getChecker();
			if(checkeruuid != null) {
				order.setCheckername(getEmpNameFromMap(emps,checkeruuid));
			}
			Long starteruuid = order.getStarter();
			if(starteruuid != null) {
				order.setStartername(getEmpNameFromMap(emps,starteruuid));
			}
			Long enderuuid = order.getEnder();
			if(enderuuid != null) {
				order.setEndername(getEmpNameFromMap(emps,enderuuid));
			}
		}
		pageModel.setList(orders);
		return pageModel;
	}
	
	/**
	 * 获取员工姓名
	 * @param emps 创建的缓存
	 * @param uuid 员工ID
	 * @return 员工姓名
	 */
	private String getEmpNameFromMap(Map<Long, String> emps, Long uuid) {
		String name = emps.get(uuid);
		if(name == null) {
			Emp emp = empDao.findById(uuid);
			emps.put(emp.getUuid(), emp.getName());
			return emp.getName();
		}else {
			return name;
		}
	}


	@Override
	/**
	 * 添加订单
	 */
	public void add(Orders order) {
		//定义该订单的总金额
		Double totalMoney = 0.0;
		List<OrderDetail> orderDetails = order.getOrderDetails();
		for (OrderDetail orderDetail : orderDetails) {
			if(order.getType().equals("1")) {
				orderDetail.setState(OrderDetail.STATE_NOT_IN);
			}
			if(order.getType().equals("2")) {
				orderDetail.setState(OrderDetail.STATE_NOT_OUT);
			}
			orderDetail.setOrders(order);
			//计算总金额
			totalMoney += orderDetail.getMoney();
		}
		//订单生成日期
		order.setCreatetime(new Date());
		//设置订单类型位采购订单
		if(order.getType().equals("1")) {
			order.setType(Orders.TYPE_IN);
		}
		if(order.getType().equals("2")) {
			order.setType(Orders.TYPE_OUT);
		}
		//设置订单的状态位未审核
		order.setState(Orders.STATE_CREATE);
		//添加该订单的总金额
		order.setTotalmoney(totalMoney);
		ordersDao.save(order);
	}

	@Override
	/**
	 * 审核订单
	 */
	public void doCheck(Long checkeruuid, Long uuid) {
		//通过uuid获取到该订单
		Orders order = ordersDao.findById(uuid);
		if(!order.getState().equals(Orders.STATE_CREATE)) {
			throw new ErpException("该订单状态不是未审核，不需要审核");
		}
		//设置审核时间
		order.setChecktime(new Date());
		//设置审核人
		order.setChecker(checkeruuid);
		//修改订单状态
		order.setState(Orders.STATE_CHECK);
		ordersDao.update(order);
	}
	
	/**
	 * 确认订单
	 */
	public void doStart(Long starteruuid, Long uuid) {
		//通过ID查找该订单
		Orders order = ordersDao.findById(uuid);
		if(!order.getState().equals(Orders.STATE_CHECK)) {
			throw new ErpException("该订单不是已审核，所以不需要确认");
		}
		//添加确认时间
		order.setStarttime(new Date());
		//添加确认人
		order.setStarter(starteruuid);
		//修改订单状态
		order.setState(Orders.STATE_STATE);
		ordersDao.update(order);
	}
	
}
