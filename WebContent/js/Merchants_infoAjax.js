var pageNo = 1;
var count = 0;
var totalPage = 1;
var pageSize = 15;
var $url = 'http://192.168.10.13:8080';
$(function() {
	doA();
})

function doA() {
	pageSize = $("#select_pagesize option:selected").val();
	$("#merchantsList  tr").empty("");
	var MercName =$('#Merchants_infoSerach').val();
	
    $.ajax({
        url: "../../merchant/getAllMerchant",
        data: {
        	name : MercName,
        	pageNo : pageNo,
        	pageSize : pageSize
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	action(result.RESULTJSON);
            } else {
                popx(result.RESULTMSG,2)
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
    var list = data.merchantInfoList;
    count = data.count;
    totalPage = Math.ceil(count / pageSize);
    $("#label_totalpage")[0].innerHTML = totalPage;
    $("#pageNo").text(pageNo);
    var index = 0;
    for (i in list) {
        trList += "<tr>" +
            "<td id='td_id_" + index + "'>" + list[i].merchantId + "</td>" +
            "<td>" + list[i].name + "</td>" +
            "<td>" + list[i].unifiedCreditCode + "</td>" +
            "<td class=''>" + list[i].address + "</td>" +
            "<td>" + list[i].phone + "</td>" +
            "<td>" + list[i].contacts + "</td>" +
            "<td>" + list[i].contactsPhone + "</td>" +
            "<td align='center'>" +
            "<div>" +
            "<a href='#' onclick='doEdit(" + list[i].merchantId + ")' title='修改'>" +
            "<i class='glyphicon glyphicon-pencil' aria-hidden='true'>" +"修改"+
            "</i>" +
            "</a>" +
            "&nbsp;&nbsp;"+
            "<a onclick='isDel(" + index + ");' title='删除' style='color:#CC0000'>" +
            "<i class='glyphicon glyphicon-remove' aria-hidden='true'>" +"删除"+
            "</i>" +
            "</a>" +
            "</div>" +
            "</td>" +
            "</tr>";
        index++;
    }
    $("#merchantsList").find('tr').eq(0).before(trList);
}

function doEdit(index){
	window.location = "modify_Merchants.html?merchantId=" + index;
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
}

//改变表格展示最大行数
function changePageSize() {
	doA();
}

//删除
function isDel(index) {
	if(confirm("确定删除吗?")){
		$.ajax({
	        url: "../../merchant/deleteMerchant",
	        data: {
	            merchantId: document.getElementById("td_id_" + index).innerHTML
	        },
	        type: "POST",
	        dataType: "JSON",
	        success: function(result) {
	            if (result.RESULTCODE == "0") {    //后端删除成功     
	                popx("删除成功",2);
	                window.location.reload();
	            }else if(result.RESULTCODE == "-92007"){
	            	 popx("该商户还有未下架商品，不能删除",2);
	            } else {
	            	popx("删除失败",2);
	            }
	        }
		})
	}
};
