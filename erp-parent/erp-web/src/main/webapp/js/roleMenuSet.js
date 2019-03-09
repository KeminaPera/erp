$(function(){
	/**
	 * 右侧角色权限设置部分
	 */
	$('#tree').tree({ 
		url:'roleAction_readRoleMenus?uuid=0',
		checkbox:true,
		animate:true,
	}); 
	/**
	 * 左侧角色列表
	 */
	$('#dg').datagrid({    
	    url:'roleAction_findAll',    
	    columns:[[    
	        {field:'uuid',title:'角色ID',width:100},    
	        {field:'name',title:'角色名称',width:100},    
	    ]],
	    singleSelect : true,
	    onClickRow:function(rowIndex, rowData){
	    	$('#tree').tree({ 
	    		url:'roleAction_readRoleMenus?uuid=' + rowData.uuid,
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
		//获取该角色修改后的权限
		var checkedStr = checked.join(",");
		//获取角色id
		var roleUuid =$('#dg').datagrid('getSelected').uuid; 
		//构建表单数据
		var formData = {uuid : roleUuid, checkedStr : checkedStr};
		$.ajax({
			url : 'roleAction_updateRoleMenus',
			data : formData,
			type : 'post',
			dataType : 'json',
			success : function(rtn){
				$.messager.alert('提示',rtn.message,'info');
			}
		});
	});
})
