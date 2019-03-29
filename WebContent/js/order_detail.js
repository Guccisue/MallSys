$(function() {
	var orderId;
	var Request = new Object();
	Request = GetRequest();
	orderId = Request['orderId'];
	// 初始化数据
	$.ajax({
        url: "../../order/getOrder",
        data: {
        	orderId : orderId
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	loadData(result.RESULTJSON);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }
    })
});


function Fen2Yuan(num) {
	if (typeof num !== "number" || isNaN( num )) return null;
	return (num / 100).toFixed( 2 );
}
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
function loadData(json) {
	var data = json;
	if(null == data || "" == data || "undefined" == data) {
		return;
	}
	var payTypeName;
	var statusName;
	var refundStatusName;
	if(data.payType ==1){
		payTypeName = "支付宝";
	}else if(data.payType ==2){
		payTypeName = "微信";
	}else{
		payTypeName = "";
	}
	
	if(data.status == 0){
		statusName="待支付 ";
		$('.pay').hide();
		$('.courier').hide();
		$('.refund').hide();
	}else if(data.status == 1 && data.refundStatus != 0){
		statusName="已支付 ";
		$('.courier').hide();
	}else if(data.status == 1 && data.refundStatus == 0){
		statusName="已支付 ";
		$('.courier').hide();
		$('.refund').hide();
	}else if(data.status == 2){
		statusName="已发货 ";
		$('.courier').hide();
	}else if(data.status == 5){
		statusName="已完成  ";
	}else if(data.status == 6 && data.refundStatus != 0 && data.refundStatus != 4){
		statusName="已取消  ";
		$('.pay').hide();
		$('.courier').hide();
	}else if(data.status == 6 && data.refundStatus != 0 && data.refundStatus == 4){
		statusName="已取消  ";
		$('.courier').hide();
	}else if(data.status == 6 && data.refundStatus == 0){
		statusName="已取消  ";
		$('.pay').hide();
		$('.courier').hide();
		$('.refund').hide();
	}else if(data.status == 7){
		statusName="已删除  ";
		$('.pay').hide();
		$('.courier').hide();
		$('.refund').hide();
	}		
	
    if(data.refundStatus == 3){
		refundStatusName="申请退款";
	} else if(data.refundStatus == 11){
		refundStatusName="已退货";
	} else if(data.refundStatus == 9){
		refundStatusName="拒绝退款";
	} else if(data.refundStatus == 10){
		refundStatusName="待退货";
	}else if(data.refundStatus == 8){
		refundStatusName="退款中";
	} else if(data.refundStatus == 4){
		refundStatusName="已退款";
	} else {
		refundStatusName="";
	} 
	
	$("#td_orderId")[0].innerHTML = data.orderId;
	$("#td_receiver")[0].innerHTML = data.receiver;
	$("#td_phone")[0].innerHTML = data.phone;
	$("#td_address")[0].innerHTML = data.receiveAddress;
	$("#td_goodsname")[0].innerHTML = data.goodsName;
	$("#td_standard")[0].innerHTML = data.standardName;
	$("#td_price")[0].innerHTML = Fen2Yuan(data.price)+"元";
	$("#td_number")[0].innerHTML = data.number;
	$("#td_totalprice")[0].innerHTML = Fen2Yuan(data.totalPrice)+"元";
	$("#td_realfee")[0].innerHTML = Fen2Yuan(data.realFee)+"元";
	$("#td_status")[0].innerHTML = statusName;
	$("#td_paytype")[0].innerHTML = payTypeName;
	$("#td_paytime")[0].innerHTML = formatDateTime(data.payTime);
	$("#td_courier")[0].innerHTML = data.courier;
	$("#td_couriernumber")[0].innerHTML = data.courierNumber;
	$("#td_refundstatus")[0].innerHTML = refundStatusName;
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

function goBack() {
	history.go(-1);
}