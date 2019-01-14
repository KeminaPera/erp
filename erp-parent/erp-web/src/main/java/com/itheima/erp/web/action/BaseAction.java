package com.itheima.erp.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itheima.erp.biz.IBaseBiz;
import com.itheima.erp.entity.PageModel;
/**
 * action层公共类
 * @author KeminaPera
 *
 * @param <T>
 */
public class BaseAction<T> {

	//属性驱动
	private T t;
	
	public T getT() {
		return t;
	}
	
	public void setT(T t) {
		this.t = t;
	}

	//属性驱动注入当前页以及页面大小
	private Integer page;
	private Integer rows;
	
	public void setPage(Integer page) {
		this.page = page;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
	
	//获取ID
	private Long uuid;

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	//注入BaseBiz
	private IBaseBiz<T> baseBiz;

	public void setBaseBiz(IBaseBiz<T> baseBiz) {
		this.baseBiz = baseBiz;
	}

	/**
	 * 查询所有部门
	 */
	public void findAll() {
			//调用业务层
			List<T> list = baseBiz.findAll();
			parseObjectAndWrite(list);
	}
	
	/**
	 * 按一定条件查询，并以分页形式返回数据
	 */
	public void findByPage() {
		//调用service层
		PageModel<T> pageModel = baseBiz.findByPage(t, page, rows);
		//将pageModel转换成前端所需要的数据并将json数据返回
		Map<String, Object> map = new HashMap<>();
		map.put("total", pageModel.getTotalRecordCount());
		map.put("rows", pageModel.getList());
		parseObjectAndWrite(map);
	}
	
	/**
	 * 添加实体
	 */
	public void add() {
		try {
			baseBiz.add(t);
			returnOptionMessage(true, "添加成功！");
		} catch (Exception e) {
			returnOptionMessage(false, "添加失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除实体
	 */
	public void delete() {
		try {
			baseBiz.delete(uuid);
			returnOptionMessage(true, "删除成功！");
		} catch (Exception e) {
			returnOptionMessage(false, "删除失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据ID查询
	 */
	public void findById() {
		T aT = baseBiz.findById(uuid);
		if(aT != null) {
			String jsonString = JSONObject.toJSONString(aT);
			convertJsonString(jsonString, "t");
		}
	}
	
	/**
	 * 将JSONstring转换成所需要的字符串
	 */
	public void convertJsonString(String jsonString, String prefix) {
		Map<String, Object> oldMap = new HashMap<>();
		Map<String, Object> newMap = new HashMap<>();
		oldMap = JSON.parseObject(jsonString);
		for (String key : oldMap.keySet()) {
			newMap.put(prefix + "." + key, oldMap.get(key));
		}
		parseObjectAndWrite(newMap);
	}
	
	/**
	 * 编辑部门
	 */
	public void update() {
		try {
			System.out.println(t);
			baseBiz.update(t);
			returnOptionMessage(true, "修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			returnOptionMessage(false, "修改失败");
		}
	}
	
	/**
	 * 抽取公共方法：返回操作结果信息
	 */
	private void returnOptionMessage(boolean result, String message) {
		Map<String, Object> map = new HashMap<>();
		map.put("success", result);
		map.put("message", message);
		parseObjectAndWrite(map);
	}

	/**
	 * 抽取公共方法：用于将object转换成json并返回
	 */
	private void parseObjectAndWrite(Object object) {
		String jsonString = JSON.toJSONString(object);
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
