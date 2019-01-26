$(function(){
	$('#dg').datagrid({
		singleSelect:true,
		onLoadSuccess:function(data){
			showChart(data.rows);
		},
		columns: [[
			{field:'month',title:'月份',width:100},
			{field:'money',title:'销售额',width:100}
		]],
		
	});
	$('#searchBtn').bind('click', function(){
		var formData = $('#searchForm').serializeJSON();
		$.ajax({
			url:'reportAction_getOrderReportByYear',
			data:formData,
			type:'post',
			dataType:'json',
			success:function(rtn){
				$('#dg').datagrid('loadData',rtn);
			}
		});
	});
});
/**
 * 趋势图
 */
function showChart(value){
	var chart = Highcharts.chart('container', {
		chart: {
			type: 'line'
		},
		title: {
			text: '销售统计图'
		},
		xAxis: {
			title: {
				text: '月份'
			},
			categories: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
		},
		yAxis: {
			title: {
				text: '销售额'
			},
			categories: ['1k', '2k', '3k', '4k', '5k', '6k', '7k', '8k', '9k', '10k', '11k', '12k']
		},
		plotOptions: {
			line: {
				dataLabels: {
					// 开启数据标签
					enabled: true          
				},
				// 关闭鼠标跟踪，对应的提示框、点击事件会失效
				enableMouseTracking: false
			}
		},
		series: [{
			name: '东京',
			data: $('#dg').datagrid('getRows')
		}]
	});
}