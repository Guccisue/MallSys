/**
 * Created by pc on 2018/11/30.
 */
var $url = 'http://192.168.10.108:8080';
var $index = 0;
function goBack(){
    history.go(-1);
}
$('#addProperty').on('click',function(){
    var $property = $('.sellingPoint');
    for(var i = 0; i< $property.length; i++) {
        var $propertyValue = $property.eq(i).val();
        if (!$propertyValue) {
            popx('请填属性名称', 2);
            $property.eq(i).focus();
            return;
        }
    }
    var trList = '<tbody class="tbodyList"><tr>' +
        '<th style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%;border-top:none;">属性名称</th>' +
        '<td style="background: white;border-top:none;">' +
        '<div class="items_addForm">' +
        '<input class="sellingPoint" type="text" placeholder="请输入属性名称，如：颜色" style="height:30px;padding:0 10px;width:200px;margin:0 20px 0 30px;"/>' +
        '<input class="btn btn-outline btn-default" type="button" value="删除属性" style="background:red; color:white;border:none;" onclick="editProperty(this)">' +
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
function editProperty(object){
    $(object).parents().parents().parents().parents('tbody').remove();
};
function addProperty_value(object){
    var $property_val = $(object).siblings('.property_val').val().replace(/(^\s*)|(\s*$)/g, "");
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
queryAllFirstRubric();
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
function queryAllSecondRubric(){
    $.ajax({
        url: "../../merchant/getAllCategory",
        data: {
            parentId:$('#select_firstcategory option:selected').val()
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
                loadSecondCategorySelected(result.RESULTJSON.categoryInfoList);
            } else {
                popx(result.RESULTMSG,2)
            }
        }

    })
}
function loadFirstCategorySelected(data) {
    var option = "<option selected='selected' disabled='disabled' value='-1'> -请选择- </option>";
    $("#select_firstcategory").html("");
    for (i in data) {
        option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>"
    }
    $("#select_firstcategory").append(option);
}

function loadSecondCategorySelected(data) {
    var option = "<option selected='selected' disabled='disabled' value='-1'> -请选择- </option>";
    $("#select_secondcategory").html("");
    for (i in data) {
        option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>"
    }
    $("#select_secondcategory").append(option);
}
$('#add_ModelBtn').on('click',function(){
    var $name = $('#shId').val();
    var $categoryId = $('#select_secondcategory').val();
    if($name == '' || $name == undefined || $name == null){
        popx("请输入模板名称",2);
        return;
    }
    if($categoryId == '' || $categoryId == undefined || $categoryId == null){
        popx("请选择所属类目",2);
        return;
    }
    var modelList = [];
    var $property = $('.sellingPoint');
    for(var i = 0; i< $property.length; i++) {
        var $propertyValue = $property.eq(i).val().replace(/(^\s*)|(\s*$)/g, "");
        if (!$propertyValue) {
            popx('请填属性名称', 2);
            $property.eq(i).focus();
            return;
        }else{
            var list = {
                'name':$property.eq(i).val(),
                'value':''
            };
            var $property_val = $property.eq(i).parents('.tbodyList').find('.add_div');
            if($property_val.length > 0){
                for(var j = 0; j< $property_val.length; j++) {
                    var $property_val_checked = $property_val.eq(j).find('label').text().replace(/(^\s*)|(\s*$)/g, "");
                    list.value += ',' + $property_val_checked;
                }
            }else{
                popx('请填属性值', 2);
                return;
            }

            list.value = list.value.substring(1);
            modelList.push(list);
        }
    }
    $.ajax({
        url: "../../templetInfo/addTempletInfo",
        data: {
            name: $name,
            categoryId: $categoryId,
            attributeArray: JSON.stringify(modelList)
        },
        type: "POST",
        dataType: "JSON",
        success: function (result) {
            console.log(result);
            if (result.RESULTCODE == "0") {
                popx('模板新建成功', 2,function(){window.location.href = 'model_list.html'});
            }else if(result.RESULTCODE == "-92017"){
                popx('商品规格不得含有/', 2)
            } else {
                popx(result.RESULTMSG, 2);
            }
        }
    });
});