package com.itheima.erp.dao;

import java.util.Date;
import java.util.List;

/**
 * Dao层报表接口
 * @author KeminaPera
 *
 */
public interface IReportDao {

	List<?> getOrderReport(Date starttime, Date endtime);

	Double getTotalMoney(int year,int month);

}
