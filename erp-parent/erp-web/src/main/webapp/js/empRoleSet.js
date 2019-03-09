$(function(){
	/**
	 * 右侧用户权限设置部分
	 */
	$('#tree').tree({ 
		url:'empAction_readEmpRoles?uuid=0',
		checkbox:true,
		animate:true,
	}); 
	/**
	 * 左侧用户列表
	 */
	$('#dg').datagrid({    
	    url:'empAction_findAll',    
	    columns:[[    
	        {field:'uuid',title:'用户ID',width:100},    
	        {field:'name',title:'用户名称',width:100},    
	    ]],
	    singleSelect : true,
	    onClickRow:function(rowIndex, rowData){
	    	$('#tree').tree({ 
	    		url:'empAction_readEmpRoles?uuid=' + rowData.uuid,
	    		checkbox:true,
	    		animate:true,
	    	});
	    }
	});
	/**
	 * 设置权限保存操作
	 */
	$('#saveBtn').bind('click', function(){
		var nodes = $('#tree').tree('getChecked');
		var checked = new Array();
		$(nodes).each(function(index, node){
			checked[index] = node.id;
		});
		//获取该用户修改后的权限
		var checkedStr = checked.join(",");
		//获取用户id
		var roleUuid =$('#dg').datagrid('getSelected').uuid; 
		//构建表单数据
		var formData = {uuid : roleUuid, checkedStr : checkedStr};
		$.ajax({
			url : 'empAction_updateEmpRoles',
			data : formData,
			type : 'post',
			dataType : 'json',
			success : function(rtn){
				$.messager.alert('提示',rtn.message,'info');
			}
		});
	});
})
