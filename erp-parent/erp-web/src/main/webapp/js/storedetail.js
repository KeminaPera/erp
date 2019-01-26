$(function(){
	$('#dg').datagrid({    
	    url:'storeDetailAction_findByPage', 
	    pagination:true,
	    singleSelect:true,
	    pageList:[3, 5, 10],
	    onClickRow:function(rowIndex, rowData){
	    	$('#_storename').html(rowData.storename);
	    	$('#_goodsname').html(rowData.goodsname);
	    	$('#storeOperDlg').dialog('open');
	    	$('#_dg').datagrid('load',{'t1.goodsuuid':rowData.goodsuuid,'t1.storeuuid':rowData.storeuuid});
	    },
	    columns:[[    
	        {field:'uuid',title:'编号',width:100},    
	        {field:'storeuuid',title:'仓库编号',width:100,hidden:true},    
	        {field:'storename',title:'仓库名称',width:100},    
	        {field:'goodsuuid',title:'商品编号',width:100,hidden:true},    
	        {field:'goodsname',title:'商品名称',width:100},    
	        {field:'num',title:'数量',width:100}    
	    ]]    
	});  
	//给查询按钮绑定点击事件
	$('#searchBtn').bind('click',function(){
		var formData = $('#searchStoredetailForm').serializeJSON();
		$('#dg').datagrid('reload',formData);
	});
	//仓库操作记录窗口
	$('#storeOperDlg').dialog({    
	    title: '库存变动记录',    
	    width: 600,    
	    height: 300,    
	    closed: true,    
	    modal: true   
	});
	//仓库操作窗口的数据表格
	$('#_dg').datagrid({
		url:'storeOperAction_findByPage',
		pagination:true,
		singleSelect:true,
		pageList:[3, 5, 10],
	    columns:[[    
	        {field:'uuid',title:'编号',width:100},    
	        {field:'opertime',title:'操作日期',width:150,formatter:getDate},    
	        {field:'num',title:'数量',width:100},    
	        {field:'type',title:'类型',width:100,formatter:getState}    
	    ]]    
	});
});
function getState(state){
	switch(state * 1){
		case 1: return '入库';
		case 2: return '出库';
	}
}
/**
 * 格式化时间
 * @param date
 * @returns
 */
function getDate(date){
	return (new Date(date).Format('yyyy-MM-dd hh:mm:ss'));
}