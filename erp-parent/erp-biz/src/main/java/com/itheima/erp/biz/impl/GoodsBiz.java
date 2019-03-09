package com.itheima.erp.biz.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.itheima.erp.biz.IGoodsBiz;
import com.itheima.erp.dao.IGoodsDao;
import com.itheima.erp.dao.IGoodsTypeDao;
import com.itheima.erp.entity.Goods;
import com.itheima.erp.entity.GoodsType;
import com.itheima.erp.exception.ErpException;
/**
 * 商品的Biz层实现类
 * @author KeminaPera
 *
 */
public class GoodsBiz extends BaseBiz<Goods> implements IGoodsBiz {

	//注入GoodsDao
	private IGoodsDao goodsDao;

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
		super.setBaseDao(this.goodsDao);
	}
	//注入IGoodsTypeDao
	private IGoodsTypeDao goodsTypeDao;
	
	public void setGoodsTypeDao(IGoodsTypeDao goodsTypeDao) {
		this.goodsTypeDao = goodsTypeDao;
	}

	/**
	 * 导出满足一定条件的商品信息
	 */
	public void export(OutputStream out, Goods goods1, Goods goods2) {
		//从数据库中获取数据
		List<Goods> goodsList = goodsDao.findAll(goods1, goods2);
		//创建工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = workbook.createSheet("商品");
		//设置表头
		HSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("名称");
		headerRow.createCell(1).setCellValue("产地");
		headerRow.createCell(2).setCellValue("厂家");
		headerRow.createCell(3).setCellValue("计量单位");
		headerRow.createCell(4).setCellValue("进货价格");
		headerRow.createCell(5).setCellValue("销售价格");
		headerRow.createCell(6).setCellValue("商品类型");
		
		//创建表体并设置内容
		for(int i = 1; i < goodsList.size() + 1; i++) {
			HSSFRow contentRow = sheet.createRow(i);
			contentRow.createCell(0).setCellValue(goodsList.get(i - 1).getName());
			contentRow.createCell(1).setCellValue(goodsList.get(i - 1).getOrigin());
			contentRow.createCell(2).setCellValue(goodsList.get(i - 1).getProducer());
			contentRow.createCell(3).setCellValue(goodsList.get(i - 1).getUnit());
			contentRow.createCell(4).setCellValue(goodsList.get(i - 1).getInprice());
			contentRow.createCell(5).setCellValue(goodsList.get(i - 1).getOutprice());
			contentRow.createCell(6).setCellValue(goodsList.get(i - 1).getGoodsType().getName());
		}
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
	
	/**
	 * 导入商品信息
	 * @throws IOException 
	 */
	@SuppressWarnings("resource")
	public void doImport(InputStream is) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		String name = workbook.getSheetName(0).toString();
		if(!"商品".equals(name)) {
			throw new ErpException("不是商品表,不能导入");
		}
		HSSFSheet sheet = workbook.getSheet("商品");
		int lastRowNum = sheet.getLastRowNum();
		Goods goods;
		for(int i = 1; i <= lastRowNum; i++) {
			//判断是否已经存在该商品
			goods = new Goods();
			goods.setName(sheet.getRow(i).getCell(0).getStringCellValue());
			List<Goods> list = goodsDao.findAll(null, goods);
			if(list != null && list.size() > 0) {
				goods = list.get(0);
			}
			goods.setOrigin(sheet.getRow(i).getCell(1).getStringCellValue().trim());
			goods.setProducer(sheet.getRow(i).getCell(2).getStringCellValue().trim());
			goods.setUnit(sheet.getRow(i).getCell(3).getStringCellValue().trim());
			goods.setInprice(sheet.getRow(i).getCell(4).getNumericCellValue());
			goods.setOutprice(sheet.getRow(i).getCell(5).getNumericCellValue());
			String goodsTypeName = sheet.getRow(i).getCell(6).getStringCellValue().trim();
			GoodsType goodsType = new GoodsType();
			goodsType.setName(goodsTypeName);
			List<GoodsType> goodsTypes = goodsTypeDao.findAll(null, goodsType);
			if(goodsTypes == null) {
				throw new ErpException("第" + (i + 1) + "行的商品类型不存在！");
			}else {
				goodsType = goodsTypes.get(0);
				goods.setGoodsType(goodsType);
			}
			//如果不存在这样的商品就将其保存到数据库
			if(list == null || list.size() == 0) {
				goodsDao.save(goods);
			}
		}
		workbook.close();
	}
}
