$(function(){
	$('#dg').datagrid({    
	    url:'storeDetailAction_getStoreAlertList',    
	    columns:[[    
	        {field:'uuid',title:'商品编号',width:100},    
	        {field:'name',title:'商品名称',width:100},    
	        {field:'storenum',title:'库存量',width:100},    
	        {field:'outnum',title:'待出库量',width:100}    
	    ]]    
	});
});