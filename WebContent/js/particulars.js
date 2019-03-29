/**
 * Created by pc on 2019/2/20.
 */
var $url = 'http://192.168.10.13:8080';
var $index = 1;
var index = 0;
var seckill = -1;  //判断是否秒杀时间；
var parentId,categoryId,templetId;
$(function() {
    var goodsId;
    Request = GetRequest();
    goodsId = Request['goodsId'];
    getGoodsById(goodsId);
    getseckill();
});
//秒杀;
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
//图片放大
 $('body').on('click','.preview',function(){
    	var _this = $(this);//将当前的pimg元素作为_this传入函数  
        imgShow("#outerdiv", "#innerdiv", "#bigimg", _this); 
    });

function imgShow(outerdiv, innerdiv, bigimg, _this){  
    var src = _this.attr("src");//获取当前点击的pimg元素中的src属性  
    $(bigimg).attr("src", src);//设置#bigimg元素的src属性  
  
        /*获取当前点击图片的真实大小，并显示弹出层及大图*/  
    $("<img/>").attr("src", src).load(function(){  
        var windowW = $(window).width();//获取当前窗口宽度  
        var windowH = $(window).height();//获取当前窗口高度  
        var realWidth = this.width;//获取图片真实宽度  
        var realHeight = this.height;//获取图片真实高度  
        var imgWidth, imgHeight;  
        var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放  
          
        if(realHeight>windowH*scale) {//判断图片高度  
            imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放  
            imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度  
            if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度  
                imgWidth = windowW*scale;//再对宽度进行缩放  
            }  
        } else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度  
            imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放  
                        imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度  
        } else {//如果图片真实高度和宽度都符合要求，高宽不变  
            imgWidth = realWidth;  
            imgHeight = realHeight;  
        }  
                $(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放  
          
        var w = (windowW-imgWidth)/2;//计算图片与窗口左边距  
        var h = (windowH-imgHeight)/2;//计算图片与窗口上边距  
      //  $(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性  
        $(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg  
    });  
      
    $(outerdiv).click(function(){//再次点击淡出消失弹出层  
        $(this).fadeOut("fast");  
    });  
}
//初始化
function action(json) {
    console.log(json)
    var data = json;
    $("#shId").val(data.name);
    $("#sellingPoint").val(data.sellingPoint);
    parentId=data.parentId;
    categoryId=data.categoryId;
    templetId=data.templetId;
  	console.log(parentId,categoryId,templetId);
    queryAllFirstRubric();
    queryAllSecondRubric();
    queryAllmodel();
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
                '<input disabled="disabled" type="checkbox" id="'+ data[i].code +'"/>' +
                '<label for="'+ data[i].code +'">'+ data[i].name +'</label><div class="add_city">';
                var $city = '';
                for(var j = 0; j < data[i].citylist.length; j++){
                    $city +=
                        '<div class="area_div city_div">' +
                        '<input disabled="disabled" type="checkbox" id="'+ data[i].citylist[j].code +'"/>' +
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
    console.log(pic01);
    for(var x = 0; x < pic01.length; x++){
        $('#preview'+x).attr("src",pic01[x].url);
        $('#preview'+x).parents().siblings('.addImg').hide();
        $('#preview'+x).parents().siblings('.delete').show();
        $('#pic'+x).val(pic01[x].url);
        $('#pic'+x+'Size').val(pic01[x].size);
        $('.showpic').eq(x).show();
    }
    for(var y = 0; y < pic02.length; y++){
        var $addImg = '<div class="item" style="border: none;">' +
            '<img class="addImg" onclick="clickImg(this);" src="../../img/standard_default3.png" style="height: 140px;display: none;"/>' +
            '<input name="addurl'+ $index +'" id="addurl'+ $index +'" type="file" class="upload_input" onchange="addchange_xq(this)" style="border:none;visibility: hidden;"/>' +
            '<div class="preBlock" style="width: 100%;"> ' +
            '<img class="preview" id="addpreview" alt="" src="'+pic02[y].url +'" name="pic" style="display: block;height: 140px;">' +
            '</div>' +
            //'<img class="delete" onclick="deleteImg_xq(this)" id="'+ $index +'" src="../../img/delete.png" style="display: block;">' +
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
            '<input class="sellingPoint" readonly type="text" value="'+ data.attributeList[i].name +'" style="height:30px;width: 200px;padding:0 10px;margin:0 20px 0 30px;"/>' +
            //'<input class="btn btn-outline btn-default" type="button" value="新增属性" id="addProperty" style="background:#1ab394; color:white;border:none;">' +
            '</div>' +
            '</td>' +
            '</tr>'+
            '<tr id="table_color_tr">' +
            '<th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;">属性值</th>' +
            '<td style="width: 90%;border-top:none;padding-left: 30px;">'
            //'<input class="property_val" readonly type="text" placeholder="请输入属性值，如：红色" value="" style="height:30px;width: 200px;padding:0 10px;margin-right: 20px;"/>'
            //'<input class="btn btn-outline btn-default" type="button" value="新增" style="background:#1ab394; color:white;border:none;" onclick="addProperty_value(this)">';

        }else{
            trList01 += '<tbody class="tbodyList">' +
            '<tr><th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%;border-top:none;">属性名称</th>' +
            '<td style="background: white;border-top:none;">' +
            '<div class="items_addForm">' +
            '<input class="sellingPoint" readonly type="text" value="'+ data.attributeList[i].name +'" style="height:30px;width: 200px;padding:0 10px;margin:0 20px 0 30px;"/>' +
            //'<input class="btn btn-outline btn-default" type="button" value="删除属性" id="editProperty" style="background:red; color:white;border:none;">' +
            '</div>' +
            '</td>' +
            '</tr>'+
            '<tr id="table_color_tr">' +
            '<th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;">属性值</th>' +
            '<td style="width: 90%;border-top:none;padding-left: 30px;">'
            //'<input class="property_val" readonly type="text" placeholder="请输入属性值，如：红色" value="" style="height:30px;width: 200px;padding:0 10px;margin-right: 20px;"/>'
            //'<input class="btn btn-outline btn-default" type="button" value="新增" style="background:#1ab394; color:white;border:none;" onclick="addProperty_value(this)">';
        }
        var trList02 = '';
        for(var j = 0; j < data.attributeList[i].valueList.length; j++){
            trList02 += '<div class="add_div">' +
            '<input disabled="disabled" type="checkbox" checked="checked" id="addInput'+ data.attributeList[i].valueList[j].id +'"/>' +
            '<label for="addInput'+ data.attributeList[i].valueList[j].id +'">'+ data.attributeList[i].valueList[j].attributeValue +'</label>' +
            '<a onclick="eisDelProperty_value(this);" style="color:#CC0000"> ' +
            '<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>' +
            '</a>' +
            '</div>';
        }
        trList01 += trList02 + '</td></tr></tbody>';
    }
    $('#table_Property').append(trList01);
    for(var z = 0; z < data.standardList.length; z++){
        var $price = Fen2Yuan(data.standardList[z].marketPrice);
        var $marketPrice = Fen2Yuan(data.standardList[z].price);
        if(data.standardList[z].pic){
            var addtr = '<tr class="tr_standard_class">' +
                '<td style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;"></td>' +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input disabled='disabled' value='"+ data.standardList[z].attributeValues +"' style='width: 50%; height: 30px;text-align: center;' type='text' class='standard' data-id='" + index + "'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='stock" + index + "' value='"+ data.standardList[z].stock +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='price" + index + "' value='"+ $price +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='marketPrice" + index + "' value='"+ $marketPrice +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 10%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='goldNumber" + index + "' value='"+ data.standardList[z].goldNumber +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 20%;text-align: center;'>" +
                "<div class='item itm_addimgs' style='height:80px;width:100%;left:0;'>" +
                "<img class='icon addImg addImg_xq' onclick='clickImg(this);' src='../../img/standard_default.png' style='display: none'/>" +
                "<input readonly name='standardpic" + index + "' id='standardpic" + index + "' type='file'class='upload_input' onchange='addchange(this)' />" +
                "<input readonly type='hidden' id='spic" + index + "' data-type='3'/>" +
                "<div class='preBlock addImg_xq'>" +
                "<img class='preview' alt='' name='pic' style='height: 60px;width: 60px;' data-type='3' src='"+ data.standardList[z].pic +"'/>" +
                "</div>" +
                //"<img class='delete deleteSmall' onclick='deleteImg(this)' src='../../img/delete.png' style='display: block'/>" +
                "</div>" +
                "</td>" +
                '</tr>';
        }else{
            var addtr = '<tr class="tr_standard_class">' +
                '<td style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;"></td>' +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input disabled='disabled' value='"+ data.standardList[z].attributeValues +"' style='width: 50%; height: 30px;text-align: center;' type='text' class='standard' data-id='" + index + "'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='stock" + index + "' value='"+ data.standardList[z].stock +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='price" + index + "' value='"+ $price +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='marketPrice" + index + "' value='"+ $marketPrice +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 10%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' type='text' id='goldNumber" + index + "' value='"+ data.standardList[z].goldNumber +"'/></td>" +
                "<td class='items_sizetable_td' style='width: 20%;text-align: center;'>" +
                "<div class='item itm_addimgs' style='height:80px;width:100%;left:0;'>" +
                "<img class='icon addImg addImg_xq' onclick='clickImg(this);' src='../../img/standard_default.png'/>" +
                "<input readonly name='standardpic" + index + "' id='standardpic" + index + "' type='file'class='upload_input' onchange='addchange(this)' />" +
                "<input readonly type='hidden' id='spic" + index + "' data-type='3'/>" +
                "<div class='preBlock addImg_xq'>" +
                "<img class='preview' alt='' name='pic' style='height: 60px;width: 60px;' data-type='3' src=''/>" +
                "</div>" +
                //"<img class='delete deleteSmall' onclick='deleteImg(this)' src='../../img/delete.png'/>" +
                "</div>" +
                "</td>" +
                '</tr>';
        }
        index++;
        $('#table_standard').append(addtr);
    }
}
//展示二级地区；
$('#add_area').on('mouseover','.area_div',function(){
    $(this).find('.add_city').stop().fadeIn(100);
}).mouseout(function(){
    $(this).find('.add_city').stop().fadeOut(100);
});
//一级类目
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
                //console.log(result)
                loadFirstCategorySelected(result.RESULTJSON.categoryInfoList);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
//二级类目
function queryAllSecondRubric(){
    var $value = $('#select_firstcategory option:selected').val();
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
                //var res = result.RESULTJSON;
                //console.log(res)
                loadSecondCategorySelected(result.RESULTJSON.categoryInfoList);
                //for(i in res){
                //    if($parentId == res[i].parentId){
                //        var $data = res[i].categoryInfoList;
                //        //console.log($data);
                //        loadSecondCategorySelected_edit(result.RESULTJSON.categoryInfoList,categoryId);
                //    }
                //}
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
function queryAllmodel() {
	console.log(categoryId,templetId)
    $.ajax({
        url: "../../templetInfo/getTempletInfo",
        data: {
            id:templetId
        },
        type: "POST",
        dataType: "JSON",
        success: function (result) {
            //console.log(result);
            if (result.RESULTCODE == "0") {
                loadModelSelected(result.RESULTJSON);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
function loadFirstCategorySelected(data) {
    //console.log(data)
    $("#select_firstcategory").html("");
    var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
    for (i in data) {
        if(data[i].categoryId == parentId){
            option += "<option selected='selected' value='" + data[i].categoryId + "'>" + data[i].name + "</option>";
        }else{
            option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>";
        }
    }
    $("#select_firstcategory").append(option);
}

function loadSecondCategorySelected(data) {
    //console.log(data)
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
function loadModelSelected(data){
    $("#select_model").html("");
   var option = "<option selected='selected'>" + data.name + "</option>";
    /*var option = "<option selected='selected' disabled='disabled' value='-1'>请选择</option>";
    for (i in data) {
        if(data[i].id == templetId){
            option += "<option selected='selected' value='"+ data[i].id +"'>" + data[i].name + "</option>";
        }else{
            option += "<option value='" + data[i].id + "'>" + data[i].name + "</option>";
        }
    }*/
    $("#select_model").append(option);
}
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
function goBack(){
    history.go(-1);
}
// 金额格式转换, 分转元
function Fen2Yuan(num) {
    if (typeof num !== "number" || isNaN( num )) return null;
    return (num / 100).toFixed( 2 );
}