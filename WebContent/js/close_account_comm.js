/**
 * Created by pc on 2019/1/28.
 */
var $url = 'http://192.168.10.13:8080';
var pageNo = 1;
var count = 0;
var pageSize = $("#select_pagesize option:selected").val();
var totalPage = 1;
$(function() {
    doA();
    queryAllFirstRubric();
       console.log(1);
});
//获取一级类目；
function queryAllFirstRubric(){
    $.ajax({
        url: "../../merchant/getAllCategory",
        data: {
            level:0
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                loadFirstCategorySelected(result.RESULTJSON.categoryInfoList);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
//获取二级类目；
function queryAllSecondRubric(){
    var $value = $('#select_firstcategory option:selected').val();
    $.ajax({
        url: "../../merchant/getAllCategory",
        data: {
            parentId:$value
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                $('.secondcategory').show();
                loadSecondCategorySelected(result.RESULTJSON.categoryInfoList);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
function loadFirstCategorySelected(data) {
    var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
    $("#select_firstcategory").html("");
    for (i in data) {
        option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>"
    }
    $("#select_firstcategory").append(option);
}

function loadSecondCategorySelected(data) {
    var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
    $("#select_secondcategory").html("");
    for (i in data) {
        option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>"
    }
    $("#select_secondcategory").append(option);
}
//修改一级二级类目；
//获取一级类目；
function queryAllFirstRubric_edit(parentId){
    $.ajax({
        url: "../../merchant/getAllCategory",
        data: {
            level:0
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                loadFirstCategorySelected_edit(result.RESULTJSON.categoryInfoList,parentId);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
//获取二级类目；
function queryAllSecondRubric_edit(parentId,categoryId){
    var $value = $('#select_firstcategory_edit option:selected').val();
    var $parentId;
    if(parentId){
        $parentId = parentId;
    }else{
        $parentId = $value
    }
    $.ajax({
        url: "../../merchant/getAllCategory",
        data: {
            parentId:$parentId
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                $('.secondcategory').show();
                loadSecondCategorySelected_edit(result.RESULTJSON.categoryInfoList,categoryId);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
function loadFirstCategorySelected_edit(data,parentId) {
    //console.log(data)
    $("#select_firstcategory_edit").html("");
    var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
    for (i in data) {
        if(data[i].categoryId == parentId){
            option += "<option selected='selected' value='" + data[i].categoryId + "'>" + data[i].name + "</option>";
        }else{
            option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>";
        }
    }
    $("#select_firstcategory_edit").append(option);
}

function loadSecondCategorySelected_edit(data,categoryId) {
    //console.log(data)
    $("#select_secondcategory_edit").html("");
    var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
    for (i in data) {
        if(data[i].categoryId == categoryId){
            option += "<option selected='selected' value='"+ data[i].categoryId +"'>" + data[i].name + "</option>";
        }else{
            option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>";
        }
    }
    $("#select_secondcategory_edit").append(option);
}
//新增获取商户名称；
$('#inputShopId').on('input propertychange',function(){
    var inputShopId = $(this).val();
    if(inputShopId != ''){
        $.ajax({
            url: "../../merchant/getMerchant",
            data: {
                merchantId: inputShopId
            },
            type: "POST",
            dataType: "JSON",
            success: function (result) {
                //console.log(result.RESULTJSON)
                if (result.RESULTCODE == "0") {
                    $('#inputShopName').val(result.RESULTJSON.name)
                } else {
                    popx(result.RESULTMSG, 3)
                }
            }

        })
    }else{
        $('#inputShopName').val('');
    }

});
//修改获取商户名称；
$('#inputShopId_edit').on('input propertychange',function(){
    var inputShopId = $(this).val();
    if(inputShopId != ''){
        $.ajax({
            url: "../../merchant/getMerchant",
            data: {
                merchantId: inputShopId
            },
            type: "POST",
            dataType: "JSON",
            success: function (result) {
                //console.log(result.RESULTJSON)
                if (result.RESULTCODE == "0") {
                    $('#inputShopName_edit').val(result.RESULTJSON.name)
                } else {
                    popx(result.RESULTMSG, 3)
                }
            }

        })
    }else{
        $('#inputShopName_edit').val('');
    }

});
//添加信息；
function staffAdd () {
    var inputShopId = $('#inputShopId').val();
    var inputShopName = $('#inputShopName').val();
    var select_firstcategory = $('#select_firstcategory').val();
    var select_secondcategory = $('#select_secondcategory').val();
    var inputRatio = $('#inputRatio').val();
    var startTime = $('#startTime').val();
    var endTime = $('#endTime').val();
    if (inputShopId == ''|| inputShopId == undefined || inputShopId == null) {
        popx('请填写商户ID',2);
        return;
    }
    if (inputShopName == ''|| inputShopName == undefined || inputShopName == null) {
        popx('请填写商户名称',2);
        return;
    }
    if (select_firstcategory == ''|| select_firstcategory == undefined || select_firstcategory == null) {
        popx('请选择销售类目',2);
        return;
    }
    if (select_secondcategory == ''|| select_secondcategory == undefined || select_secondcategory == null) {
        popx('请选择销售二级类目',2);
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
    }
    $.ajax({
        url: "../../pointdeduct/addPointDeductInfo",
        data: {
            merchantId:inputShopId,
            type: 1,
            categoryId:select_secondcategory,
            deductPoint: inputRatio,
            beginTime: startTime,
            endTime:endTime,
            goodsId:0,
            target:1
        },
        type: "GET",
        dataType: "JSON",
        success: function (result) {
            //console.log(result)
            if (result.RESULTCODE == "0") {
                doA();
                popx('添加成功',2);
                $('#staff_add').modal('hide');
            } else {
                popx(result.RESULTMSG,3)
            }
        }
    })
};
//修改保存信息；
function staffEdit () {
    var inputShopId = $('#inputShopId_edit').val();
    var inputShopName = $('#inputShopName_edit').val();
    var select_firstcategory = $('#select_firstcategory_edit').val();
    var select_secondcategory = $('#select_secondcategory_edit').val();
    var inputRatio = $('#inputRatio_edit').val();
    var startTime = $('#startTime_edit').val();
    var endTime = $('#endTime_edit').val();
    var listId = $('#editBtn').attr('data-id');
    //console.log(listId);
    if (inputShopId == ''|| inputShopId == undefined || inputShopId == null) {
        popx('请填写商户ID',2);
        return;
    }
    if (inputShopName == ''|| inputShopName == undefined || inputShopName == null) {
        popx('请填写商户名称',2);
        return;
    }
    if (select_firstcategory == ''|| select_firstcategory == undefined || select_firstcategory == null) {
        popx('请选择销售类目',2);
        return;
    }
    if (select_secondcategory == ''|| select_secondcategory == undefined || select_secondcategory == null) {
        popx('请选择销售二级类目',2);
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
    }
    $.ajax({
        url: "../../pointdeduct/updatePointDeductInfo",
        data: {
            merchantId:inputShopId,
            type: 1,
            categoryId:select_secondcategory,
            deductPoint: inputRatio,
            beginTime: startTime,
            endTime:endTime,
            goodsId:0,
            target:1,
            id:listId
        },
        type: "GET",
        dataType: "JSON",
        success: function (result) {
            //console.log(result)
            if (result.RESULTCODE == "0") {
                doA();
                popx('修改成功',2);
                $('#modal_edit').modal('hide');
            } else {
                popx(result.RESULTMSG,2)
            }
        }
    })
};
// 渲染回调
function action(json) {
    //console.log(json)
    var data = json;
    $("#label_totalpage")[0].innerHTML = "1";
    if(null == json || "" == json || "undefined" == json){
        return;
    }
    var trList = "";
    var list = data.pointDeductInfoList;
    count = data.count;
    totalPage = Math.ceil(count / pageSize);
    $("#label_totalpage")[0].innerHTML = totalPage;
    $("#pageNo").text(pageNo);
    for (i in list) {
        trList += "<tr>" +
        "<td>"+ list[i].merchantId +"</td>" +
        "<td>"+ list[i].merchantName +"</td>" +
        "<td>"+ list[i].parentName +"</td>" +
        "<td>"+ list[i].name +"</td>" +
        "<td>"+ formatDateTime(list[i].beginTime) +"</td>" +
        "<td>"+ formatDateTime(list[i].endTime) +"</td>" +
        "<td>"+ list[i].deductPoint + "%" +"</td>" +
        "<td>" +
        "<a href='#' id='edit' data-toggle='modal' data-target='#modal_edit' data-id= " + list[i].id + ">" +
        "<i class='glyphicon glyphicon-pencil' aria-hidden='true'></i> 修改" +
        "</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
        "<a href='#' onclick='isDel("+ list[i].id +")'>" +
        "<i class='glyphicon glyphicon-trash' aria-hidden='true'></i> 删除" +
        "</a>" +
        "</td>" +
        "</tr>"
    }
    $("#goodsList").append(trList);
}
//获取商户详情;
$('#goodsList').on('click','#edit',function () {
    var $id = $(this).attr('data-id');
    $('#editBtn').attr('data-id',$id);
    $.ajax({
        url:"../../pointdeduct/getPointDeductInfo",
        data: {
            id:$id
        },
        type: "GET",
        dataType: "JSON",
        success: function (result) {
            //console.log(result);
            if (result.RESULTCODE == "0") {
                var dataList = result.RESULTJSON;
                //console.log(dataList);
                $("#inputShopId_edit").val(dataList.merchantId);
                $("#inputShopName_edit").val(dataList.merchantName);
                $('#inputRatio_edit').val(dataList.deductPoint);
                $('#startTime_edit').val(formatDateTime(dataList.beginTime));
                $('#endTime_edit').val(formatDateTime(dataList.endTime));
                $('#role_sele_edit').val(dataList.idCode);
                var parentId = dataList.parentId;
                var categoryId = dataList.categoryId;
                queryAllFirstRubric_edit(parentId);
                queryAllSecondRubric_edit(parentId,categoryId);
            } else {
                popx(result.RESULTMSG,2)
            }
        }
    })
});
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

//搜索
function doSearch(){
    $("#goodsList").empty("");
    var merchantName = $('#merchantName').val();
    pageNo = 1;
    $.ajax({
        url: "../../pointdeduct/getAllPointDeductInfo",
        data: {
            target:1,
            pageNo : pageNo,
            pageSize : pageSize,
            merchantName:merchantName
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

function doA() {
    pageSize = $("#select_pagesize option:selected").val();
    $("#goodsList").empty("");
    $.ajax({
        url: "../../pointdeduct/getAllPointDeductInfo",
        data: {
            target:1,
            pageNo : pageNo,
            pageSize : pageSize
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            //console.log(result)
            if (result.RESULTCODE == "0") {
                action(result.RESULTJSON);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
//删除
function isDel(index) {
    if(confirm("确定删除吗")){
        $.ajax({
            url: "../../pointdeduct/deletePointDeductInfo",
            data: {
                id:index
            },
            type: "POST",
            dataType: "JSON",
            success: function(result) {
                if (result.RESULTCODE == "0") {
                    popx("删除成功",2,function(){
                        window.location.reload();
                    });
                } else {
                    popx("删除失败",2);
                }
            }


        })
    }
};
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
    return y + '-' + m + '-' + d;
}