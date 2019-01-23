var isEditingRowIndex;
$(function(){
	
	//添加采购/销售订单窗口
	$('#addOrdersDlg').dialog({    
	    title: addOrdersDlg.title,    
	    width: 700,    
	    height: 400,    
	    closed: true,    
	    modal: true   
	}); 
	
	//添加采购/销售订单的订单项数据表格
	$('#addOrdersItem').datagrid({
		singleSelect:true,
		rownumbers:true,
		showFooter:true,
		onClickRow : function(rowIndex, rowData){
			//取消上一次可编辑行
			$('#addOrdersItem').datagrid('endEdit', isEditingRowIndex);
			//获取新添加行的index，并让其可编辑
			isEditingRowIndex = rowIndex;
			$('#addOrdersItem').datagrid('beginEdit', isEditingRowIndex);
			//将选中的这行的price和num编辑器绑定键盘抬起事件
			bindEvent();
		},
		toolbar: [{
			text:'添加',
			iconCls: 'icon-add',
			handler: function(){
				if(isEditingRowIndex > -1){
					var isValidate = $('#addOrdersItem').datagrid('validateRow',isEditingRowIndex);
					if(!isValidate){
						$.messager.alert('提示信息','数据不合法，不能存在空字段','info');
						return;
					}
				}
				//取消上一次可编辑行
				$('#addOrdersItem').datagrid('endEdit', isEditingRowIndex);
				//添加一个新行
				$('#addOrdersItem').datagrid('appendRow',{});
				//获取新添加行的index，并让其可编辑
				isEditingRowIndex = $('#addOrdersItem').datagrid('getRows').length - 1;
				$('#addOrdersItem').datagrid('beginEdit', isEditingRowIndex);
				bindEvent();
			}
		},'-',{
			text:'提交',
			iconCls: 'icon-save',
			handler: function(){
				//取消上一次可编辑行
				$('#addOrdersItem').datagrid('endEdit', isEditingRowIndex);
				var formData = $('#ordersForm').serializeJSON();
				var dgData = JSON.stringify($('#addOrdersItem').datagrid('getRows'));
				formData['gridData'] = dgData;
				$.ajax({
					url:'ordersAction_add',
					type:'post',
					dataType:'json',
					data:formData,
					success: function(rtn){
						$.messager.alert('提示信息',rtn.message,'info');
						if(rtn.success){
							$('#addOrdersItem').datagrid('reload');
						}
					}
				});
			}
		}],

	    columns:[[    
	        {field:'goodsuuid',title:'商品编号',width:100,editor:{type:'numberbox',options:{disabled:true}}},    
	        {field:'goodsname',title:'商品名称',width:100,editor:{type:'combobox',options:{
	        	url:'goodsAction_findAll',
	        	valueField:'name',
	        	textField:'name',
	        	editable:false,
	        	required : true,
	        	onSelect : function(record){
	        		//获取商品编号的编辑器并赋值
	        		$(getEditor('goodsuuid').target).numberbox('setValue', record.uuid);
	        		//获取商品价格的编辑器并赋值
	        		$(getEditor('price').target).numberbox('setValue', record.inprice);
	        		//获取商品数量的编辑器并取得其焦点
	        		$(getEditor('num').target).select();
	        		
	        	}
	        }}},    
	        {field:'price',title:'价格',width:100,editor:{type:'numberbox',options:{precision:2,required : true}}},    
	        {field:'num',title:'数量',width:100,editor:'numberbox',editor:{type:'numberbox',options:{value:1,min:1,required : true}}},    
	        {field:'money',title:'金额',width:100,editor:{type:'numberbox',options:{disabled:true,precision:2}}},    
	        {field:'-',title:'操作',width:100,formatter:function(value, row, index){
	        	return '<a href="javascript:void(0)" onclick="del('+index+')">删除</a>';
	        }}    
	    ]]    
	});
	$('#cc').combogrid({    
	    panelWidth:700,    
	    idField:'uuid',    
	    textField:'name',    
	    url:'supplierAction_findAll?t1.type=1',    
	    columns:[[    
	    	{field:'uuid',title:'编号',width:100},
            {field:'name',title:'名称',width:100},
            {field:'address',title:'联系地址',width:100},
            {field:'contact',title:'联系人',width:100},
            {field:'tele',title:'联系电话',width:100},
            {field:'email',title:'邮件地址',width:100}
	    ]]    
	});  
});

/**
 * 订单项的删除
 * @param index 要删除的行下标
 */
function del(index){
	$.messager.confirm('确认消息', '您确认要删除该记录吗？', function(yes){
		if (yes){
			$('#addOrdersItem').datagrid('deleteRow',index);
			var data = $('#addOrdersItem').datagrid('getData');
			$('#addOrdersItem').datagrid('loadData',data);
			getTotalMoney();
		}
	});
}
/**
 * 过去某个字段的编辑器
 * @param fieldName 数据表格的字段名
 * @returns 该字段的编辑器
 */
function getEditor(fieldName){
	return $('#addOrdersItem').datagrid('getEditor', {index:isEditingRowIndex,field:fieldName});
}
/**
 * 计算每个订单项的总金额
 */
function cal(){
	var price = $(getEditor('price').target).val();
	var num = $(getEditor('num').target).val();
	var money = (price * num).toFixed(2);
	$(getEditor('money').target).val(money);
}
/**
 * 获取编辑器并绑定键盘抬起事件
 */
function bindEvent(){
	$(getEditor('price').target).bind('keyup',function(){
		cal();
		getTotalMoney();
	});
	$(getEditor('num').target).bind('keyup',function(){
		cal();
		getTotalMoney();
	});
}
/**
 * 计算该订单的总金额
 */
function getTotalMoney(){
	var totalMoney = 0;
	$('#addOrdersItem').datagrid('endEdit',isEditingRowIndex);
	var rows = $('#addOrdersItem').datagrid('getRows');
	for(var i = 0; i < rows.length; i++){
		totalMoney += parseFloat(rows[i].money);
	}
	$('#totalMoney').html(totalMoney.toFixed(2));
}