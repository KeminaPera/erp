package com.itheima.erp.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itheima.erp.biz.IReportBiz;
import com.itheima.erp.dao.IReportDao;
/**
 * biz层报表实现类
 * @author KeminaPera
 *
 */
public class ReportBiz implements IReportBiz {

	//注入IReportDao
	private IReportDao reportDao;

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}

	@Override
	/**
	 * 销售订单报表
	 */
	public List<?> getOrderReport(Date starttime, Date endtime) {
		return reportDao.getOrderReport(starttime, endtime);
	}

	@Override
	/**
	 * 根据年份获取每个月的销售额
	 */
	public List<Map<String, Object>> getOrderReportByYear(int year) {
		//存储查询返回结果
		Double data = null;
		//存储最终目标结果
		//存储返回结果
		List<Map<String, Object>> returnResult = new ArrayList<>();
		String month = null;
		Double money = null;
		for(int i = 1; i < 13; i++) {
			Map<String, Object> targetData = new HashMap<>();
			data = reportDao.getTotalMoney(year, i);
			month = i + "月";
			if(data == null) {
				money = 0.00;
				targetData.put("month", month);
				targetData.put("money", money);
			}else {
				money = data;
				targetData.put("month", month);
				targetData.put("money", money);
			}
			returnResult.add(targetData);
		}
		return returnResult;
	}
	
}
