package com.itheima.erp.web.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.itheima.erp.biz.IEmpBiz;
import com.itheima.erp.entity.Emp;
import com.opensymphony.xwork2.ActionContext;

/**
 * 用户登录的action类
 * @author KeminaPera
 *
 */
public class LoginAction {

	//注入EmpBiz
	private IEmpBiz empBiz;
	
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
	private String username;
	private String pwd;
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public void login() {
		try {
			Emp emp = empBiz.login(username, pwd);
			if(emp != null) {
				ActionContext.getContext().getSession().put("loginEmp", emp);
				returnOptionMessage(true, "登录成功！");
			}else {
				returnOptionMessage(false, "用户名或密码错误！");
			}
		}catch(Exception e) {
			e.printStackTrace();
			returnOptionMessage(false, "请重新登录！");
		}
	}
	
	/**
	 * 显示用户名
	 */
	public void showName() {
		Emp emp = (Emp) ActionContext.getContext().getSession().get("loginEmp");
		if(emp != null) {
			returnOptionMessage(true, emp.getUsername());
		}else {
			returnOptionMessage(false, null);
		}
	}
	/**
	 * 用户退出
	 */
	public void logout() {
		try {
			ActionContext.getContext().getSession().remove("loginEmp");
			returnOptionMessage(true, "");
		}catch(Exception e) {
			e.printStackTrace();
			returnOptionMessage(false, "退出失败，请重新退出！");
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
