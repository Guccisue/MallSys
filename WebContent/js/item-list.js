var $url = 'http://192.168.10.108:8080';
var pageNo = 1;
var count = 0;
var pageSize = $("#select_pagesize option:selected").val();
var totalPage = 1;
var purposeId = '';
var $shopId = '';
var $shopName = '';
$(function() {
	doA();
});
    // 渲染回调
function action(json) {
    //IDENTITY   1：商户  0：总部
    //console.log(json)
	var data = json.RESULTJSON;
	var role = json.IDENTITY;
	$("#label_totalpage")[0].innerHTML = "1";
	if(null == json || "" == json || "undefined" == json){
		return;
	}
    var trList = "";
    var list = data.goodsInfoList;
    count = data.count;
    $("#label_totalcount")[0].innerHTML = data.count;
    totalPage = Math.ceil(count / pageSize);
    $("#label_totalpage")[0].innerHTML = totalPage;
    $("#pageNo").text(pageNo);
    var index = 0;
    var statusName;
    var operatorName;
    for (i in list) {
        if(role == 0){
            switch (list[i].status) {
                case 0:
                    statusName = "<td style='color: #666;'>待上架</td>";
                    operatorName = "";
                    break;
                case 1:
                    statusName = "<td style='color: #00B83F;'>已上架</td>";
                    operatorName = "<a onclick='soldOut(" + list[i].goodsId + ");'>" +
                    "<i class='glyphicon glyphicon-download' aria-hidden='true'></i> 下架" +
                    "</a>&nbsp;&nbsp;&nbsp;&nbsp;" +
                    "<a class='modal_edit' id='" + list[i].goodsId + "' data-name='" + list[i].name + "' data-toggle='modal' data-target='#modal_edit'>" +
                    "<i class='glyphicon glyphicon-wrench' aria-hidden='true'></i> 结算设置" +
                    "</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    break;
                case 2:
                    statusName = "<td style='color: #999;'>已下架</td>";
                    operatorName = "";
                    break;
                case 4:
                        statusName = "<td style='color: #f1a417;'>审核中</td>";
                        operatorName = "<a class='audit' id='" + list[i].goodsId + "' data-toggle='modal' data-target='#client_register' >" +
                        "<i class='glyphicon glyphicon-list-alt' aria-hidden='true'></i> 审核" +
                        "</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    break;
                case 5:
                    statusName = "<td style='color: #f02d2f;'>已驳回</td>";
                    operatorName = "";
                    break;
            }
            trList += "<tr>" +
            "<td id='td_id_" + index + "'>" + list[i].goodsId + "</td>" +
            "<td>" + list[i].merchantName + "</td>" +
            "<td title='" + list[i].name + "'>" + list[i].name + "</td>" +
            "<td>" + list[i].categoryName + "</td>" + statusName +
             "<td>" + formatDateTime(list[i].createTime) + "</td>" +  
            "<td>" + formatDateTime(list[i].modifyTime) + "</td>" +  
            "<td style='text-align: left;padding-left: 30px;'>" + operatorName +
            "<a class='record' id='" + list[i].goodsId + "' data-toggle='modal' data-target='#client_record'>" +
            "<i class='glyphicon glyphicon-list-alt' aria-hidden='true'></i> 审核记录" +
            "</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
            "<a class='particulars' onclick='particulars(" + list[i].goodsId + ")' >" +
            "<i class='glyphicon glyphicon-tasks' aria-hidden='true'></i> 详情" +
            "</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
            "</td>" +
            "</tr>";
            index++;
            $('#add_item').hide();
        }else{
            switch (list[i].status) {
                case 0:
                    statusName = "<td style='color: #666;'>待上架</td>";
                    operatorName = "<a onclick='submitAudit(" + list[i].goodsId + ");'>" +
                    "<i class='glyphicon glyphicon-upload' aria-hidden='true'></i> 提交审核" +
                    "</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    break;
                case 1:
                    statusName = "<td style='color: #00B83F;'>已上架</td>";
                    operatorName = "<a onclick='soldOut(" + list[i].goodsId + ");'>" +
                    "<i class='glyphicon glyphicon-download' aria-hidden='true'></i> 下架" +
                    "</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    break;
                case 2:
                    statusName = "<td style='color: #999;'>已下架</td>";
                    operatorName = "<a onclick='reshelf(" + list[i].goodsId + ");'>" +
                    "<i class='glyphicon glyphicon-refresh' aria-hidden='true'></i> 重新上架" +
                    "</a>&nbsp;&nbsp;&nbsp;&nbsp;";
                    break;
                case 4:
                        statusName = "<td style='color: #f1a417;'>审核中</td>";
                        operatorName = "";
                    break;
                case 5:
                    statusName = "<td style='color: #f02d2f;'>已驳回</td>";
                    operatorName = "";
                    break;
            }
            trList += "<tr>" +
            "<td id='td_id_" + index + "'>" + list[i].goodsId + "</td>" +
            "<td>" + list[i].merchantName + "</td>" +
            "<td title='" + list[i].name + "'>" + list[i].name + "</td>" +
            "<td>" + list[i].categoryName + "</td>" + statusName +
             "<td>" + formatDateTime(list[i].createTime) + "</td>" +  
            "<td>" + formatDateTime(list[i].modifyTime) + "</td>" +
            "<td style='text-align: left;padding-left: 30px;'>" +
            "<a href='#' onclick='doEdit(" + list[i].goodsId + ")' >" +
            "<i class='glyphicon glyphicon-pencil' aria-hidden='true'></i> 修改" +
            "</a>&nbsp;&nbsp;&nbsp;&nbsp;" + operatorName +
            "<a class='record' id='" + list[i].goodsId + "' data-toggle='modal' data-target='#client_record'>" +
            "<i class='glyphicon glyphicon-list-alt' aria-hidden='true'></i> 审核记录" +
            "</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
            "<a class='particulars' onclick='particulars(" + list[i].goodsId + ")' >" +
            "<i class='glyphicon glyphicon-tasks' aria-hidden='true'></i> 详情" +
            "</a>&nbsp;&nbsp;&nbsp;&nbsp;"+
            "<a onclick='isDel(" + list[i].goodsId + ");' style='color:#CC0000'>" +
            "<i class='glyphicon glyphicon-trash' aria-hidden='true'></i> 删除" +
            "</a>" +
            "</td>" +
            "</tr>";
            index++;
            $('#add_item').show();
        }
    }
    $("#goodsList").append(trList);
}

function doEdit(index){
	window.location = "edit_items.html?goodsId=" + index ;
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

//改变表格展示最大行数
function changePageSize() {
    doA();
}
$('#goodsList').on('click','.audit',function(){
    purposeId = $(this).attr('id');
    //console.log(purposeId);
});
$('#goodsList').on('click','.modal_edit',function(){
    $shopId = $(this).attr('id');
    $shopName = $(this).attr('data-name');
    //console.log($shopId,$shopName);
});
//查看详情；
function particulars(index){
    window.location = "particulars.html?goodsId=" + index ;
}
//设置结算信息；
function staffEdit () {
    //var inputShopId = $('#inputShopId_edit').val();
    //var inputShopName = $('#inputShopName_edit').val();
    var inputRatio = $('#inputRatio_edit').val();
    var type_edit = $('input[name="type_edit"]:checked').val();
    var startTime = $('#startTime_edit').val();
    var listId = $('#editBtn').attr('data-id');
    //console.log(listId)
    var endTime = $('#endTime_edit').val();
    if (type_edit == ''|| type_edit == undefined || type_edit == null) {
        popx('请选择结算模式',2);
        return;
    }
    if (inputRatio == ''|| inputRatio == undefined || inputRatio == null) {
        popx('请填结算比例',2);
        return;
    }
    if (startTime == ''|| startTime == undefined || startTime == null) {
        popx('请填开始时间',2);
        return;
    }
    if (endTime == ''|| endTime == undefined || endTime == null) {
        popx('请填结束时间',2);
        return;
    }    //console.log($shopId,$shopName);
    $.ajax({
        url: "../../pointdeduct/addPointDeductInfo",
        data: {
            merchantId:0,
            goodsId:$shopId,
            name:$shopName,
            type: type_edit,
            deductPoint: inputRatio,
            beginTime: startTime,
            endTime:endTime,
            target:2
        },
        type: "GET",
        dataType: "JSON",
        success: function (result) {
            //console.log(result)
            if (result.RESULTCODE == "0") {
                doA();
                popx('设置成功',2);
                $('#modal_edit').modal('hide');
            } else {
                popx(result.RESULTMSG,2)
            }
        }
    })
};
//审核；
function addFollowRecord(){
    var remark = $('#remark').val().trim();
    var authenticity = $("#authenticity option:selected").val();
    if(authenticity == 5){
        if(remark ==''|| remark == undefined || remark == null){
            popx('请填驳回理由',2);
            return;
        }
    }
    $('#addFollowRecord>button').attr('disabled',true);
    $.ajax({
        url: "../../goods/updateGoodsStatus",
        data: {
            goodsId: purposeId,
            status:authenticity,
            rejectReason:remark
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            //console.log(result)
            $('#addFollowRecord>button').attr('disabled',false);
            if (result.RESULTCODE == "0") {
                doA();
                popx('保存成功',2);
                $('#client_register').modal('hide');
                $('#remark').val('');
                $("#authenticity option").eq(0).attr('selected','selected');
            } else {
                popx('保存失败',2);
                $('#client_register').modal('hide');
            }
        }
    })
};
//审核记录；
$('#goodsList').on('click','.record',function(){
    $('#modal_ul').html('');
    var purposeId = $(this).attr('id');
    $.ajax({
        url: "../../goods/getAllAuditingInfoByGoodsId",
        data: {
            goodsId: purposeId
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            //console.log(result)
            if (result.RESULTCODE == "0") {
                var list = result.RESULTJSON;
                //console.log(list);
                if(list.length > 0){
                    for(var i = 0; i < list.length; i++){
                        var $status = '';
                        if(list[i].status == 1){
                            $status = '<span style="color: #00B83F">通过</span>'
                        }else{
                            $status = '<span style="color: #f02d2f">驳回</span>'
                        }
                        var $li = ' <li class="modal_li">' +
                            '<div><span>'+ list[i].name +'</span>：（ '+ $status + ' ）' + formatDateTime(list[i].createTime) + '</div>' +
                            '<div>'+ list[i].rejectReason +'</div>'+
                            '</li>';
                        $('#modal_ul').append($li);
                    }
                }else{
                    var $li = ' <li class="modal_li"><div style="text-align: center;">暂无记录~~</div></li>';
                    $('#modal_ul').append($li);
                }

            } else {
                popx(result.RESULTMSG,2);
                $('#client_register').modal('hide');
            }
        }

    })
});
//搜索
function doSearch(){
	$("#goodsList  tr").empty("");
    pageNo = 1;
    var goodsId=$('#goodsId').val().trim() == ''? 0 :$('#goodsId').val().trim();
	$.ajax({
        url: "../../goods/getAllGoods",
        data: {
        	name: $('#goodsName').val().trim(),
        	status: $('#goodsStatus option:selected').val(),
        	merchantName: $('#merchantName').val().trim(),
        	goodsId: goodsId,
        	categoryId: $('#categoryId option:selected').val(),
        	beginTime: $('#beginDate').val(),
        	endTime: $('#endDate').val(),
        	pageNo : pageNo,
        	pageSize : pageSize
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                action(result);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })

}

function doA() {
	pageSize = $("#select_pagesize option:selected").val();
	$("#goodsList  tr").empty("");
	var goodsName =$('#goodsName').val();
	 var goodsId=$('#goodsId').val().trim() == ''? 0 :$('#goodsId').val().trim();
    $.ajax({
        url: "../../goods/getAllGoods",
        data: {
        	name: $('#goodsName').val().trim(),
        	status: $('#goodsStatus option:selected').val(),
        	goodsId: goodsId,
        	categoryId: $('#categoryId option:selected').val(),
        	merchantName: $('#merchantName').val(),
        	beginTime: $('#beginDate').val(),
        	endTime: $('#endDate').val(),
        	pageNo : pageNo,
        	pageSize : pageSize
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            //console.log(result);
            if (result.RESULTCODE == "0") {
                action(result);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
//删除
function isDel(index) {
	if(confirm("确定删除吗?")){
		 $.ajax({
 	        url: "../../goods/updateGoodsStatus",
 	        data: {
 	        	goodsId: index,
 	        	status:3
 	        },
 	        type: "POST",
 	        dataType: "JSON",
 	        success: function(result) {
 	            if (result.RESULTCODE == "0") {
                    popx("删除成功",2,function(){
                        doA();
                    });
 	            } else {
                    popx("删除失败",2);
 	            }
 	        }


 	    })
	}

};
//提交审核；
function submitAudit(index) {
    if(confirm("确定提交审核吗？")){
        $.ajax({
            url: "../../goods/updateGoodsStatus",
            data: {
                goodsId: index,
                status:4
            },
            type: "POST",
            dataType: "JSON",
            success: function(result) {
                if (result.RESULTCODE == "0") {
                    popx("提交审核成功",2,function(){doA()});
                } else {
                    popx("提交审核失败",2);
                }
            }


        })
    }

};
//重新上架；
function reshelf(index) {
    if(confirm("确定重新上架吗？")){
        $.ajax({
            url: "../../goods/updateGoodsStatus",
            data: {
                goodsId: index,
                status:1
            },
            type: "POST",
            dataType: "JSON",
            success: function(result) {
                if (result.RESULTCODE == "0") {
                    popx("上架成功",2,function(){doA()});
                } else {
                    popx("上架失败",2);
                }
            }


        })
    }
};
//下架；
function soldOut(index) {
    if(confirm("确定下架吗？")){
        $.ajax({
            url: "../../goods/updateGoodsStatus",
            data: {
                goodsId: index,
                status:2
            },
            type: "POST",
            dataType: "JSON",
            success: function(result) {
                if (result.RESULTCODE == "0") {
                    popx("下架成功",2,function(){doA()});
                } else {
                    popx(result.RESULTMSG,2);
                }
            }


        })
    }
};
// 格式化日期, 毫秒转String
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
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
}