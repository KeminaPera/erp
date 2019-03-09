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
		},'-',{
			text : '导出',
			iconCls: 'icon-excel',
			handler: function(){
				//获取查询表单数据
				var formData = $('#searchForm').serializeJSON();
				$.download(actionName+'_export' + param,formData);
			}
		},'-',{
			text : '导入',
			iconCls: 'icon-save',
			handler: function(){
				$('#importDlg').dialog('open');
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
	
	//数据导入窗口
	$('#importDlg').dialog({ 
		title:'数据导入',
	    width: 330,    
	    height: 106,    
	    closed: true,    
	    modal: true,
	    buttons:[{
			text:'导入',
			handler:function(){
				//通过ajax上传数据
				$.ajax({
					url:actionName + '_doImport',
					type:'post',
					data:new FormData($('#importForm')[0]),
					processData:false,
					contentType:false,
					dataType:'json',
					success : function(rtn){
						$.messager.alert('提示', rtn.message,'info', function(){
							$('#importDlg').dialog('close');
							$('#importForm').form('clear');
							$('#dg').datagrid('reload');
						});
					}
				});
			}
		},{
			text:'取消',
			handler:function(){
				$('#importDlg').dialog('close');
				$('#importForm').form('clear');
			}
		}]
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