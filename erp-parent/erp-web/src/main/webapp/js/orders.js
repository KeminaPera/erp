//操作类型<findAll：查询所有><doCreate:订单录入><doCheck:订单审核><doStart:订单确认><doInStore:入库>
var oper = Request['oper'];
//订单类别 <type=1:采购订单><type=2:销售订单>
var type = Request['type'];
//url后面的参数
var param = "";
var orderState;
var orderDetailuuid;
var supplierOrCustomer_type;
var supplierOrCustomer_name;
var addOrderDlg_title;
var isEditingRowIndex;
var addOrderType;
var inOrOutStore_title;
var inOrOutStore_text;
var inOrOutStore_message;
var inOrOutStore_url;
$(function(){
	if(oper == 'findAll'){
		if(type * 1 == 1){
			//采购订单的查询
			document.title = "采购订单查询";
			param = "?t1.type=1";
			$('#searchOrders').html();
		}
		if(type * 1 == 2){
			//销售订单的查询
			document.title = "销售订单查询";
			param = "?t1.type=2";
		}
		//显示查询条件
		showDiv();
	}
	if(oper == 'doCreate'){
		var title;
		if(type * 1 == 1){
			//采购订单的创建
			document.title = "采购订单录入";
			param = "?t1.type=1&t1.state=0";
			title = '采购申请';
			addOrderDlg_title = '添加采购订单项';
			supplierOrCustomer_type = 1;
			addOrderType = 1;
			supplierOrCustomer_name = '供应商 ';
			$('#supplierOrCudtomer_name').html(supplierOrCustomer_name);
		}
		if(type * 1 == 2){
			//销售订单的创建
			document.title = "销售订单录入";
			param = "?t1.type=2&t1.state=0";
			title = '添加销售订单';
			addOrderDlg_title = '添加销售订单项';
			supplierOrCustomer_type = 2;
			addOrderType = 2;
			supplierOrCustomer_name = '客户';
			$('#supplierOrCudtomer_name').html(supplierOrCustomer_name);
		}
		$('#dg').datagrid({
			toolbar: [{
				text: title,
				iconCls: 'icon-add',
				handler: function(){
					$('#addOrderDlg').dialog('open');
				}
			}]
		});
	}
	if(oper == 'doCheck'){
		if(type * 1 == 1){
			document.title = "采购订单审核";
			param = "?t1.type=1&t1.state=0";
			$('#orderDetail').dialog({
				toolbar:[{
					text:'审核',
					iconCls:'icon-search',
					handler:function(){
						$.ajax({
							url:'ordersAction_doCheck?uuid=' + $('#uuid').html(),
							dataType:'json',
							type:'post',
							success:function(rtn){
								if(rtn.success){
									$.messager.alert("提示", rtn.message, "info",function(){
										$('#orderDetail').dialog('close');
										$('#dg').datagrid('reload');
									});
								}else{
									$.messager.alert("提示", rtn.message, "info");
								}
							}
						});
					}
				}]
			});
		}
	}
	if(oper == 'doStart'){
		if(type * 1 == 1){
			document.title = "采购订单确认";
			param = "?t1.type=1&t1.state=1";
			$('#orderDetail').dialog({
				toolbar:[{
					text:'确认',
					iconCls:'icon-search',
					handler:function(){
						$.ajax({
							url:'ordersAction_doStart?uuid=' + $('#uuid').html(),
							dataType:'json',
							type:'post',
							success:function(rtn){
								if(rtn.success){
									$.messager.alert("提示", rtn.message, "info",function(){
										$('#orderDetail').dialog('close');
										$('#dg').datagrid('reload');
									});
								}else{
									$.messager.alert("提示", rtn.message, "info");
								}
							}
						});
					}
				}],
				
			});
		}
	}
	if(oper == 'doInStore'){
		document.title = "采购订单入库";
		param = "?t1.type=1&t1.state=2";
		inOrOutStore_title = "订单项入库";
		inOrOutStore_text = "入库";
		inOrOutStore_message = "确定要入库吗？";
		inOrOutStore_url = "orderDetailAction_inStore";
		//添加订单项入库操作
		$('#orderItemDetail').datagrid({
			onDblClickRow:function(rowIndex, rowData){
				$('#instore').dialog('open');
				$('#goodsuuid').val(rowData.goodsuuid);
				$('#goodsname').val(rowData.goodsname);
				$('#num').val(rowData.num);
				$('#orderDetailuuid').val(rowData.uuid);
			}
		});
	}
	if(oper == 'doOutStore'){
		document.title = "采购订单出库";
		param = "?t1.type=2&t1.state=0";
		inOrOutStore_title = "订单项出库";
		inOrOutStore_text = "出库";
		inOrOutStore_message = "确定要出库吗？";
		inOrOutStore_url = "orderDetailAction_outStore";
		//添加订单项入库操作
		$('#orderItemDetail2').datagrid({
			onDblClickRow:function(rowIndex, rowData){
				$('#instore').dialog('open');
				$('#goodsuuid').val(rowData.goodsuuid);
				$('#goodsname').val(rowData.goodsname);
				$('#num').val(rowData.num);
				$('#orderDetailuuid').val(rowData.uuid);
			}
		});
	}
	
	if(type * 1 == 1){
		$('#dg').datagrid({
			url : 'ordersAction_findByPage' + param,
			pagination:true,
			singleSelect:true,
			pageList : [3,5,10],
			pageSize : 5,
			onDblClickRow : function(rowIndex, rowData){
				//显示table中的数据
				$('#uuid').html(rowData.uuid);
				$('#supplier').html(rowData.supplier.name);
				$('#state').html(getState(rowData.state));
				$('#creater').html(rowData.creatername);
				$('#checker').html(rowData.checkername);
				$('#starter').html(rowData.startername);
				$('#ender').html(rowData.endername);
				$('#createtime').html(formatDate(rowData.createtime));
				$('#checktime').html(formatDate(rowData.checktime));
				$('#starttime').html(formatDate(rowData.starttime));
				$('#endtime').html(formatDate(rowData.endtime));
				//加载订单项
				$('#orderItemDetail').datagrid('loadData',rowData.orderDetails);
				//显示订单明细
				$('#orderDetail').dialog('open');
			},
			columns:getColumns(type)
		});
	}
	if(type * 1 == 2){
		$('#dg').datagrid({
			url : 'ordersAction_findByPage' + param,
			pagination:true,
			singleSelect:true,
			pageList : [3,5,10],
			pageSize : 5,
			onDblClickRow : function(rowIndex, rowData){
				//显示table中的数据
				$('#uuid2').html(rowData.uuid);
				$('#supplier2').html(rowData.supplier.name);
				$('#state2').html(getState2(rowData.state));
				$('#creater2').html(rowData.creatername);
				$('#ender2').html(rowData.endername);
				$('#createtime2').html(formatDate(rowData.createtime));
				$('#endtime2').html(formatDate(rowData.endtime));
				//加载订单项
				$('#orderItemDetail2').datagrid('loadData',rowData.orderDetails);
				//显示订单明细
				$('#orderDetail2').dialog('open');
			},
			columns:getColumns(type)
		});
	}
	
	
	$('#supplierOrCustomer').combobox({    
	    url:'supplierAction_findAll?t1.type=' + supplierOrCustomer_type,    
	    valueField:'uuid',    
	    textField:'name',
	    editable:false
	});  

	//添加订单窗口
	$('#addOrderDlg').dialog({    
	    title: addOrderDlg_title,    
	    width: 650,    
	    height: 300,    
	    closed: true,    
	    modal: true,
	}); 
	
	//添加订单窗口里面的数据表格(订单明细表格)
	$('#addOrdersItem').datagrid({
		singleSelect:true,
		showFooter:true,
		onClickRow:function(rowIndex, rowData){
			if(validateGrid()){
				$('#addOrdersItem').datagrid('endEdit',isEditingRowIndex);
				isEditingRowIndex = rowIndex;
			}
			$('#addOrdersItem').datagrid('beginEdit',isEditingRowIndex);
			bindEvent();
		},
		toolbar: [{
	    	text:'添加',
			iconCls: 'icon-add',
			handler: function(){
				if(validateGrid()){
					$('#addOrdersItem').datagrid('endEdit',isEditingRowIndex);
					$('#addOrdersItem').datagrid('appendRow',{});
					isEditingRowIndex = $('#addOrdersItem').datagrid('getRows').length - 1;
					$('#addOrdersItem').datagrid('beginEdit',isEditingRowIndex);
					bindEvent();
				}
			}
		},'-',{
	    	text:'提交',
			iconCls: 'icon-save',
			handler: function(){
				//获取供应商或客户，如果为空不能提交
				var value = $('#supplierOrCustomer').combobox('getValue');
				if(value == ""){
					$.messager.alert("提示",supplierOrCustomer_name + '不能为空！',"info");
					return;
				}
				//判断数据表格是否有数据项，没有不能提交
				if($('#addOrdersItem').datagrid('getRows').length * 1 == 0){
					$.messager.alert("提示","请添加商品！","info");
					return;
				}
				//isEditingRowIndex行数据是否合法
				if(!validateGrid()){
					return;
				}
				var formData = $('#addOrdeForm').serializeJSON();
				//获取所有订单项
				var gridData = JSON.stringify($('#addOrdersItem').datagrid('getRows'));
				formData['gridData'] = gridData;
				$.ajax({
					url:'ordersAction_add?t1.type=' + addOrderType,
					type:'post',
					dataType:'json',
					data:formData,
					success:function(rtn){
						if(rtn.success){
							$.messager.alert("提示",rtn.message,"info",function(){
								$('#addOrderDlg').dialog('close');
								$('#dg').datagrid('reload');
								//成功之后清空数据表格和表单
								$('#addOrdersItem').datagrid('loadData',{ total: 0, rows: [] });
								$('#addOrdeForm').form('clear');
								$('#addOrdersItem').datagrid('reloadFooter',[
									{num: '合计', money: 0.00},
								]);
							});
						}else{
							$.messager.alert("提示",rtn.message,"info");
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
	        	onSelect:function(record){
	        		$(getEditor('goodsuuid').target).val(record.uuid);
	        		$(getEditor('price').target).val(record.inprice);
	        		$(getEditor('num').target).select();
	        		cal();
	        	}
	        }}},    
	        {field:'price',title:'价格',width:100,editor:{type:'numberbox',options:{value:0.00,min:0.00,precision:2}}},    
	        {field:'num',title:'数量',width:100,editor:{type:'numberbox',options:{min:1}}},    
	        {field:'money',title:'金额',width:100,editor:{type:'numberbox',options:{disabled:true,precision:2}}},    
	        {field:'-',title:'操作',width:100,formatter:function(value, row, index){
	        	if(row.num != '合计'){
	        		return '<a href="javascript:void(0)" onclick="deleteRow('+index+')">删除</a>';
	        	}
	        }}    
	    ]]
	});
	
	//订单明细窗口
	$('.orderDetail').dialog({    
	    title: '订单明细',    
	    width: 800,    
	    height: 400,    
	    closed: true,    
	    modal: true   
	});    
	//订单项详情
	$('.orderItemDetail').datagrid({ 
		singleSelect:true,
	    columns:[[    
	        {field:'goodsuuid',title:'商品编号',width:100},    
	        {field:'goodsname',title:'商品名称',width:100},    
	        {field:'price',title:'价格',width:100},    
	        {field:'num',title:'数量',width:100},    
	        {field:'money',title:'金额',width:100},    
	        {field:'state',title:'状态',width:100,formatter:getOrderDetailState}    
	    ]]    
	});
	//订单项入库窗口
	$('#instore').dialog({    
	    title: inOrOutStore_title, 
	    width: 400,    
	    height: 250,    
	    closed: true,    
	    modal: true,
	    buttons:[{
			text:inOrOutStore_text,
			handler:function(){
				//检查是否选择了仓库
				var storeData = $('#store').combobox('getValue'); 
				if(storeData == ""){
					$.messager.alert("提示","仓库不能为空！","info");
					return;
				}
				$.messager.confirm("确认", inOrOutStore_message, function(yes){
					if(yes){
						var formData = $('#instoreform').serializeJSON();
						$.ajax({
							url:inOrOutStore_url,
							type:'post',
							dataType:'json',
							data:formData,
							success:function(rtn){
								if(rtn.success){
									//入库成功
									$.messager.alert("提示",rtn.message,'info',function(){
										//关闭入库窗口
										$('#instore').dialog('close');
										//刷新订单明细窗口
									});
								}else{
									$.messager.alert("提示",rtn.message,'info',function(){
										//关闭入库窗口
										$('#instore').dialog('close');
									});
								}
							}
						});
					}
					//不管入库还是不入库都清空表单
					$('#instoreform').form('clear');
				});
			}
		},{
			text:'取消',
			handler:function(){
				//关闭窗口
				$('#instore').dialog('close');
				//清空表单
				$('#instoreform').form('clear');
			}
		}]
	});  
	
	//给查询按钮绑定点击事件
	$('#searchBtn').bind('click', function(){
		var formData = $('#searchOrdersForm').serializeJSON();
		$.ajax({
			url:'ordersAction_findByPage',
			type:'post',
			dataType:'json',
			data:formData,
			success:function(rtn){
				alert(rtn);
			}
		});
	});
	//加载行脚
	$('#addOrdersItem').datagrid('reloadFooter',[
		{num: '合计', money: 0},
	]);
	
	/*//仓库
	$('#_store').combogrid({    
	    panelWidth:450,    
	    value:'006',    
	     
	    idField:'uuid',    
	    textField:'name',    
	    url:'storeAction_findAll',    
	    columns:[[    
	        {field:'uuid',title:'仓库编号',width:60,hidden:true},    
	        {field:'name',title:'仓库名称',width:100},    
	        {field:'goodsname',title:'商品名称',width:120},    
	        {field:'num',title:'数量',width:100}    
	    ]]    
	});  */

});
/**
 * 格式化时间
 */
function formatDate(date){
	return new Date(date).Format("yyyy-MM-dd");
}
/**
 * 根据采购订单状态得到代表的真实含义
 */
function getState(state){
	switch(state){
		case "0" : return '未审批';
		case "1" : return '已审批';
		case "2" : return '已确定';
		case "3" : return '已入库';
		default : return '';
	}
}
/**
 * 根据销售订单状态得到代表的真实含义
 */
function getState2(state){
	switch(state){
		case "0" : return '未出库';
		case "1" : return '已出库';
		default : return '';
	}
}
/**
 * 获取订单明细状态
 */
 function getOrderDetailState(oper){
	 if(type * 1 == 1){
		 switch(oper){
		 case "0": return '未入库';
		 case "1": return '已入库';
		 }
	 }
	 if(type * 1 == 2){
		 switch(oper){
		 case "0": return '未出库';
		 case "1": return '已出库';
		 }
	 }
}
/**
 * 计算每个订单项的总金额
 */
/*function cal(){
	var num = $('#goodsnum').val();
	var price = $('#goodsprice').val();
	$('#goodsmoney').val((price * num).toFixed(2));
}*/
/**
 * 通过订单的类别获取数据表格columns的格式
 */
function getColumns(type){
	if(type * 1 == 1){
		return [[
		{field:'uuid',title:'编号',width:100},    
        {field:'createtime',title:'生成日期',width:100,formatter:formatDate},    
        {field:'checktime',title:'检查日期',width:100,formatter:formatDate}, 
        {field:'starttime',title:'开始日期',width:100,formatter:formatDate}, 
        {field:'endtime',title:'结束日期',width:100,formatter:formatDate}, 
        {field:'creatername',title:'下单员',width:100}, 
        {field:'checkername',title:'审查员',width:100}, 
        {field:'startername',title:'采购员',width:100}, 
        {field:'endername',title:'库管员',width:100}, 
        {field:'supplier',title:'供应商',width:100,formatter:function(value, row, index){
        	return value.name;
        }}, 
        {field:'totalmoney',title:'总金额',width:100}, 
        {field:'state',title:'订单状态',width:100, formatter:getState}
      ]];
	}
	if(type * 1 == 2){
		return [[
		{field:'uuid',title:'编号',width:100},    
        {field:'createtime',title:'生成日期',width:100,formatter:formatDate},    
        {field:'endtime',title:'结束日期',width:100,formatter:formatDate}, 
        {field:'creatername',title:'下单员',width:100}, 
        {field:'endername',title:'库管员',width:100}, 
        {field:'supplier',title:'客户',width:100,formatter:function(value, row, index){
        	return value.name;
        }}, 
        {field:'totalmoney',title:'总金额',width:100}, 
        {field:'state',title:'订单状态',width:100, formatter:getState2}
      ]];
	}
}
/**
 * 删除添加订单中不再需要的订单项
 */
function deleteRow(index){
	if(validateGrid()){
		$.messager.confirm('提示','你确定要删除这行吗？',function(yes){
			if(yes){
				$('#addOrdersItem').datagrid('deleteRow', index);
				//删除选定行之后，再获取所有行数据并重新排序
				$('#addOrdersItem').datagrid('loadData',$('#addOrdersItem').datagrid('getRows'));
				getTotalMoney();
			}
		});
	}
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
 * 获取编辑器并绑定键盘抬起事件
 */
function bindEvent(){
	$(getEditor('price').target).bind('keyup',function(){
		cal();
		//getTotalMoney();
	});
	$(getEditor('num').target).bind('keyup',function(){
		cal();
		//getTotalMoney();
	});
}
/**
 * 计算每个订单项的总金额
 */
function cal(){
	var price = $(getEditor('price').target).val();
	var num = $(getEditor('num').target).val();
	var money = (price * num).toFixed(2);
	$(getEditor('money').target).val(money);
	getTotalMoney();
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
	$('#addOrdersItem').datagrid('reloadFooter',[
		{num: '合计', money: totalMoney.toFixed(2)},
	]);
}
/**
 * 校验添加订单项是否合法
 * @returns false：不合法
 */
function validateGrid(){
	if($('#addOrdersItem').datagrid('getRows').length != 0){
		var rowData = $('#addOrdersItem').datagrid('getRows')[isEditingRowIndex];
		if($.isEmptyObject(rowData)){
			$.messager.alert("提示","请选择商品！","info");
			return false;
		}
		if(rowData.goodsname == ""){
			$.messager.alert("提示","商品名称不能为空！","info");
			return false;
		}
		if(rowData.price == ""){
			$.messager.alert("提示","价格不能为空！","info");
			return false;
		}
		if(rowData.num == ""){
			$.messager.alert("提示","数量不能为空！","info");
			return false;
		}
	}
	return true;
}
/**
 * 订单查询的显示
 */
function showDiv(){
	if(type * 1 == 1){
		//是采购订单,显示采购的订单的查询
		$('#searchDiv1').show();
	}
	if(type * 1 == 2){
		//是销售订单,显示销售订单的查询
		$('#searchDiv2').show();
	}
}