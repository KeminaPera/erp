package com.itheima.erp.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.itheima.erp.biz.IGoodsBiz;
import com.itheima.erp.entity.Goods;
import com.itheima.erp.exception.ErpException;
/**
 * 商品的Action类
 * @author KeminaPera
 *
 */
public class GoodsAction extends BaseAction<Goods> {

	//注入IGoodsBiz
	private IGoodsBiz goodsBiz;

	public void setGoodsBiz(IGoodsBiz goodsBiz) {
		this.goodsBiz = goodsBiz;
		super.setBaseBiz(this.goodsBiz);
	}
	
	//属性注入
	private File file;
	private String fileFileName;
	private String fileContentType;
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * 商品信息的导出
	 */
	public void export() {
		HttpServletResponse response = ServletActionContext.getResponse();
		String fileName = "商品.xls";
		try {
			response.setHeader("Content-Disposition", "attachment;filename="+new String(fileName.getBytes(),"ISO-8859-1"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		goodsBiz.export(outputStream, getT1(), getT2());
	}
	
	/**
	 * 商品信息的导入
	 */
	public void doImport() {
		if(!"application/vnd.ms-excel".equals(getFileContentType())) {
			returnOptionMessage(false, "文件格式不是excel格式");
			return;
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(getFile());
			goodsBiz.doImport(fis);
			returnOptionMessage(true, "导入成功!");
		} catch (ErpException e) {
			e.printStackTrace();
			returnOptionMessage(false, e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			returnOptionMessage(false, "导入失败！");
		}
	}
}
