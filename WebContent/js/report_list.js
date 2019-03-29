var pageNo = 1;
var count = 0;
var totalPage = 1;
var pageSize = 15;
$(function() {
	laydate.render({
		  elem: '#reportTime', //指定元素
		  type: 'month'
		});
	doA();
})

function doA() {
	pageSize = $("#select_pagesize option:selected").val();
	$("#reportFormList  tr").empty("");
	var MercName =$('#Merchants_infoSerach').val();
	var reportTime = $('#reportTime').val();
    $.ajax({
        url: "../../report/getAllReportFormInfo",
        data: {
        	merchantName : MercName,
        	reportTime : reportTime,
        	pageNo : pageNo,
        	pageSize : pageSize
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	action(result.RESULTJSON);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }
        
    })
}

// 渲染回调
function action(json) {
	var data = json;
	$("#label_totalpage")[0].innerHTML = "0";
	if(null == json || "" == json || "undefined" == json){
		return;
	}
    var trList = "";
    var list = data.reportFormInfoList;
    count = data.count;
    totalPage = Math.ceil(count / pageSize);
    $("#label_totalpage")[0].innerHTML = totalPage;
    var index = 0;
    for (i in list) {
        trList += "<tr>" +
            "<td id='td_id_" + index + "'>" + list[i].merchantId + "</td>" +
            "<td>" + list[i].merchantName + "</td>" +
            "<td>" + list[i].reportTime + "</td>" +
            "<td>" + list[i].orderNum + "</td>" +
            "<td>" + Fen2Yuan(list[i].orderMoney) +"元"+ "</td>" +
            "<td>" + Fen2Yuan(list[i].hasPayMoney) +"元"+ "</td>" +
            "<td>" + Fen2Yuan(list[i].hasRefundMoney) +"元"+ "</td>" +
            "<td>" + Fen2Yuan(list[i].canBalanceBefore) +"元"+ "</td>" +
            "<td>" + Fen2Yuan(list[i].canBalanceAfter) +"元"+ "</td>" +
            "<td align='center'>" +
            "<div><a  onclick='queryDetail(" + list[i].merchantId + ")'>" +
				"<i class='glyphicon glyphicon-search' aria-hidden='true'></i>" +"查看订单明细"+
			"</a></div>" +
            "</td>" +
            "</tr>";
        index++;
    }
    $("#reportFormList").find('tr').eq(0).before(trList);
}
function Fen2Yuan(num) {
	if (typeof num !== "number" || isNaN( num )) return null;
	return (num / 100).toFixed( 2 );
}


function queryDetail(merchantId){
	var queryTime = $('#reportTime').val();
	window.location.href="order_list_detail.html?merchantId="+merchantId+"&queryTime="+queryTime;
}
//格式化日期, 毫秒转String
function formatDateTime(inputTime) {    
    var date = new Date(inputTime);  
    var y = date.getFullYear();    
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;    
    return y + '-' + m ;    
}

//下一页
function nextPage(){
	if(pageNo >= totalPage){
		return;
	}
	pageNo++;
	doA();
	 $("#pageNo").text(pageNo);
};
//上一页
function lastPage(){
	if(pageNo <= 1){
		return;
	}
	pageNo--;
	doA();
	 $("#pageNo").text(pageNo);
}



//搜索
function doSearch(){
	pageNo = 1;
	doA();
	$("#pageNo").text(pageNo);	
}

//改变表格展示最大行数
function changePageSize() {
	doA();
}