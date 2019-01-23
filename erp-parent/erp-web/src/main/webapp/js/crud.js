var method = "";
if(name == 'undefined'){
	name = "";
}
if(typeof(param) == 'undefined'){
	param = "";
}
$(function() {
	//数据表格
	$('#dg').datagrid({
		url : actionName+'_findByPage' + param,
		singleSelect : true,
		pagination : true,
		striped : true,
		rownumbers : true,
		pageList : [3, 5 , 10],
		pageSize : 3,
		toolbar: [{
			text : '添加',
			iconCls: 'icon-add',
			handler: function(){
				method = "add";
				$("#editWindow").dialog({
					'closed' : false,
					'title' : '添加' + name
					});
				$('#editForm').form('clear');
			}
		}],
		columns : [ columns ]
	});
	//添加和修改窗口
	$('#editWindow').dialog({    
	    width: 400,    
	    height: 200,    
	    closed: true,    
	    modal: true   
	});
	//添加或修改
	$("#editBtn").bind('click',function(){
		if(!$('#editForm').form('validate')){
			return ;
		};
		var formData = $("#editForm").serializeJSON();
		$.ajax({
			url : actionName+'_'+ method + param,
			data : formData,
			dataType : 'json',
			type : 'post',
			success : function(data){
				$.messager.alert("提示",data.message,'info',function(){
					$("#editWindow").dialog({'closed' : true});
					$("#dg").datagrid('reload');
				});
			}
		});
	});
	//根据条件查询
	$("#searchBtn").bind('click',function(){
		var formData = $('#searchForm').serializeJSON();
		$('#dg').datagrid('load',formData);
	});
	
});

//删除该记录
function del(uuid){
	$.messager.confirm('确认','您确认要删除该记录吗？',function(yes){    
	    if (yes){   
	    	$.ajax({
				url : actionName+'_delete?uuid='+uuid,
				dataType : 'json',
				type : 'post',
				success : function(data){
					$.messager.alert("提示",data.message,'info',function(){
						$("#dg").datagrid('reload');
					});
				}
			});
	    }    
	});
}
//打开编辑窗口并将数据回显
function edit(uuid){
	method = "update";
	$('#editWindow').window({
		'closed' : false,
		'title' : '修改' + name
	});
	$('#editForm').form('clear');
	$('#editForm').form('load',actionName+'_findById?uuid=' + uuid);
}
//清除表单
function clearForm(name){
	$('#' + name).form('clear');
}