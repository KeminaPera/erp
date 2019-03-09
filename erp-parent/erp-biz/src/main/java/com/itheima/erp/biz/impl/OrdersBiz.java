package com.itheima.erp.biz.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import com.itheima.erp.biz.IOrdersBiz;
import com.itheima.erp.dao.IEmpDao;
import com.itheima.erp.dao.IOrdersDao;
import com.itheima.erp.dao.ISupplierDao;
import com.itheima.erp.entity.Emp;
import com.itheima.erp.entity.OrderDetail;
import com.itheima.erp.entity.Orders;
import com.itheima.erp.entity.PageModel;
import com.itheima.erp.entity.Supplier;
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
	
	private ISupplierDao supplierDao;

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
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
	
	/**
	 * 导出订单
	 */
	public  void export(OutputStream out, Orders orders1, Orders orders2) {
	}
	
	/**
	 * 导出某个订单
	 */
	public void exportById(OutputStream out, Long uuid) {
		Orders orders = ordersDao.findById(uuid);
		String title = "";
		if(Orders.TYPE_IN.equals(orders.getType())) {
			title = "采购单";
		}
		if(Orders.TYPE_OUT.equals(orders.getType())) {
			title = "销售单";
		}
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(title);
		HSSFCellStyle content_style = workbook.createCellStyle();
		content_style.setBorderBottom(BorderStyle.THIN);
		content_style.setBorderTop(BorderStyle.THIN);
		content_style.setBorderLeft(BorderStyle.THIN);
		content_style.setBorderRight(BorderStyle.THIN);
		HSSFFont content_font = workbook.createFont();
		content_font.setFontName("宋体");
		content_font.setFontHeightInPoints((short)11);
		content_style.setFont(content_font);
		//水平剧中
		HSSFRow row = null;
		HSSFCell cell = null;
		sheet.createRow(0).createCell(0);
		for(int i = 0; i < 7 + orders.getOrderDetails().size(); i++) {
			row = sheet.createRow(i + 2);
			for(int j = 0; j < 4; j++) {
				cell = row.createCell(j);
				cell.setCellStyle(content_style);
			}
		}
		//合并单元格得到目标的表格形式
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 3));
		sheet.addMergedRegion(new CellRangeAddress(7, 7, 0, 3));
		//设置数据
		sheet.getRow(0).getCell(0).setCellValue("采购单");
		sheet.getRow(2).getCell(0).setCellValue("供应商");
		sheet.getRow(3).getCell(0).setCellValue("下单日期");
		sheet.getRow(4).getCell(0).setCellValue("审核日期");
		sheet.getRow(5).getCell(0).setCellValue("确认日期");
		sheet.getRow(6).getCell(0).setCellValue("结束日期");
		
		sheet.getRow(3).getCell(2).setCellValue("经办人");
		sheet.getRow(4).getCell(2).setCellValue("经办人");
		sheet.getRow(5).getCell(2).setCellValue("经办人");
		sheet.getRow(6).getCell(2).setCellValue("经办人");
		
		sheet.getRow(7).getCell(0).setCellValue("订单明细");
		
		sheet.getRow(8).getCell(0).setCellValue("商品名称");
		sheet.getRow(8).getCell(1).setCellValue("价格");
		sheet.getRow(8).getCell(2).setCellValue("数量");
		sheet.getRow(8).getCell(3).setCellValue("金额");
		
		//设置行高
		sheet.getRow(0).setHeight((short)1000);
		for(int i = 2; i < 8; i++) {
			sheet.getRow(i).setHeight((short)500);
		}
		//设置列宽
		for(int j = 0; j < 4; j++) {
			sheet.setColumnWidth(j, 5000);
		}
		//设置水平居中
		content_style.setAlignment(HorizontalAlignment.CENTER);
		content_style.setVerticalAlignment(VerticalAlignment.CENTER);
		//设置表头
		HSSFCellStyle header_style = workbook.createCellStyle();
		//设置表头字体
		HSSFFont header_font = workbook.createFont();
		header_font.setBold(true);
		header_font.setFontHeightInPoints((short)18);
		header_font.setFontName("黑体");
		header_style.setFont(header_font);
		header_style.setAlignment(HorizontalAlignment.CENTER);
		header_style.setVerticalAlignment(VerticalAlignment.CENTER);
		sheet.getRow(0).getCell(0).setCellStyle(header_style);
		//日期格式化
		HSSFCellStyle style_date = workbook.createCellStyle();
		style_date.cloneStyleFrom(content_style);
		HSSFDataFormat dataFormat = workbook.createDataFormat();
		style_date.setDataFormat(dataFormat.getFormat("yyyy-MM-dd"));
		sheet.getRow(3).getCell(1).setCellStyle(style_date);
		sheet.getRow(4).getCell(1).setCellStyle(style_date);
		sheet.getRow(5).getCell(1).setCellStyle(style_date);
		sheet.getRow(6).getCell(1).setCellStyle(style_date);
		//设置内容
		Supplier supplier = supplierDao.findById(orders.getSupplier().getUuid());
		sheet.getRow(2).getCell(1).setCellValue(supplier.getName());
		if(orders.getCreatetime() != null) {
			sheet.getRow(3).getCell(1).setCellValue(orders.getCreatetime());
		}
		if(orders.getChecktime() != null) {
			sheet.getRow(4).getCell(1).setCellValue(orders.getChecktime());
		}
		if(orders.getStarttime() != null) {
			sheet.getRow(5).getCell(1).setCellValue(orders.getStarttime());
		}
		if(orders.getEndtime() != null) {
			sheet.getRow(6).getCell(1).setCellValue(orders.getEndtime());
		}
		//设置经办人
		if(orders.getCreater() != null) {
			Emp creater = empDao.findById(orders.getCreater());
			sheet.getRow(3).getCell(3).setCellValue(creater.getName());
		}
		if(orders.getChecker() != null) {
			Emp checker = empDao.findById(orders.getChecker());
			sheet.getRow(4).getCell(3).setCellValue(checker.getName());
		}
		if(orders.getStarter() != null) {
			Emp starter = empDao.findById(orders.getStarter());
			sheet.getRow(5).getCell(3).setCellValue(starter.getName());
		}
		if(orders.getEnder() != null) {
			Emp ender = empDao.findById(orders.getEnder());
			sheet.getRow(6).getCell(3).setCellValue(ender.getName());
		}
		//订单明细
		int rowIndex = 9;
		for(OrderDetail od : orders.getOrderDetails()) {
			sheet.getRow(rowIndex).getCell(0).setCellValue(od.getGoodsname());
			sheet.getRow(rowIndex).getCell(1).setCellValue(od.getPrice());
			sheet.getRow(rowIndex).getCell(2).setCellValue(od.getNum());
			sheet.getRow(rowIndex).getCell(3).setCellValue(od.getMoney());
			rowIndex++;
		}
		//设置表尾
		HSSFCellStyle foot_style = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short)11);
		foot_style.setFont(font);
		foot_style.setAlignment(HorizontalAlignment.CENTER);
		foot_style.setVerticalAlignment(VerticalAlignment.CENTER);
		
		HSSFRow createRow = sheet.createRow(rowIndex);
		createRow.setRowStyle(foot_style);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0 , 2));
		createRow.createCell(3).setCellValue("合计："+orders.getTotalmoney());
		try {
			workbook.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}
