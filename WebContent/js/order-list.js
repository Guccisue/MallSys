var pageNo = 1;
var count = 0;
var pageSize = 10;
var totalPage = 1;
var $url = 'http://192.168.10.13:8080';
$(function() {
    doA();
	laydate.render({
		  elem: '#beginDate', //指定元素
		  type: 'datetime'
		});
			
		laydate.render({
		  elem: '#endDate', //指定元素
		  type: 'datetime'
		});
})
function exportToExl(){
	window.location.href="../../order/export?status="+$('#orderStatus option:selected').val()+"&startTime="+$('#beginDate').val()+"&endTime="+$('#endDate').val()+"&refundStatus="+$('#refundStatus option:selected').val()+"&payType="+$('#payType option:selected').val();
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
    $("#pageNo").text(pageNo);
    var index = 0;
    var statusName;
    for (i in list) { 
    	var updateTime;
    	var operateName;
        var refundStatusName;
    	if(list[i].status == 1 && list[i].refundStatus == -1){
    		updateTime = list[i].payTime
    		operateName="<a  href='#' onclick=\"doDelivery(' "+list[i].orderId+" ')\">" +"<p>去发货</p>"+"</a>";
    		refundStatusName="";
    	} else if(list[i].status == 1 && list[i].refundStatus == 3){
    		updateTime = list[i].payTime
    		operateName="<a  href='#' onclick=\"doRefund(' "+list[i].orderId+" ')\">" +"<p style='color:red'>退款审核</p>"+"</a>";
    		refundStatusName="申请退款";
    	} else if(list[i].status == 2 && list[i].refundStatus == 3){
    		updateTime = list[i].deliverTime 
    		operateName="<a  href='#' onclick=\"doRefund(' "+list[i].orderId+" ')\">" +"<p style='color:red'>退款审核</p>"+"</a>";
    		refundStatusName="申请退款";
    	} else if(list[i].status == 6 && list[i].refundStatus != 4){
    		updateTime = list[i].createTime 
    		operateName ="<a  href='#'>" +""+"</a>";
    		refundStatusName="";
    	} else if(list[i].refundStatus == 3){
    		updateTime = list[i].payTime
    		operateName="<a  href='#' onclick=\"doRefund(' "+list[i].orderId+" ')\">" +"<p style='color:red'>退款审核</p>"+"</a>";
    		refundStatusName="申请退款";
    	} else if(list[i].refundStatus == 11){
    		updateTime = list[i].payTime 
    		operateName ="<a  href='#'>" +""+"</a>";
    		refundStatusName="已退货";
    	} else if(list[i].refundStatus == 9){
    		updateTime = list[i].payTime 
    		operateName ="<a  href='#'>" +""+"</a>";
    		refundStatusName="拒绝退款";
    	} else if(list[i].refundStatus == 10){
    		updateTime = list[i].payTime 
    		operateName ="<a  href='#'>" +""+"</a>";
    		refundStatusName="待退货";
    	}else if(list[i].refundStatus == 8){
    		updateTime = list[i].payTime 
    		operateName="<a  href='#' onclick=\"doRefund2(' "+list[i].orderId+" ')\">" +"<p style='color:red'>退款处理</p>"+"</a>";
    		refundStatusName="退款中";
    	} else if(list[i].refundStatus == 4){
    		updateTime = list[i].payTime 
    		operateName ="<a  href='#'>" +""+"</a>";
    		refundStatusName="已退款";
    	} else if(list[i].status == 5){
    		updateTime = list[i].receiveTime 
    		operateName ="<a  href='#'>" +""+"</a>";
    		refundStatusName="";
    	}else {
    		updateTime = list[i].createTime;
    		operateName="";
    		refundStatusName="";
    	} 
    	var payTypeName;
    	if(list[i].payType ==1){
    		payTypeName = "支付宝";
    	}else if(list[i].payType ==2){
    		payTypeName = "微信";
    	}else{
    		payTypeName = "";
    	}
    	
    	switch (list[i].status) {
		case 0:
			statusName="待支付 ";
			break;
		case 1:
			statusName="已支付 ";
			break;
		case 2:
			statusName="已发货 ";
			break;		
		case 5:
			statusName="已完成  ";
			break;
		case 6:
			statusName="已取消  ";
			break;
		case 7:
			statusName="已删除  ";
			break;		
		}      
    	
        trList += "<tr>" +
            "<td id='td_id_" + index + "'>" + list[i].orderId + "</td>" +
            "<td>" + list[i].receiver + "</td>" +
            "<td>" + list[i].phone + "</td>" +
            "<td>" + Fen2Yuan(list[i].realFee) + "元" +"</td>" +
            "<td class='goodsName'>" + list[i].goodsName + "</td>" +
            "<td>" + list[i].number + "</td>" +
            "<td class='remark'>" + list[i].remark + "</td>" +
            "<td>" + statusName + "</td>" +
            "<td>" + refundStatusName + "</td>" +
            "<td>" + formatDateTime(updateTime) + "</td>" +
            "<td>" + payTypeName + "</td>" +
            "<td align='center'>" +
            "<div><a href='order_detail.html?orderId=" + list[i].orderId + "''>" +
				"<i class='glyphicon glyphicon-search' aria-hidden='true'></i>" +"查看详情"+
			"</a></div>" +
            "</td>" +
            "<td align='center'>" +
            "<div>" +operateName+
            "</div>" +
            "</td>" +
            "</tr>";
        index++;
    }
    $("#orderList").find('tr').eq(0).before(trList);
}
function lastPage(){
    if(pageNo <= 1){
        return;
    }
    pageNo--;
    doA();
    $("#pageNo").text(pageNo);
}
function nextPage() {
    if(pageNo >= totalPage) {
        return;
    }
    pageNo++;
    doA();
    $("#pageNo").text(pageNo);
}

function changePrice(priceStr) {
	return parseInt(priceStr * 100);
}

//搜索
function doSearch(){
	$("#orderList  tr").empty("");
    pageNo = 1;
	$.ajax({
        url: "../../order/getAllOrder",
        data: { 
        	receiver: $('#receiver').val(),
        	phone: $('#phone').val(),
        	payType: $('#payType option:selected').val(),
        	goodsName: $('#goodsName').val(),
        	status: $('#orderStatus option:selected').val(),
        	refundStatus: $('#refundStatus option:selected').val(),
        	orderId: $('#orderNum').val(),
        	startTime: $('#beginDate').val(),
        	endTime: $('#endDate').val(),
        	pageNo : pageNo,
        	pageSize:pageSize
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	count = result.RESULTJSON.count;
                totalPage = Math.ceil(count / pageSize);
                if(count !=0 || count !=undefined){
                	 $('.M-box3').pagination({
                    pageCount: totalPage,
                    totalData:result.RESULTJSON.count,
                    mode:'unfixed',
                    isHide:'true',
                    jump: true,
                    coping: true,
                    homePage: '首页',
                    endPage: '末页',
                    prevContent: '上页',
                    nextContent: '下页',
                    callback: function (api) {
                        pageNo = api.getCurrent();
                        doB();
                    }
                });
                	
                }
               
            	action(result.RESULTJSON);
            } else {
                alert(result.RESULTMSG)
            }
        }
        
    })
	
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
	$("#orderList  tr").empty("");
    $.ajax({
        url: "../../order/getAllOrder",
        data: {  
        	receiver: $('#receiver').val(),
        	phone: $('#phone').val(),
        	payType: $('#payType option:selected').val(),
        	goodsName: $('#goodsName').val(),
        	status: $('#orderStatus option:selected').val(),
        	refundStatus: $('#refundStatus option:selected').val(),
        	orderId: $('#orderNum').val(),
        	startTime: $('#beginDate').val(),
        	endTime: $('#endDate').val(),
        	pageNo : pageNo,
        	pageSize : pageSize
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	action(result.RESULTJSON);
                count = result.RESULTJSON.count;
                totalPage = Math.ceil(count / pageSize);
                console.log(result.RESULTJSON);
                $('.M-box3').pagination({
                    pageCount: totalPage,
                    totalData:result.RESULTJSON.count,
                    mode:'unfixed',
                    isHide:'true',
                    jump: true,
                    coping: true,
                    homePage: '首页',
                    endPage: '末页',
                    prevContent: '上页',
                    nextContent: '下页',
                    callback: function (api) {
                        pageNo = api.getCurrent();
                        doB();
                    }
                });
            } else {
                alert(result.RESULTMSG)
            }
        }
        
    })
}
function doB(){
    pageSize = $("#select_pagesize option:selected").val();
    $("#orderList  tr").empty("");
    $.ajax({
        url: "../../order/getAllOrder",
        data: {
            receiver: $('#receiver').val(),
            phone: $('#phone').val(),
            payType: $('#payType option:selected').val(),
            goodsName: $('#goodsName').val(),
            status: $('#orderStatus option:selected').val(),
            refundStatus: $('#refundStatus option:selected').val(),
            orderId: $('#orderNum').val(),
            startTime: $('#beginDate').val(),
            endTime: $('#endDate').val(),
            pageNo : pageNo,
            pageSize:pageSize
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

function doDelivery(index){
	$("#div_confirm")[0].style.display = "";
	$("#orderId").val(index);
}

function doRefund(index){
	$.ajax({
        url: "../../order/getOrder",
        data: {    
        	orderId:index.trim()
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	$('#needRefundMoney').val(Fen2Yuan(result.RESULTJSON.realFee));
            	$('#totalPrice').val(Fen2Yuan(result.RESULTJSON.totalPrice));
            	$('#hasRefund').val(Fen2Yuan(result.RESULTJSON.fee));
            	$('#refundId').val(result.RESULTJSON.refundId);
            	$('#transactionId').val(result.RESULTJSON.transactionId);
            } else {
                alert(result.RESULTMSG)
            }
        }
        
    })
	$("#div_refund")[0].style.display = "";
	$("#orderId ").val(index);
}

function doRefund2(index){
	$.ajax({
        url: "../../order/getOrder",
        data: {    
        	orderId:index.trim()
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	$('#needRefundMoney2').val(Fen2Yuan(result.RESULTJSON.totalPrice-result.RESULTJSON.fee));
            	$('#transactionId').val(result.RESULTJSON.transactionId);
            } else {
                alert(result.RESULTMSG)
            }
        }
    })
	$("#div_refund2")[0].style.display = "";
	$("#orderId ").val(index);
}

function submitRefund(){
	var rejectCause = $('#rejectCause').val().trim();
	//直接退款
	if($("#returnType option:selected").val() == 2){
		$.ajax({
	        url: "../../order/doRefund",
	        data: {    
	        	orderId:$("#orderId").val().trim(),
	        	auditStatus:$("#returnType option:selected").val(),
	        	transactionId:$("#transactionId").val().trim(),
	        	realFee:changePrice($("#needRefundMoney").val())
	        },
	        type: "POST",
	        dataType: "JSON",
	        success: function(result) {
	            if (result.RESULTCODE == "0") {
	    		    alert("处理成功");
	    		    $("#div_refund")[0].style.display = "none";
                    doB();
	            } else {
	                alert("处理失败")
	            }
	        }
	    })
	}else{
		if($("#returnType option:selected").val() == 3 && rejectCause== "" ){
			alert("请填写退款原因");
		}else{
			$.ajax({
		        url: "../../order/updateReturnStatus",
		        data: {    
		        	orderId:$("#orderId").val().trim(),
		        	auditStatus:$("#returnType option:selected").val(),
		        	reason:rejectCause
		        },
		        type: "POST",
		        dataType: "JSON",
		        success: function(result) {
		            if (result.RESULTCODE == "0") {
		            	alert("处理成功");
		            	$("#div_refund")[0].style.display = "none";
                        doB();
		            } else {
		                alert(result.RESULTMSG)
		            }
		        }
		        
		    })
		}
		
	}
	
}

function submitRefund2(){
	var rejectCause = $('#rejectCause').val().trim();
	//直接退款，该情况是在退款退货后执行的退款操作
	$.ajax({
        url: "../../order/doRefund",
        data: {    
        	orderId:$("#orderId").val().trim(),
        	auditStatus:1,
        	transactionId:$("#transactionId").val().trim(),
        	realFee:changePrice($("#needRefundMoney2").val())
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
	        	alert("处理成功");
	        	$("#div_refund2")[0].style.display = "none";
                doB();
            } else {
                alert(result.RESULTMSG)
            }
        }
    })
}
function closeSpan(){
	$("#div_confirm")[0].style.display = "none";
}

function closeSpan2(){
	$("#div_refund")[0].style.display = "none";
}

function closeSpan3(){
	$("#div_refund2")[0].style.display = "none";
}

function submitDeliveryInfo(){
	var orderId = $("#orderId").val().trim();
	var deliveryType = $("#deliveryType").val();
	var deliveryNum = $("#deliveryNum").val().replace(/(^\s*)|(\s*$)/g, '');
	var remarks = $("#remarks").val().replace(/(^\s*)|(\s*$)/g, '');
	if(deliveryType == '' || deliveryType == undefined || deliveryType == null){
		alert("请选择物流公司");
	}else if((deliveryNum == '' || deliveryNum == undefined || deliveryNum == null ) && deliveryType != "到店自提"){
		alert("请填写物流单号");
	}else{
		$.ajax({
	        url: "../../order/updateDeliver",
	        data: {    
	        	orderId:orderId,
	        	courier:deliveryType,
	        	courierNumber:deliveryNum,
	        	remark:remarks
	        },
	        type: "POST",
	        dataType: "JSON",
	        success: function(result) {
	            if (result.RESULTCODE == "0") {
	            	alert("发货成功");
	            	$("#div_confirm")[0].style.display = "none";
                    doB();
	            } else {
	                alert(result.RESULTMSG)
	            }
	        }
	        
	    })
	}
}

//改变表格展示最大行数
function changePageSize() {
	doA();
}





function selectType(){
	var deliveryType = $("#deliveryType").val();
	if(deliveryType == "到店自提"){
		$('#tr_number').hide();
	}else{
		$('#tr_number').show();
	}
}
