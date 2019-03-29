var pageNo = 1;
var count = 0;
var pageSize = 15;
var totalPage = 1;
$(function() {
	var merchantId;
	var queryTime;
	var Request = new Object();
	Request = GetRequest();
	merchantId = Request['merchantId'];
	queryTime = Request['queryTime'];
	$('#input_merchantId').val(merchantId);
	$('#input_queryTime').val(queryTime);
	// 初始化数据
	$.ajax({
        url: "../../report/getReportOrderDetail",
        data: {
        	pageNo:pageNo,
        	pageSize: pageSize,
        	merchantId : merchantId,
        	queryTime : queryTime
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
})

function GetRequest() {
	   var url = location.search; //获取url中含"?"符后的字串
	   var theRequest = new Object();
	   if (url.indexOf("?") != -1) {
	      var str = url.substr(1);
	      strs = str.split("&");
	      for(var i = 0; i < strs.length; i ++) {
	         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
	      }
	   }
	   return theRequest;
	}
    // 渲染回调
function action(json) {
	var data = json;
	$("#label_totalpage")[0].innerHTML = "1";
	if(null == json || "" == json || "undefined" == json){
		return;
	}
    var trList = "";
    var list = data.orderRecordList;
    count = data.count;
    totalPage = Math.ceil(count / pageSize);
    $("#label_totalpage")[0].innerHTML = totalPage;
    var index = 0;
    for (i in list) {  	
    	var type=list[i].type=="1"?'按比例':(list[i].type=="2"?'按金额':'未设置结算');
    	var symbol=list[i].type=="1"?'%':'元';
        trList += "<tr>" +
            "<td id='td_id_" + index + "'>" + list[i].orderId + "</td>" +
            "<td>" + formatDateTime(list[i].receiveTime) + "</td>" +
            "<td>" + list[i].goodsName +"</td>" +
            "<td>" + list[i].standardName + "</td>" +
            "<td>" + type +"</td>" +
            "<td>" + list[i].deductPoint + symbol + "</td>" +
            "<td>" + Fen2Yuan(list[i].totalPrice) +"元"+ "</td>" +
            "<td>" + Fen2Yuan(list[i].realFee) +"元"+ "</td>" +
            "<td>" + Fen2Yuan(list[i].couponFee) +"元"+ "</td>" +
            "<td>" + Fen2Yuan(list[i].hasRefundMoney) +"元"+ "</td>" +
            "<td>" + Fen2Yuan(list[i].canBalanceBefore)+"元" + "</td>" +
            "<td>" + Fen2Yuan(list[i].canBalanceAfter)+"元" + "</td>" +
            "</tr>";
        index++;
    }
    $("#orderListDetail").find('tr').eq(0).before(trList);
}

function goBack(){
	history.go(-1);
}
//下一页
function nextPage(){
	if(pageNo >= totalPage){
		return;
	}
	pageNo++;
	doA();
};
//上一页
function lastPage(){
	if(pageNo <= 1){
		return;
	}
	pageNo--;
	doA();
}

function Fen2Yuan(num) {
	if (typeof num !== "number" || isNaN( num )) return null;
	return (num / 100).toFixed( 2 );
}
//格式化日期, 毫秒转String
function formatDateTime(inputTime) {    
    var date = new Date(inputTime);  
    var y = date.getFullYear();    
    var m = date.getMonth() + 1;    
    m = m < 10 ? ('0' + m) : m;    
    var d = date.getDate();    
    d = d < 10 ? ('0' + d) : d;    
    var h = date.getHours();  
    h = h < 10 ? ('0' + h) : h;  
    var minute = date.getMinutes();  
    var second = date.getSeconds();  
    minute = minute < 10 ? ('0' + minute) : minute;    
    second = second < 10 ? ('0' + second) : second;   
    return y + '-' + m + '-' + d + ' '+ h + ':' + minute + ':' + second;    
}

function doA() {
	pageSize = $("#select_pagesize option:selected").val();
	$("#orderListDetail  tr").empty("");
    $.ajax({
        url: "../../report/getReportOrderDetail",
        data: {         	
        	pageNo : pageNo,
        	pageSize : pageSize,
        	merchantId : $('#input_merchantId').val(),
        	queryTime : $('#input_queryTime').val()
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	action(result.RESULTJSON);
            } else {
                alert(result.RESULTMSG)
            }
        }
        
    })
}
//改变表格展示最大行数
function changePageSize() {
	doA();
}

function exportToExl(){
	window.location.href="../../report/export?merchantId="+$('#input_merchantId').val()+"&queryTime="+$('#input_queryTime').val();
}
