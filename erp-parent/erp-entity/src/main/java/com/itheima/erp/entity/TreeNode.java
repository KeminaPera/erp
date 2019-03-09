package com.itheima.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限控制部分树节点实体类
 * @author KeminaPera
 *
 */
public class TreeNode implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;                    //节点ID
	private String text;                //节点文本信息
	private boolean checked;            //是否被选中
	private List<TreeNode> children;    //该节点下面的子节点
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public List<TreeNode> getChildren() {
		if(children == null) {
			children = new ArrayList<TreeNode>();
		}
		return children;
	}
	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", text=" + text + ", checked=" + checked + ", children=" + children + "]";
	}
	
}
