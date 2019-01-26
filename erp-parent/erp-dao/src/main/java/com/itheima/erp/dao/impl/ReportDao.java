package com.itheima.erp.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.itheima.erp.dao.IReportDao;
import com.itheima.erp.entity.Orders;
/**
 * Dao层报表实现类
 * @author KeminaPera
 *
 */
public class ReportDao extends HibernateDaoSupport implements IReportDao {

	/**
	 * 获取销售订单报表
	 * @return
	 */
	public List<?> getOrderReport(Date starttime, Date endtime){
		String hql = "select new Map(gt.name as name, sum(ol.money) as y) "
				+ "from Orders o, OrderDetail ol, Goods g, GoodsType gt "
				+ "where o.uuid = ol.orders and g.uuid = gt.uuid and ol.goodsuuid = g.uuid and o.type="+Orders.TYPE_OUT+" ";
		List<Date> dateList = new ArrayList<>();
		if(starttime != null) {
			hql += "and o.createtime >= ? ";
			dateList.add(starttime);
		}
		if(endtime != null) {
			hql += "and o.createtime <= ? ";
			dateList.add(endtime);
		}
		hql += "group by gt.name";
		List<?> list = this.getHibernateTemplate().find(hql,dateList.toArray());
		for (Object object : list) {
			System.out.println(object);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 根据年份和月份查询每个月销售额
	 */
	public Double getTotalMoney(int year, int month) {
		String hql="select sum(od.money) "
				+ "from OrderDetail od,Orders o "
				+ "where od.orders=o and o.type=? "
				+ "and year(o.createtime)=? and month(o.createtime)=?";
		List<Double> list = (List<Double>) this.getHibernateTemplate().find(hql,Orders.TYPE_OUT, year, month);
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
