$(function(){
	$('#dg').datagrid({    
	    url:'storeOperAction_findByPage', 
	    pagination:true,
	    singleSelect:true,
	    pageList:[3, 5, 10],
	    columns:[[    
	        {field:'uuid',title:'编号',width:100},    
	        {field:'empname',title:'员工',width:100},    
	        {field:'opertime',title:'操作日期',width:150,formatter:getDate},    
	        {field:'storename',title:'仓库',width:100},    
	        {field:'goodsname',title:'商品名称',width:100},    
	        {field:'num',title:'数量',width:100},    
	        {field:'type',title:'类型',width:100,formatter:getType},    
	    ]]    
	}); 
	
	$('#searchBtn').bind('click',function(){
		var formData = $('#searchStoreOperForm').serializeJSON();
		$('#dg').datagrid('load',formData);
	});
});
/**
 * 获取操作类型1：入库 2：出库
 * @param type
 * @returns
 */
function getType(type){
	switch(type * 1){
		case 1: return '入库';
		case 2: return '出库';
	}
}
function getDate(date){
	return (new Date(date)).Format("yyyy-MM-dd hh:mm:ss");
}