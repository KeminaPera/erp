package com.itheima.erp.entity;

import java.util.List;

/**
 * 分页的页面实体类
 * @author KeminaPera
 *
 */
public class PageModel<T> {

	private Integer currPage;               //当前页
	private Integer pageSize;               //页面展示的数据条数
	private Integer totalRecordCount;       //总记录数
	private Integer totalPageCount;         //总页面数
	private List<T> list;                   //该页面要展示的数据
	public Integer getCurrPage() {
		return currPage;
	}
	public void setCurrPage(Integer currPage) {
		this.currPage = currPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalRecordCount() {
		return totalRecordCount;
	}
	public void setTotalRecordCount(Integer totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	public Integer getTotalPageCount() {
		return totalPageCount;
	}
	public void setTotalPageCount(Integer totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	
}
