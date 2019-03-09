package com.itheima.erp.biz;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.itheima.erp.entity.Goods;

/**
 * 商品的Biz层接口
 * @author KeminaPera
 *
 */
public interface IGoodsBiz extends IBaseBiz<Goods> {
	
	/**
	 * 数据导出
	 * @param out 输出流
	 * @param goods1 商品1(主要根据该商品的属性查询)
	 * @param goods2 商品2(主要根据该商品属性的某个区间查询，与商品1搭配使用)
	 */
	void export(OutputStream out, Goods goods1, Goods goods2);
	
	/**
	 * 数据导入
	 * @param is 输入流
	 * @throws IOException
	 */
	void doImport(InputStream is) throws IOException;
}
