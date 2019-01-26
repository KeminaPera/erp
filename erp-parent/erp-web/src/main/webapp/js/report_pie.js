$(function(){
	$('#dg').datagrid({    
	    url:'reportAction_getOrderReport',  
	    onLoadSuccess:function(data){
	    	getPie(data.rows);
	    },
	    columns:[[    
	        {field:'name',title:'商品名称',width:100},    
	        {field:'y',title:'销售额',width:100}    
	    ]]    
	});
	//给查询按钮绑定点击事件
	$('#reportBtn').bind('click', function(){
		var formData = $('#searchForm').serializeJSON();
		if(formData.endtime != ''){
			formData.endtime += ' 23:59:59';
		}
		$('#dg').datagrid('load', formData);
	});
});
function getPie(_data){
	Highcharts.chart('container', {
		chart: {
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false,
			type: 'pie'
		},
		title: {
			text: '销售统计图'
		},
		tooltip: {
			pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
					enabled: false
				},
				showInLegend: true
			}
		},
		series: [{
			name: 'Brands',
			colorByPoint: true,
			data: _data
		}]
	});
}