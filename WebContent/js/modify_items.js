var $url = 'http://192.168.10.108:8080';
var flag ;
var $index = 1;
var index = 0;
var ainum = 1;
var isUpdateGoodsInfo = 1;   //商品本身(name,status)；0未修改；1修改；
var isUpdateGoodsPic = 0;   //图片 0未修改；1修改；
var isUpdateGoodsAtt = 0;    //属性 0未修改；1修改；
var isUpdateGoodsStandard = 0; // 规格 0未修改；1修改；
var seckill = -1;  //判断是否秒杀时间；
    $(function() {
    getseckill();
	var goodsId;
	Request = GetRequest();
	goodsId = Request['goodsId'];
    getGoodsById(goodsId);
});
$("#shId").on('change',function(){
    isUpdateGoodsInfo = 1;
});
$("#sellingPoint").on('change',function(){
    isUpdateGoodsInfo = 1;
});
$("#goodsStatus").on('change',function(){
    isUpdateGoodsInfo = 1;
});
$("#select_firstcategory").on('change',function(){
    isUpdateGoodsInfo = 1;
});
$("#select_secondcategory").on('change',function(){
    isUpdateGoodsInfo = 1;
});
$("#select_area").on('change',function(){
    isUpdateGoodsInfo = 1;
});
$('#add_area').on('change','.area_div input',function(){
    isUpdateGoodsInfo = 1;
});
$("#select_model").on('change',function(){
    $("#table_standard>tbody>tr").not(":first").not('.tr_standard_class_not').remove();
    isUpdateGoodsInfo = 1;
    isUpdateGoodsAtt = 1;
    isUpdateGoodsStandard = 1;
});
$('#table_Property').on('change','.sellingPoint',function(){
    isUpdateGoodsAtt = 1;
    isUpdateGoodsStandard = 1;
});
$('#description').on('change',function(){
    isUpdateGoodsInfo = 1;
});
$('#endDate').on('change',function(){
    isUpdateGoodsInfo = 1;
});
$('#startDate').on('change',function(){
    isUpdateGoodsInfo = 1;
});
$('#table_standard').on('change','.items_sizetable_td>input',function(){
    isUpdateGoodsStandard = 1;
});
function getGoodsById(goodsId){
    $.ajax({
        url: "../../goods/getGoods",
        data: { goodsId: goodsId},
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                action(result.RESULTJSON);
            } else {
                popx(result.RESULTMSG,2);
            }
        },
        error:function(result){
            popx("未进入正确方法",2);
        }
    })
}
//获取url传值
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
//初始化
function action(json) {
    console.log(json)
	var data = json;
    $("#shId").val(data.name);
    $("#sellingPoint").val(data.sellingPoint);
    queryAllFirstRubric(data.parentId);
    queryAllSecondRubric(data.parentId,data.categoryId);
    queryAllmodel(data.categoryId,data.templetId);
    //$("#goodsStatus").val(data.status);
    $("#description").val(data.description);
    var deliveryArea = data.deliveryArea.split(',');
    $.ajax({
        url: "../../area.json",
        type: "POST",
        dataType: "JSON",
        success: function(data) {
            //console.log(data)
            var $area = '';
            for(var i = 0; i < data.length; i++){
                $area += '<div class="left area_div">' +
                '<input type="checkbox" id="'+ data[i].code +'"/>' +
                '<label for="'+ data[i].code +'">'+ data[i].name +'</label><div class="add_city">';
                var $city = '';
                for(var j = 0; j < data[i].citylist.length; j++){
                    $city +=
                        '<div class="area_div city_div">' +
                        '<input type="checkbox" id="'+ data[i].citylist[j].code +'"/>' +
                        '<label for="'+ data[i].citylist[j].code +'">'+ data[i].citylist[j].name +'</label>' +
                        '</div>';
                }
                $area += $city + '</div></div>';
            }
            $('#add_area').append($area);
            //console.log(deliveryArea)
            if(deliveryArea != ''){
                $('#area').show();
                $('#select_area').find("option[value = 1]").attr("selected","selected");
                var $add_areaInput = $('#add_area').find('.area_div input');
                for(var b = 0; b < $add_areaInput.length; b++){
                    var allId = $add_areaInput.eq(b).attr('id');
                    for(var c = 0; c < deliveryArea.length; c++){
                        var thisId = deliveryArea[c];
                        if(allId == thisId){
                            $add_areaInput.eq(b).prop('checked',true);
                        }
                    }
                }
                var $deliveryAreaInput = $('#add_area>.area_div');
                for(var d = 0; d < $deliveryAreaInput.length; d++){
                    if ($deliveryAreaInput.eq(d).find('.add_city>.city_div>input').is(':checked')) {
                        $deliveryAreaInput.eq(d).find('>input').prop('checked', true);
                    }
                }
            }
        }
    });
    if(data.seckillCategoryId == data.parentId){
        $('#seckill').show();
        $('#startDate').val(formatDateTime(data.seckillTime));
        $('#endDate').val(formatDateTime(data.endTime));
    }else{
        $('#seckill').hide();
    }
    var pic01 = [];
    var pic02 = [];
    for(var a = 0; a < data.picList.length; a++){
        if(data.picList[a].type == 1){
            var pic01list = {
                'url':data.picList[a].pic,
                'size':data.picList[a].picSize
            };
            pic01.push(pic01list)
        }
        if(data.picList[a].type == 2){
            var pic02list = {
                'url':data.picList[a].pic,
                'size':data.picList[a].picSize
            };
            pic02.push(pic02list)
        }
    }
    for(var x = 0; x < pic01.length; x++){
        $('#preview'+x).attr("src",pic01[x].url);
        $('#preview'+x).parents().siblings('.addImg').hide();
        $('#preview'+x).parents().siblings('.delete').show();
        $('#pic'+x).val(pic01[x].url);
        $('#pic'+x+'Size').val(pic01[x].size);
    }
    for(var y = 0; y < pic02.length; y++){
        var $addImg = '<div class="item" style="border: none;">' +
            '<img class="addImg" onclick="clickImg(this);" src="../../img/standard_default3.png" style="height: 140px;display: none;"/>' +
            '<input name="addurl'+ $index +'" id="addurl'+ $index +'" type="file" class="upload_input" onchange="addchange_xq(this)" style="border:none;visibility: hidden;"/>' +
            '<div class="preBlock" style="width: 100%;"> ' +
            '<img class="preview" id="addpreview" alt="" src="'+pic02[y].url +'" name="pic" style="display: block;height: 140px;">' +
            '</div>' +
            '<img class="delete" onclick="deleteImg_xq(this)" id="'+ $index +'" src="../../img/delete.png" style="display: block;">' +
            '</div>';
        $('#addImg_xq_01').before($addImg);
        var $addInput = '<input type="hidden" id="pic_xq'+ $index +'" data-type="2"value="'+ pic02[y].url +'"/><input type="hidden" id="pic_xqSize'+ $index +'" value="'+ pic02[y].size +'"/>';
        $('#pic_xq_input').append($addInput);
        $index++;
    }
    var trList01 ='';
    for(var i = 0; i< data.attributeList.length; i++ ){
        if(i == 0){
            trList01 += '<tbody class="tbodyList">' +
            '<tr><th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%;border-top:none;">属性名称</th>' +
            '<td style="background: white;border-top:none;">' +
            '<div class="items_addForm">' +
            '<input class="sellingPoint" type="text" value="'+ data.attributeList[i].name +'" style="height:30px;width: 200px;padding:0 10px;margin:0 20px 0 30px;"/>' +
            '<input class="btn btn-outline btn-default" type="button" value="新增属性" id="addProperty" style="background:#1ab394; color:white;border:none;">' +
            '</div>' +
            '</td>' +
            '</tr>'+
            '<tr id="table_color_tr">' +
            '<th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;">属性值</th>' +
            '<td style="width: 90%;border-top:none;padding-left: 30px;">' +
            '<input class="property_val" type="text" placeholder="请输入属性值，如：红色" value="" style="height:30px;width: 200px;padding:0 10px;margin-right: 20px;"/>' +
            '<input class="btn btn-outline btn-default" type="button" value="新增" style="background:#1ab394; color:white;border:none;" onclick="addProperty_value(this)">';

        }else{
            trList01 += '<tbody class="tbodyList">' +
            '<tr><th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%;border-top:none;">属性名称</th>' +
            '<td style="background: white;border-top:none;">' +
            '<div class="items_addForm">' +
            '<input class="sellingPoint" type="text" value="'+ data.attributeList[i].name +'" style="height:30px;width: 200px;padding:0 10px;margin:0 20px 0 30px;"/>' +
            '<input class="btn btn-outline btn-default" type="button" value="删除属性" id="editProperty" style="background:red; color:white;border:none;">' +
            '</div>' +
            '</td>' +
            '</tr>'+
            '<tr id="table_color_tr">' +
            '<th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;">属性值</th>' +
            '<td style="width: 90%;border-top:none;padding-left: 30px;">' +
            '<input class="property_val" type="text" placeholder="请输入属性值，如：红色" value="" style="height:30px;width: 200px;padding:0 10px;margin-right: 20px;"/>' +
            '<input class="btn btn-outline btn-default" type="button" value="新增" style="background:#1ab394; color:white;border:none;" onclick="addProperty_value(this)">';
        }
        var trList02 = '';
        for(var j = 0; j < data.attributeList[i].valueList.length; j++){
            trList02 += '<div class="add_div">' +
            '<input type="checkbox" checked="checked" id="addInput'+ data.attributeList[i].valueList[j].id +'"/>' +
            '<label for="addInput'+ data.attributeList[i].valueList[j].id +'">'+ data.attributeList[i].valueList[j].attributeValue +'</label>' +
            '<a onclick="eisDelProperty_value(this);" style="color:#CC0000"> ' +
            '<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>' +
            '</a>' +
            '</div>';
        }
        trList01 += trList02 + '</td></tr></tbody>';
    }
    $('#table_Property').append(trList01);
    console.log(data.standardList);
    for(var z = 0; z < data.standardList.length; z++){
        var $price = Fen2Yuan(data.standardList[z].marketPrice);
        var $marketPrice = Fen2Yuan(data.standardList[z].price);
        if(data.standardList[z].pic){
            var addtr = '<tr class="tr_standard_class">' +
                '<td style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;"></td>' +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input disabled='disabled' value='"+ data.standardList[z].attributeValues +"' style='width: 50%; height: 30px;text-align: center;' type='text' class='standard' data-id='" + index + "'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input class='disDot' type='number' style='width: 50%; height: 30px;text-align: center;' type='text' id='stock" + index + "' value='"+ data.standardList[z].stock +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='price" + index + "' value='"+ $price +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='marketPrice" + index + "' value='"+ $marketPrice +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 10%;text-align: center;'><input class='disDot' type='number' style='width: 50%; height: 30px;text-align: center;' type='text' id='goldNumber" + index + "' value='"+ data.standardList[z].goldNumber +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'>" +
                "<div class='item itm_addimgs' style='height:80px;width:100%;left:0;'>" +
                "<img class='icon addImg addImg_xq' onclick='clickImg(this);' src='../../img/standard_default.png' style='display: none'/>" +
                "<input name='standardpic" + index + "' id='standardpic" + index + "' type='file'class='upload_input' onchange='addchange(this)' />" +
                "<input type='hidden' id='spic" + index + "' data-type='3' value='"+ data.standardList[z].pic +"'/>" +
                "<div class='preBlock addImg_xq'>" +
                "<img class='preview' alt='' name='pic' style='height: 60px;width: 60px;' data-type='3' src='"+ data.standardList[z].pic +"'/>" +
                "</div>" +
                "<img class='delete deleteSmall' onclick='deleteImg(this)' src='../../img/delete.png' style='display: block'/>" +
                "</div>" +
                "</td>" +
                "<td class='items_sizetable_td' style='width: 5%;text-align: center;'>" +
                "<input class='btn btn-outline btn-default' type='button'value='删除' id='items_sizeBtn3' onclick=' deleteRow($(this));' style='background:red; color:white;border:none;' />" +
                "</td>" +
                '</tr>';
        }else{
            var addtr = '<tr class="tr_standard_class">' +
                '<td style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;"></td>' +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input disabled='disabled' value='"+ data.standardList[z].attributeValues +"' style='width: 50%; height: 30px;text-align: center;' type='text' class='standard' data-id='" + index + "'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input class='disDot' type='number' style='width: 50%; height: 30px;text-align: center;' class='disDot' id='stock" + index + "' value='"+ data.standardList[z].stock +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='price" + index + "' value='"+ $price +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='marketPrice" + index + "' value='"+ $marketPrice +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 10%;text-align: center;'><input class='disDot' type='number' style='width: 50%; height: 30px;text-align: center;' class='disDot' id='goldNumber" + index + "' value='"+ data.standardList[z].goldNumber +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'>" +
                "<div class='item itm_addimgs' style='height:80px;width:100%;left:0;'>" +
                "<img class='icon addImg addImg_xq' onclick='clickImg(this);' src='../../img/standard_default.png'/>" +
                "<input name='standardpic" + index + "' id='standardpic" + index + "' type='file'class='upload_input' onchange='addchange(this)' />" +
                "<input type='hidden' id='spic" + index + "' data-type='3' value='"+ data.standardList[z].pic +"'/>" +
                "<div class='preBlock addImg_xq'>" +
                "<img class='preview' alt='' name='pic' style='height: 60px;width: 60px;' data-type='3' src=''/>" +
                "</div>" +
                "<img class='delete deleteSmall' onclick='deleteImg(this)' src='../../img/delete.png'/>" +
                "</div>" +
                "</td>" +
                "<td class='items_sizetable_td' style='width: 5%;text-align: center;'>" +
                "<input class='btn btn-outline btn-default' type='button'value='删除' id='items_sizeBtn3' onclick=' deleteRow($(this));' style='background:red; color:white;border:none;' />" +
                "</td>" +
                '</tr>';
        }
        index++;
        $('#table_standard').append(addtr);
    }
}

$('#select_secondcategory').on('change',function(){
    $("#select_model").html("");
    $.ajax({
        url: "../../templetInfo/getTempletInfoByCategoryId",
        data: {
            categoryId:$(this).val()
        },
        type: "POST",
        dataType: "JSON",
        success: function (result) {
            //console.log(result.RESULTJSON);
            var data = result.RESULTJSON;
            if (result.RESULTCODE == "0") {
                var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
                for (i in data) {
                    option += "<option value='" + data[i].id + "'>" + data[i].name + "</option>"
                }
                $("#select_model").append(option);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    });
});
//去除小数点；
$('#table_standard').on('keyup','.disDot',function(){
    if(! /^\d+$/.test(this.value)){
        this.value='';
    }
});
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
function getseckill(){
    $.ajax({
        url: "../../merchant/getSeckillCategoryId",
        data: {},
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            //console.log(result)
            if (result.RESULTCODE == "0") {
                //console.log(result.RESULTJSON)
                seckill = result.RESULTJSON;
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    });
}

$("#select_model").on('change',function() {
    $('#table_Property').html('');
    index = 0;
    $.ajax({
        url: "../../templetInfo/getTempletInfo",
        data: {
            id:$(this).val()
        },
        type: "POST",
        dataType: "JSON",
        success: function (result) {
            //console.log(result.RESULTJSON.attributeInfoList);
            var dataList = result.RESULTJSON.attributeInfoList;
            if (result.RESULTCODE == "0") {
                var trList01 ='';
                for(var i = 0; i< result.RESULTJSON.attributeInfoList.length; i++ ){
                    if(i == 0){
                        trList01 += '<tbody class="tbodyList">' +
                        '<tr><th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%;border-top:none;">属性名称</th>' +
                        '<td style="background: white;border-top:none;">' +
                        '<div class="items_addForm">' +
                        '<input class="sellingPoint" type="text" value="'+ result.RESULTJSON.attributeInfoList[i].name +'" style="height:30px;width: 200px;padding:0 10px;margin:0 20px 0 30px;"/>' +
                        '<input class="btn btn-outline btn-default" type="button" value="新增属性" id="addProperty" style="background:#1ab394; color:white;border:none;">' +
                        '</div>' +
                        '</td>' +
                        '</tr>'+
                        '<tr id="table_color_tr">' +
                        '<th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;">属性值</th>' +
                        '<td style="width: 90%;border-top:none;padding-left: 30px;">' +
                        '<input class="property_val" type="text" placeholder="请输入属性值，如：红色" value="" style="height:30px;width: 200px;padding:0 10px;margin-right: 20px;"/>' +
                        '<input class="btn btn-outline btn-default" type="button" value="新增" style="background:#1ab394; color:white;border:none;" onclick="addProperty_value(this)">';

                    }else{
                        trList01 += '<tbody class="tbodyList">' +
                        '<tr><th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%;border-top:none;">属性名称</th>' +
                        '<td style="background: white;border-top:none;">' +
                        '<div class="items_addForm">' +
                        '<input class="sellingPoint" type="text" value="'+ result.RESULTJSON.attributeInfoList[i].name +'" style="height:30px;width: 200px;padding:0 10px;margin:0 20px 0 30px;"/>' +
                        '<input class="btn btn-outline btn-default" type="button" value="删除属性" id="editProperty" style="background:red; color:white;border:none;">' +
                        '</div>' +
                        '</td>' +
                        '</tr>'+
                        '<tr id="table_color_tr">' +
                        '<th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;">属性值</th>' +
                        '<td style="width: 90%;border-top:none;padding-left: 30px;">' +
                        '<input class="property_val" type="text" placeholder="请输入属性值，如：红色" value="" style="height:30px;width: 200px;padding:0 10px;margin-right: 20px;"/>' +
                        '<input class="btn btn-outline btn-default" type="button" value="新增" style="background:#1ab394; color:white;border:none;" onclick="addProperty_value(this)">';
                    }
                    var trList02 = '';
                    for(var j = 0; j < result.RESULTJSON.attributeInfoList[i].valueList.length; j++){
                        trList02 += '<div class="add_div">' +
                        '<input type="checkbox" checked="checked" id="addInput'+ result.RESULTJSON.attributeInfoList[i].valueList[j].id +'"/>' +
                        '<label for="addInput'+ result.RESULTJSON.attributeInfoList[i].valueList[j].id +'">'+ result.RESULTJSON.attributeInfoList[i].valueList[j].attributeValue +'</label>' +
                        '<a onclick="eisDelProperty_value(this);" style="color:#CC0000"> ' +
                        '<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>' +
                        '</a>' +
                        '</div>';
                    }
                    trList01 += trList02 + '</td></tr></tbody>';
                }
                //console.log(trList01);
                $('#table_Property').append(trList01);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    });
});
$('#table_Property').on('click','#addProperty',function(){
    var $property = $('.sellingPoint');
    for(var i = 0; i< $property.length; i++) {
        var $propertyValue = $property.eq(i).val();
        if (!$propertyValue) {
            popx('请填属性名称', 2);
            $property.eq(i).focus();
            return;
        }
    }
    isUpdateGoodsAtt = 1;
    isUpdateGoodsStandard = 1;
    //$('#table_Property').html('');
    var trList = '<tbody class="tbodyList"><tr>' +
        '<th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%;border-top:none;">属性名称</th>' +
        '<td style="background: white;border-top:none;">' +
        '<div class="items_addForm">' +
        '<input class="sellingPoint" type="text" placeholder="请输入属性名称，如：颜色" style="height:30px;padding:0 10px;width:200px;margin:0 20px 0 30px;"/>' +
        '<input class="btn btn-outline btn-default" type="button" value="删除属性" id="editProperty" style="background:red; color:white;border:none;">' +
        '</div>' +
        '</td>' +
        '</tr>' +
        '<tr>' +
        '<th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;">属性值</th>' +
        '<td style="width: 90%;border-top:none;padding-left: 30px;">' +
        '<input class="property_val" type="text" style="height:30px;width: 200px;padding:0 10px;margin-right: 20px;" placeholder="请输入属性值，如：红色"/>' +
        '<input class="btn btn-outline btn-default" type="button" value="新增" style="background:#1ab394; color:white;border:none;" onclick="addProperty_value(this)">' +
        '</td>' +
        '</tr></tbody>';
    $('#table_Property').append(trList);
});
$('#table_Property').on('click','#editProperty',function(){
    isUpdateGoodsAtt = 1;
    isUpdateGoodsStandard = 1;
    $(this).parents().parents().parents().parents('tbody').remove();
});
function addProperty_value(object){
    //var $property_val = $(object).siblings('.property_val').val().replace(/(^\s*)|(\s*$)/g, "");
    var $property_val = $(object).siblings('.property_val').val().replace(/\s*/g,"");
    if (!$property_val) {
        popx('请填属性值', 2);
        $(object).siblings('.property_val').focus();
        return;
    }
    var reg = /\//g;
    if(reg.test($property_val)){
        popx('禁止输入"/"', 2);
        return;
    }
    isUpdateGoodsAtt = 1;
    isUpdateGoodsStandard = 1;
    $index++;
    var trList = '<div class="add_div">' +
        '<input type="checkbox" checked="checked" id="addInput'+ $index +'"/>' +
        '<label for="addInput'+ $index +'">'+ $property_val +'</label>' +
        '<a onclick="eisDelProperty_value(this);" style="color:#CC0000"> ' +
        '<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>' +
        '</a>' +
        '</div>';
    $(object).after(trList);
    $(object).siblings('.property_val').val('');
};
function eisDelProperty_value(object){
    isUpdateGoodsAtt = 1;
    isUpdateGoodsStandard = 1;
    $(object).parents('.add_div').remove();
};
$('#table_Property').on('mousemove','.add_div',function(){
    //$(this).find('a').stop().fadeIn(100);
    $(this).find('a').css('visibility','visible');
});
$('#table_Property').on('mouseout','.add_div',function(){
    //$(this).find('a').stop().fadeOut(100);
    $(this).find('a').css('visibility','hidden');
});
function editProperty_value(object){
    $(object).parents().parents('tr').remove();
};
$("#select_area").on('change',function() {
    //console.log($(this).val());
    if($(this).val() == 1){
        $('#area').show();
    }else{
        $('#area').hide().find('input').prop('checked',false);
    }
});
$("#allcity").on('change',function() {
    if ($(this).is(':checked')) {
        $('#area').find('input').prop('checked', true);
        $('#allcity_label').text('取消全选');
    } else {
        $('#area').find('input').prop('checked', false);
        $('#allcity_label').text('全选');
    }
});
$("#add_area").on('change','.area_div>input',function() {
    if ($(this).is(':checked')) {
        $(this).parent().find('.city_div>input').prop('checked', true);
    } else {
        $(this).parent().find('.city_div>input').prop('checked', false);
    }
});

$('#add_area').on('mouseover','.area_div',function(){
    $(this).find('.add_city').stop().fadeIn(100);
}).mouseout(function(){
    $(this).find('.add_city').stop().fadeOut(100);
});

$('#add_area').on('change','.add_city>.city_div>input',function(){
    if ($(this).is(':checked')) {
        $(this).parent().parent().siblings('input').prop('checked', true);
    }
});

$("#detail_as_btn").click(function() {
    for(var a = 0; a<ainum ; a++){
        if($("#goodsdiscription"+a).val() == ""){
            popx("请先上传详情图片",2);
            return;
        }else if( flag == true){
            popx("图片上传中，请稍等",2);
            return;
        }
    }

    var aidom = "<div class='detail_addImg'>" +
        "<img class='icon addImg' onclick='clickImg(this);' src='../../img/detail_default.png' />"+
        "<input name='discription"+ainum+"' id='discription"+ainum+"' type='file' class='upload_input' onchange='change(this)' />"+
        "<div class='preBlock'> <img class='preview' alt='' name='pic' width='520' height='220' />  </div>"+
        "<div class='delete-image' title='删除'>&times;</div></div>";

    if (ainum <= 20) {
        $("#div_detailpics").append(aidom);
        flag = false;
        ainum += 1;
    } else {
        popx("图片添加已达到限定值！",2);
    }
});
//del
$(".xyItems").on("click", ".delete-image", function() {
    $('#goodsdiscription'+(ainum-1)).val("");
    $('#goodsdiscriptionSize'+(ainum-1)).val("");
    $(this).parent().remove();
    ainum -= 1;
});
//rank-up
$(".xyItems").on("click", ".xyrank .up", function() {
    var self = $(this).parent().parent();
    self.insertBefore(self.prev());
    self.animate({ opacity: 0.1 }, 200).animate({ opacity: 1 }, 200).animate({ opacity: 0.1 }, 200).animate({ opacity: 1 }, 200);
});
//rank-down
$(".xyItems").on("click", ".xyrank .dw", function() {
    var self = $(this).parent().parent();
    self.insertAfter(self.next());
    self.animate({ opacity: 0.1 }, 200).animate({ opacity: 1 }, 200).animate({ opacity: 0.1 }, 200).animate({ opacity: 1 }, 200);
});

//点击
var clickImg = function(obj) {
    if(flag == true){
        popx("图片上传中,请稍等",2);
        return;
    }
    $(obj).parent().find('.upload_input').click();
}
//删除
var deleteImg = function(obj) {
    flag = false;
    isUpdateGoodsPic = 1;
    var deleteIndex = $(obj).attr('data-id');
    if(deleteIndex){
        $("#pic"+deleteIndex).val('');
        $("#pic"+deleteIndex+"Size").val('');
    }
    $(obj).parent().find('input').val('');
    $(obj).parent().find('img.preview').attr("src", "");
    //IE9以下
    $(obj).parent().find('img.preview').css("filter", "");
    $(obj).hide();
    $(obj).parent().find('.addImg').show();
};
var deleteImg_xq = function(obj) {
    flag = false;
    isUpdateGoodsPic = 1;
    var deleteIndex = $(obj).attr('id');
    //console.log(deleteIndex);
    //var x = deleteIndex*2;
    //var y = deleteIndex*2 + 1;
    if($(obj).parent().parent().find('.item').length > 1){
        $(obj).parent().remove();
        $("#pic_xq"+deleteIndex).remove();
        $("#pic_xqSize"+deleteIndex).remove();
    }else{
        $("#pic_xq0").remove();
        $("#pic_xqSize0").remove();
        $(obj).parent().find('input').val('');
        $(obj).parent().find('img.preview').attr("src", "");
        //IE9以下
        $(obj).parent().find('img.preview').css("filter", "");
        $(obj).hide();
        $(obj).parent().find('.addImg').show();
    }
}
//选择图片
function change(file) {
    //预览
    var pic = $(file).parent().find(".preview");
    //添加按钮
    var addImg = $(file).parent().find(".addImg");
    //删除按钮
    var deleteImg = $(file).parent().find(".delete");

    var ext = file.value.substring(file.value.lastIndexOf(".") + 1).toLowerCase();

    // gif在IE浏览器暂时无法显示
    if (ext != 'png' && ext != 'jpg' && ext != 'jpeg') {
        if (ext != '') {
            popx("图片的格式必须为png或者jpg或者jpeg格式！",2);
        }
        return;
    }
    //判断IE版本
    var isIE = navigator.userAgent.match(/MSIE/) != null,
        isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null;
    isIE10 = navigator.userAgent.match(/MSIE 10.0/) != null;
    if (isIE && !isIE10) {
        file.select();
        var reallocalpath = document.selection.createRange().text;
        // IE6浏览器设置img的src为本地路径可以直接显示图片
        if (isIE6) {
            pic.attr("src", reallocalpath);
        } else {
            // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
            pic.css("filter", "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + reallocalpath + "\")");
            // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
            pic.attr('src', 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==');
        }
        addImg.hide();
        deleteImg.show();
    } else {
        html5Reader(file, pic, addImg, deleteImg,1);
    }
}
function addchange(file) {
    //预览
    var pic = $(file).parent().find(".preview");
    //添加按钮
    var addImg = $(file).parent().find(".addImg");
    //删除按钮
    var deleteImg = $(file).parent().find(".delete");

    var ext = file.value.substring(file.value.lastIndexOf(".") + 1).toLowerCase();

    // gif在IE浏览器暂时无法显示
    if (ext != 'png' && ext != 'jpg' && ext != 'jpeg') {
        if (ext != '') {
            popx("图片的格式必须为png或者jpg或者jpeg格式！",2);
        }
        return;
    }
    //判断IE版本
    var isIE = navigator.userAgent.match(/MSIE/) != null,
        isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null,
        isIE10 = navigator.userAgent.match(/MSIE 10.0/) != null;
    if (isIE && !isIE10) {
        file.select();
        var reallocalpath = document.selection.createRange().text;
        // IE6浏览器设置img的src为本地路径可以直接显示图片
        if (isIE6) {
            pic.attr("src", reallocalpath);
        } else {
            // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
            pic.css("filter", "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + reallocalpath + "\")");
            // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
            pic.attr('src', 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==');
        }
        addImg.hide();
        deleteImg.show();
        var $addImg = '<div class="item" style="border: none;">' +
            '<img class="addImg" onclick="clickImg(this);" src="../../img/standard_default.png" style="height: 140px;"/>' +
            '<input name="addurl0" id="addurl0" type="file" class="upload_input" onchange="addchange(this)" style="border:none;visibility: hidden;"/>' +
            '<div class="preBlock" style="width: 100%;"> <img class="preview" id="addpreview" alt="" name="pic" style="display: block;height: 140px;">' +
            '</div>' +
            '<img class="delete" onclick="deleteImg(this)" src="../../img/delete.png">' +
            '</div>'
        $('#addImg_xq').append($addImg);
    } else {
        html5Reader(file, pic, addImg, deleteImg,3);
    }
}
function addchange_xq(file) {
    //预览
    var pic = $(file).parent().find(".preview");
    //添加按钮
    var addImg = $(file).parent().find(".addImg");
    //删除按钮
    var deleteImg = $(file).parent().find(".delete");

    var ext = file.value.substring(file.value.lastIndexOf(".") + 1).toLowerCase();

    // gif在IE浏览器暂时无法显示
    if (ext != 'png' && ext != 'jpg' && ext != 'jpeg') {
        if (ext != '') {
            popx("图片的格式必须为png或者jpg或者jpeg格式！",2);
        }
        return;
    }
    //判断IE版本
    var isIE = navigator.userAgent.match(/MSIE/) != null,
        isIE6 = navigator.userAgent.match(/MSIE 6.0/) != null,
        isIE10 = navigator.userAgent.match(/MSIE 10.0/) != null;
    if (isIE && !isIE10) {
        file.select();
        var reallocalpath = document.selection.createRange().text;
        // IE6浏览器设置img的src为本地路径可以直接显示图片
        if (isIE6) {
            pic.attr("src", reallocalpath);
        } else {
            // 非IE6版本的IE由于安全问题直接设置img的src无法显示本地图片，但是可以通过滤镜来实现
            pic.css("filter", "progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src=\"" + reallocalpath + "\")");
            // 设置img的src为base64编码的透明图片 取消显示浏览器默认图片
            pic.attr('src', 'data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==');
        }
        addImg.hide();
        deleteImg.show();
        var $addImg = '<div class="item" style="border: none;">' +
            '<img class="addImg" onclick="clickImg(this);" src="../../img/standard_default3.png" style="height: 140px;"/>' +
            '<input name="addurl'+ $index +'" id="addurl'+ $index +'" type="file" class="upload_input" onchange="addchange_xq(this)" style="border:none;visibility: hidden;"/>' +
            '<div class="preBlock" style="width: 100%;"> <img class="preview" id="addpreview" alt="" name="pic" style="display: block;height: 140px;">' +
            '</div>' +
            '<img class="delete" onclick="deleteImg_xq(this)" id="'+ $index +'" src="../../img/delete.png">' +
            '</div>';
        $('#addImg_xq').append($addImg);
        var $addInput = '<input type="hidden" id="pic_xq'+ $index +'" data-type="2"/><input type="hidden" id="pic_xqSize'+ $index +'"/>';
        $('#pic_xq_input').append($addInput);
        $index++;
    } else {
        html5Reader_xq(file, pic, addImg, deleteImg,2);
    }
}
function html5Reader_xq(file, pic, addImg, deleteImg,type) {
    var file = file.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e) {
        pic.attr("src", this.result);
    };
    flag = true;
    uploadFile(pic.context.name,type);
    addImg.hide();
    deleteImg.show();
    var $addImg = '<div class="item" style="border: none;">' +
        '<img class="addImg" onclick="clickImg(this);" src="../../img/standard_default3.png" style="height: 140px;"/>' +
        '<input name="addurl'+ $index +'" id="addurl'+ $index +'" type="file" class="upload_input" onchange="addchange_xq(this)" style="border:none;visibility: hidden;"/>' +
        '<div class="preBlock" style="width: 100%;"> <img class="preview" id="addpreview" alt="" name="pic" style="display: block;height: 140px;">' +
        '</div>' +
        '<img class="delete" onclick="deleteImg_xq(this)" id="'+ $index +'" src="../../img/delete.png">' +
        '</div>'
    $('#addImg_xq').append($addImg);
    var $addInput = '<input type="hidden" id="pic_xq'+ $index +'" data-type="2"/><input type="hidden" id="pic_xqSize'+ $index +'"/>';
    $('#pic_xq_input').append($addInput);
    $index++;
    //console.log($index)
}
//H5渲染
function html5Reader(file, pic, addImg, deleteImg,type) {
    var file = file.files[0];
    var reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = function(e) {
        pic.attr("src", this.result);
    }
    flag = true;
    uploadFile(pic.context.name,type);
    addImg.hide();
    deleteImg.show();
}
//  商品规格点击添加
$("#items_sizeBtn").click(function() {
    var select_model = $("#select_model").val();
    if(select_model == '' || select_model == undefined ||select_model == null){
        popx('请先选择属性模板',2);
        return;
    }
    //$("#table_standard>tbody>tr").not(":first").not('.tr_standard_class_not').remove();
    var $property = $('.sellingPoint').length;
    var arr01 = [];
    for(var i = 0; i < $property; i++){
        var $value = $('.sellingPoint').eq(i).parents().parents().parents().parents('tbody').find('.add_div>input:checked');
        var arr = [];
        for(var j = 0; j< $value.length; j++){
            var $text = $value.eq(j).siblings('label').text();
            arr.push($text);
        }
        arr01.push(arr);
    }
    function combine(arr) {
        var r = [];
        (function f(t, a, n) {
            if (n  ==0) return r.push(t);
            for (var i = 0; i < a[n-1].length; i++) {
                f(t.concat(a[n-1][i]), a, n - 1);
            }
        })([], arr, arr.length);
        return r;
    }
    var res = combine(arr01);
    //console.log(res);
    if($property >0){
        if($value.length > 0){
            for(var n = 0; n < res.length; n++) {
                var $standard = '';
                var arr1=res[n];
                // 数组反转。
                var len1=res[n].length;
                res[n] = $.map(arr1,function(v,i){// map方法匿名函数传的值v是值、i是索引。
                    return arr1[len1-1-i];
                });
                //console.log(res[n]);
                for(var m = 0; m < res[n].length; m++) {
                    $standard += ' / '+res[n][m];
                }
                $standard = $standard.substring(2);
                //console.log($standard.substring(1))
                var addtr = '<tr class="tr_standard_class">' +
                    '<td style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;"></td>' +
                    "<td class='items_sizetable_td' style='width: 20%;text-align: center;'><input disabled='disabled' value='"+ $standard +"' style='width: 50%; height: 30px;text-align: center;' type='text' class='standard' data-id='" + index + "'/></td>" +
                    "<td class='items_sizetable_td' style='width: 20%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='stock" + index + "'/></td>" +
                    "<td class='items_sizetable_td' style='width: 20%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='price" + index + "' /></td>" +
                    "<td class='items_sizetable_td' style='width: 20%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='marketPrice" + index + "' /></td>" +
                    "<td class='items_sizetable_td' style='width: 20%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='goldNumber" + index + "' /></td>" +
                    "<td class='items_sizetable_td' style='width: 15%;text-align: center;'>" +
                    "<div class='item itm_addimgs' style='height:80px;width:100%;left:0;'>" +
                    "<img class='icon addImg addImg_xq' onclick='clickImg(this);' src='../../img/standard_default.png'/>" +
                    "<input name='standardpic" + index + "' id='standardpic" + index + "' type='file'class='upload_input' onchange='addchange(this)' />" +
                    "<input type='hidden' id='spic" + index + "' data-type='3'/>" +
                    "<div class='preBlock addImg_xq'>" +
                    "<img class='preview' alt='' name='pic' style='height: 60px;width: 60px;' data-type='3'/>" +
                    "</div>" +
                    "<img class='delete deleteSmall' onclick='deleteImg(this)' src='../../img/delete.png' />" +
                    "</div>" +
                    "</td>" +
                    "<td class='items_sizetable_td' style='width: 20%;text-align: center;'>" +
                    "<input class='btn btn-outline btn-default' type='button'value='删除' id='items_sizeBtn3' onclick=' deleteRow($(this));' style='background:red; color:white;border:none;' />" +
                    "</td>" +
                    '</tr>';
                index++;
                $('#table_standard').append(addtr);
            }
        }else{
            popx('请勾选属性值',2)
        }
    }else{
        popx('请先添加属性名称',2)
    }
});


//删除规格
function deleteRow(obj) {
    index--;
    obj.parents(".tr_standard_class").remove();
    isUpdateGoodsStandard = 1;
}

function goBack(){
    history.go(-1);
}
//一级类目
function queryAllFirstRubric(id){
    $.ajax({
        url:  "../../pointdeduct/getAllCategoryIdByMerchantId",
        data: {},
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                //console.log(result)
                loadFirstCategorySelected(result.RESULTJSON,id);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
//二级类目
function queryAllSecondRubric(parentId,categoryId){
    var $value = $('#select_firstcategory option:selected').val();
    var $parentId;
    if(parentId){
        $parentId = parentId;
    }else{
        $parentId = $value
    }
    $.ajax({
        url:  "../../pointdeduct/getAllCategoryIdByMerchantId",
        data: {},
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                var res = result.RESULTJSON;
                console.log(res)
                for(i in res){
                    if($parentId == res[i].parentId){
                        var $data = res[i].categoryInfoList;
                        //console.log($data);
                        loadSecondCategorySelected($data,categoryId);
                    }
                }
                if($value){
                    if(seckill == $value){
                        $('#seckill').show();
                    }else{
                        $('#seckill').hide();
                    }
                }
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
//模板查询
function queryAllmodel(categoryId,templetId) {
    $.ajax({
        url: "../../templetInfo/getTempletInfoByCategoryId",
        data: {
            categoryId:categoryId
        },
        type: "POST",
        dataType: "JSON",
        success: function (result) {
            //console.log(result);
            if (result.RESULTCODE == "0") {
                loadModelSelected(result.RESULTJSON,templetId);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
function uploadFile(url,picType){
    //console.log(picType)
    var newUrl = document.getElementById(url).files[0];
    var formData = new FormData();
    formData.append('pic0', newUrl);
    formData.append('picType', picType);
    $.ajax({
        url: "../../goods/uploadFile",
        data: formData,
        type: "POST",
        contentType: false,
        mimeType:"multipart/form-data",
        processData:false,
        success: function(result) {
            //console.log(result);
            isUpdateGoodsPic = 1;
            var dataObj=eval("("+result+")");
            if (dataObj.RESULTCODE == 0) {
                var str = url.slice(0,3);
                var str2 = url.slice(0,11);
                var str3 = url.slice(0,6);
                var a= url.slice(3,4);
                var b= url.slice(11,url.length);
                var c= url.slice(6,url.length);
                if(str == "url"){
                    console.log(1111)
                    $("#pic"+a).val(dataObj.REQRESULT);
                    $("#pic"+a+"Size").val(dataObj.IMGSIZE);
                    flag = false;
                }
                if(str2 == "standardpic"){
                    console.log(2222)
                    $("#spic"+b).val(dataObj.REQRESULT);
                    flag = false;
                }
                if(str2 == "discription"){
                    console.log(3333)
                    $("#goodsdiscription"+b).val(dataObj.REQRESULT);
                    $("#goodsdiscriptionSize"+b).val(dataObj.IMGSIZE);
                    flag = false;
                }
                if(str3 == "addurl"){
                    console.log(4444)
                    $("#pic_xq"+c).val(dataObj.REQRESULT);
                    $("#pic_xqSize"+c).val(dataObj.IMGSIZE);
                    flag = false;
                }

            } else {
                popx("添加失败",2)
            }
        }

    })
}
function loadFirstCategorySelected(data,parentId) {
    $("#select_firstcategory").html("");
    var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
    for (i in data) {
        if(data[i].parentId == parentId){
            option += "<option selected='selected' value='" + data[i].parentId + "'>" + data[i].name + "</option>";
        }else{
            option += "<option value='" + data[i].parentId + "'>" + data[i].name + "</option>";
        }
    }
    $("#select_firstcategory").append(option);
}

function loadSecondCategorySelected(data,categoryId) {
    $("#select_secondcategory").html("");
    var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
    for (i in data) {
        if(data[i].categoryId == categoryId){
            option += "<option selected='selected' value='"+ data[i].categoryId +"'>" + data[i].name + "</option>";
        }else{
            option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>";
        }
    }
    $("#select_secondcategory").append(option);
}
function loadModelSelected(data,id){
    $("#select_model").html("");
    var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
    for (i in data) {
        if(data[i].id == id){
            option += "<option selected='selected' value='"+ data[i].id +"'>" + data[i].name + "</option>";
        }else{
            option += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
        }
    }
    $("#select_model").append(option);
}
function changePrice(priceStr) {
    //return parseInt(priceStr * 100);
    return Math.round(priceStr * 100);
}

function addItem(){
    if(flag == true){
        popx("图片上传中,请稍等",2);
        return;
    }
    var goodsName = $("#shId").val().replace(/(^\s*)|(\s*$)/g, '');
    var firstCategory = $("#select_firstcategory").val();
    var secondCategory = $("#select_secondcategory").val();
    //var goodsStatus = $("#goodsStatus").val();
    var description = $("#description").val();
    var sellingPoint = $("#sellingPoint").val();
    var isRestrict = $("#select_area").val();
    var templetId = $("#select_model").val();
    var startDate = $("#startDate").val();
    var endDate = $("#endDate").val();
    if(goodsName == '' || goodsName == undefined || goodsName == null){
        popx("请输入商品名称",2);
        return;
    }
    if(!/^[\s\S]{15,30}$/.test(goodsName)){
        popx("商品名称需大于15个字符小于30个字符",2);
        return;
    }
    if(sellingPoint == '' || sellingPoint == undefined || sellingPoint == null){
        popx("请输入商品卖点",2);
        return;
    }
    if(!/^[\s\S]{15,150}$/.test(sellingPoint)){
        popx("商品卖点需大于15个字符",2);
        return;
    }
    //if(goodsStatus == -1 || goodsStatus == null){
    //    popx("请选择商品状态",2);
    //    return;
    //}
    if(firstCategory == -1 || firstCategory == null){
        popx("请选择所属一级类目",2);
        return;
    }
    if(secondCategory == -1 || secondCategory == null){
        popx("请选择所属二级类目",2);
        return;
    }
    if(templetId == undefined || templetId == null || templetId == -1){
        popx("请选择所属模板",2);
        return;
    }
    var deliveryArea ='';
    if(isRestrict == 1){
        var $city_div = $('.city_div').find('input:checked');
        for(var a = 0; a < $city_div.length; a++){
            deliveryArea +=  ',' + $city_div.eq(a).attr('id');
        }
        deliveryArea = deliveryArea.substring(1);
        if(deliveryArea == '' || deliveryArea == undefined || deliveryArea == null){
            popx("请勾选限售区域",2);
            return;
        }
    }
    var modelList = [];
    var $property = $('.sellingPoint');
    for(var i = 0; i< $property.length; i++) {
        var $propertyValue = $property.eq(i).val();
        if (!$propertyValue) {
            popx('请填属性名称', 2);
            $property.eq(i).focus();
            return;
        }else{
            var $property_val = $property.eq(i).parents('.tbodyList').find('.add_div');
            for(var j = 0; j< $property_val.length; j++) {
                var list = {
                    'name':$property.eq(i).val(),
                    'attributeValue':',' + $property_val.eq(j).find('label').text()
                };
                list.attributeValue = list.attributeValue.substring(1);
                modelList.push(list);
            }
        }
    }
    //console.log(modelList)
    if(firstCategory == seckill && startDate == ''){
        popx("请选择秒杀开始时间",2);
        return;
    }
    if(firstCategory == seckill && endDate == ''){
        popx("请选择秒杀结束时间",2);
        return;
    }

    //商品规格
    var tstandardjson=[];
    for(var a = 0; a < $('.standard').length; a++){
        var attributeValues = $('.standard').eq(a).val().replace(/\s*/g,"");
        var dataId = $('.standard').eq(a).attr('data-id');
        //console.log(dataId)
        var goldNumber=$("#goldNumber"+dataId).val();
        if(goldNumber==undefined || goldNumber==null){
        	goldNumber=0
        }
        var tstandard =
        {
            "attributeValues":attributeValues,
            "stock":$("#stock"+dataId).val(),
            "marketPrice":$("#price"+dataId).val(),
            "price":$("#marketPrice"+dataId).val(),
            "goldNumber":goldNumber,
            "pic":$("#spic"+dataId).val()
        };
        tstandardjson.push(tstandard);
    }
    if(tstandardjson.length == 0){
        popx("请至少添加一种商品规格",2);
        return;
    }
    var reg = /^\+?[0-9]\d*$/;
    var reg2 = /^([1-9]\d*(\.\d*[1-9])?)|(0\.\d*[1-9])$/;
    for(var j = 0;j<tstandardjson.length;j++){
        //console.log(tstandardjson[j])
        if(tstandardjson[j].stock == ""){
            popx("请填写库存",2);
            return;
        }
        if(!reg.test(tstandardjson[j].stock)){
            popx("库存只能为大于等于0的整数",2);
            return;
        }
        if(tstandardjson[j].marketPrice == ""){
            popx("请填写市场价格",2);
            return;
        }
        if(!reg2.test(tstandardjson[j].marketPrice)){
            popx("市场价格填写非法",2);
            return;
        }
        if(tstandardjson[j].price == ""){
            popx("请填写价格",2);
            return;
        }
        if(!reg2.test(tstandardjson[j].price)){
            popx("价格填写非法",2);
            return;
        }
        //console.log('金币数',tstandardjson[j].goldNumber);
        //console.log('商城价格',tstandardjson[j].price);
        //console.log('市场价格',tstandardjson[j].marketPrice);
        //if(tstandardjson[j].goldNumber > tstandardjson[j].price){
        //    popx("金币数不能高于商城价格",2);
        //    return;
        //}
        if($("#spic"+dataId).val() == ''){
            popx("请上传规格图片",2);
            return;
        }
        tstandardjson[j].price = changePrice(tstandardjson[j].price);
        tstandardjson[j].marketPrice = changePrice(tstandardjson[j].marketPrice);
    }
    var picJson = new Array();
    for(var y = 0;y < 5; y++){
        var arrJson1 = {};
        var Pic1 = $("#pic"+y).val();
        var PicSize1 = $("#pic"+ y +"Size").val();
        var PicType1 = $("#pic"+y).attr('data-type');
        if(!Pic1){
            Pic1 = '';
        }
        if(!PicSize1){
            PicSize1 = ''
        }
        if(!PicType1){
            PicType1 = ''
        }
        arrJson1 = {
            'pic':Pic1,
            'picSize':PicSize1,
            'type':PicType1,
            'sequence':y+1
        };
        if(Pic1 != '' && PicSize1 != '' && PicType1 != ''){
            picJson.push(arrJson1);
        }
    }
    //console.log(picJson);
    for(var x = 0;x < 20; x++){
        var arrJson = {};
        var Pic = $("#pic_xq"+x).val();
        var PicSize = $("#pic_xqSize"+x).val();
        var PicType = $("#pic_xq"+x).attr('data-type');
        if(!Pic){
            Pic = '';
        }
        if(!PicSize){
            PicSize = ''
        }
        if(!PicType){
            PicType = ''
        }
        arrJson = {
            'pic':Pic,
            'picSize':PicSize,
            'type':PicType,
            'sequence':x+1
        };
        if(Pic != '' && PicSize != '' && PicType != ''){
            picJson.push(arrJson);
        }
    }
    //console.log(picJson);
    var goodsId;
    Request = GetRequest();
    goodsId = Request['goodsId'];
    $.ajax({
        url: "../../goods/updateGoods",
        data: {
            goodsId:goodsId,
            name:goodsName,
            categoryId:secondCategory,
            templetId:templetId,
            //status:goodsStatus,
            sellingPoint:sellingPoint,
            description:description,
            isRestrict:isRestrict,
            deliveryArea:deliveryArea,
            seckillTime:startDate, //开始时间
            endTime: endDate,  // 结束时间
            picJson:JSON.stringify(picJson),   //图片 type:1:展示图片 2:详情图片;sequence:排序
            attributeJson:JSON.stringify(modelList),  //	属性
            standardJson:JSON.stringify(tstandardjson),   //规格
            isUpdateGoodsInfo:isUpdateGoodsInfo,   //商品本身(name,status)；0未修改；1修改；
            isUpdateGoodsPic:isUpdateGoodsPic,    //图片 0未修改；1修改；
            isUpdateGoodsAtt:isUpdateGoodsAtt,    //属性 0未修改；1修改；
            isUpdateGoodsStandard:isUpdateGoodsStandard // 规格 0未修改；1修改；
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                popx("修改成功", 2,function(){
                    window.location.href = "Item_list.html";
                });
            }else if(result.RESULTCODE == "-92016"){
                popx('金币数不能高于价格', 2)
            } else if(result.RESULTCODE == "-92017"){
                popx('商品规格不得含有/', 2)
            } else {
                popx(result.RESULTMSG, 2)
            }
        }
    })
}
// 金额格式转换, 分转元
function Fen2Yuan(num) {
    if (typeof num !== "number" || isNaN( num )) return null;
    return (num / 100).toFixed( 2 );
}