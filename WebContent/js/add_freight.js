/**
 * Created by pc on 2019/3/27.
 */
var $url = 'http://192.168.10.108:8080';
var deliveryArea ='';
var nameArea ='';
var index = 0;
var $index = 0;
var dataAreaList = '';
var dataDistrictList = '';
//返回上一页；
function goBack(){
    history.go(-1);
}
//获取省份；
//选择发货地址；
$.ajax({
    url: "../../area.json",
    type: "POST",
    dataType: "JSON",
    success: function(data) {
        //console.log(data)
        $('#add_area').empty();
        dataAreaList = data;
        var $area = '';
        var $province = '';
        for(var i = 0; i < data.length; i++){
            $area +='<div class="left area_div">' +
            '<input type="checkbox" id="'+ data[i].code+'"/>' +
            '<label for="'+ data[i].code+'">'+ data[i].name + '</label>' +
            '</div>';
            $province +="<option value='"+ data[i].code + "'> "+ data[i].name + " </option>";
        }
        $('#add_area').append($area);
        $('#select_firstArea').append($province);
    }
});
function queryAlltwoArea(){
    $('#select_twoArea').empty();
    var $val = $('#select_firstArea').val();
    var $city = '';
    for(var i = 0; i < dataAreaList.length; i++){
        if(dataAreaList[i].code == $val){
            for(var y = 0; y < dataAreaList[i].citylist.length; y++){
                dataDistrictList = dataAreaList[i].citylist;
                $city +="<option value='"+ dataAreaList[i].citylist[y].code + "'> "+ dataAreaList[i].citylist[y].name + " </option>";
            }
        }
    }
    $('#select_twoArea').append($city);
    queryAllthreeArea();
}
function queryAllthreeArea(){
    $('#select_threeArea').empty();
    var $val = $('#select_twoArea').val();
    var $district = '';
    for(var i = 0; i < dataDistrictList.length; i++){
        if(dataDistrictList[i].code == $val){
            for(var y = 0; y < dataDistrictList[i].arealist.length; y++){
                $district +="<option value='"+ dataDistrictList[i].arealist[y].code + "'> "+ dataDistrictList[i].arealist[y].name + " </option>";
            }
        }
    }
    $('#select_threeArea').append($district);
}
function modal_hide(){
    $('#modal_area').modal('hide');
}
$('#add_List').on('click','#select_area',function(){
    $index = $(this).attr('data-id');
    //console.log($index)
});
//选择省份；
function confirm_area(){
    var $city_div = $('#add_area').find('input:checked');
    for(var a = 0; a < $city_div.length; a++){
        deliveryArea +=  ',' + $city_div.eq(a).attr('id');
        nameArea +=  ',' + $city_div.eq(a).siblings().text();
    }
    deliveryArea = deliveryArea.substring(1);
    nameArea = nameArea.substring(1);
    if(deliveryArea == '' || deliveryArea == undefined || deliveryArea == null){
        popx("请勾选限售区域",2);
        return;
    }
    //console.log(nameArea)
    $('.areaList'+$index).text(nameArea);
    $('#modal_area').modal('hide');
    $('#add_area').find('input').attr('checked',false);
    deliveryArea = '';
    nameArea = '';
}
//添加地区；
function add_area(){
    //if($('.areaList'+ index).text() == ''){
    //    popx('请先选择运送地区',2);
    //    return;
    //}
    //if($('.first'+ index).val() == ''){
    //    popx('请先填写首件数量',2);
    //    return;
    //}
    //if($('.first_pice'+ index).val() == ''){
    //    popx('请先填写首件运费',2);
    //    return;
    //}
    //if($('.tow'+ index).val() == ''){
    //    popx('请先填写续件数量',2);
    //    return;
    //}
    //if($('.tow_pice'+ index).val() == ''){
    //    popx('请先填写续件运费',2);
    //    return;
    //}
    index++;
    var trList = '<tr>' +
        '<td style="width: 400px;padding:10px;line-height: 30px;">' +
        '<div class="areaList'+ index +'"></div>' +
        '<a href="#" data-toggle="modal" data-target="#modal_area" data-id="'+ index +'" id="select_area">选择省份' +
        '</a>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="first'+ index +'"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="first_pice'+ index +'"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="tow'+ index +'"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="tow_pice'+ index +'"/>' +
        '</td>' +
        '<td style="width:100px;">' +
        '<a href="#" class="delete_list">' +
        '<i class="glyphicon glyphicon-trash" aria-hidden="true"></i> 删除' +
        '</a>' +
        '</td>' +
        '</tr>';
    $('#add_List').append(trList);
}
//删除；
$('#add_List').on('click','.delete_list',function(){
    //console.log($('#add_List tr').length);
    if($('#add_List tr').length == 1){
        popx('至少保留一条数据',2);
        return;
    }
    $(this).parent().parent().remove();
    index--;
});
//支持限售区域；
function curbSales1() {
    $('#add_List').empty();
    var trList ='<tr>' +
        '<td style="width: 400px;padding:10px;line-height: 30px;">' +
        '<div class="areaList0"></div>' +
        '<a href="#" data-toggle="modal" data-target="#modal_area" data-id="0" id="select_area">选择省份' +
        '</a>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="first0"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="first_pice0"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="tow0"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="tow_pice0"/>' +
        '</td>' +
        '<td style="width:100px;">' +
        '<a href="#" class="delete_list">' +
        '<i class="glyphicon glyphicon-trash" aria-hidden="true"></i> 删除' +
        '</a>' +
        '</td>' +
        '</tr>';
    $('#add_List').append(trList);
}
//不支持限售区域；
function curbSales2() {
    $('#add_List').empty();
    var trList = '<tr>' +
        '<td style="width: 400px;padding:10px;line-height: 30px;">默认地区' +
        '<div class="areaList"></div>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="first"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="first_pice"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="tow"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="tow_pice"/>' +
        '</td>' +
        '<td style="width:100px;">' +
        '</td>' +
        '</tr>' +
        '<tr>' +
        '<td style="width: 400px;padding:10px;line-height: 30px;">' +
        '<div class="areaList0"></div>' +
        '<a href="#" data-toggle="modal" data-target="#modal_area" data-id="0" id="select_area">选择省份' +
        '</a>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="first0"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="first_pice0"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="tow0"/>' +
        '</td>' +
        '<td>' +
        '<input type="number" class="tow_pice0"/>' +
        '</td>' +
        '<td style="width:100px;">' +
        '<a href="#" class="delete_list">' +
        '<i class="glyphicon glyphicon-trash" aria-hidden="true"></i> 删除' +
        '</a>' +
        '</td>' +
        '</tr>';
    $('#add_List').append(trList);
}
//保存；
function add_ModelBtn(){
    console.log($('#select_firstArea').val());
    console.log($('#select_twoArea').val());
    console.log($('#select_threeArea').val());
}
$(document).ready(function(){
    var ipt = $('.first0');
    ipt.on('keyup',function(){
        if(! /^\d+$/.test(this.value)){
            this.value='';
        }
    });
});