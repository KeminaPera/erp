package com.itheima.erp.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * biz层报表接口
 * @author KeminaPera
 *
 */
public interface IReportBiz {

	List<?> getOrderReport(Date starttime, Date endtime);

	List<Map<String, Object>> getOrderReportByYear(int year);

}
