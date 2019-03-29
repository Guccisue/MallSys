/**
 * Created by pc on 2019/3/27.
 */
var $index= 0;
//删除；
function modal_hide(){
    $('#modal_delete').modal('hide');
}
function delete_freight(){
    $('.freight_model').eq($index).remove();
    $('#modal_delete').modal('hide');
}
$('#add_List').on('click','.delete',function(){
    $index = $(this).attr('data-id');
    //console.log($index)
});
//修改页面；
function doEdit(index){
    window.location = "edit_freight.html?goodsId=" + index ;
}