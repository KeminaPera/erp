package com.itheima.erp.web.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itheima.erp.biz.IReportBiz;

/**
 * web层实现类
 * @author KeminaPera
 *
 */
public class ReportAction {

	private IReportBiz reportBiz;

	public void setReportBiz(IReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}
	
	private Date starttime;
	private Date endtime;
	
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	
	private int year;

	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * 获取订单报表(饼状图)
	 */
	public void getOrderReport(){
		List<?> orderReport = reportBiz.getOrderReport(starttime, endtime);
		parseObjectAndWrite(orderReport);
	}
	
	/**
	 * 获取这一年份每个月的销售总额(趋势图)
	 */
	public void getOrderReportByYear() {
		List<Map<String, Object>> list = reportBiz.getOrderReportByYear(year);
		parseObjectAndWrite(list);
	}
	
	/**
	 * 解析结果并返回给客户端
	 * @param object
	 */
	public void parseObjectAndWrite(Object object) {
		//String jsonString = JSON.toJSONString(object);
		//DisableCircularReferenceDetect禁用循环引用
		String jsonString = JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
		//获取HttpServletResponse对象
		HttpServletResponse response = ServletActionContext.getResponse();
		//处理中文乱码问题
		response.setContentType("text/html;charset=utf-8");
		try {
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
