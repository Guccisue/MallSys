$(function() {
        $('#add_rubricBtn').click(
            function() {
                // 得到表单数据 var uid = $('#userName').val();
            	
                var lmName = $('#lmName').val().replace(/(^\s*)|(\s*$)/g, '');
                var lmSelted = $("#lmSelted option:selected").val();
                var parentid = 0;
            	if(lmSelted == 1){
            		parentid = $("#select_secondcategory option:selected").val();
            	}
            	if(lmName == '' || lmName == undefined || lmName == null){
            	   alert("请填写类目名称");	
            	}else if(lmSelted == 1 && parentid == 0){
            		alert("请选择上级目录");
            	}else{
            		 $.ajax({
                         url: "../../merchant/addCategory",
                         data: {
                         	name: lmName,
                         	level:lmSelted,
                         	parentId:parentid
                         },
                         type: "POST",
                         dataType: "JSON",
                         success: function(result) {
                             if (result.RESULTCODE == "0") {  
                            	 alert('添加成功');
                                 window.location.href = "rubric.html";
                             }else if(result.RESULTCODE == "-92001"){
                            	 alert("类目不能重复")
                             }else {
                                 alert("添加失败")
                             }
                         }
                         
                     })
            	}
            }
        )
    })
    
function changeFirstCategory() {
	 var status= $("#lmSelted option:selected").val();
	 if(status == 0){
		 $('#tr_secondcategory').hide();
	 }else{
		 getSecondCategory();
	 }
	
}

    
function getSecondCategory() {
	var lmSelted = $("#lmSelted option:selected").val();
    $.ajax({
        url: "../../merchant/getCategoryByParentId",
        data: {
        	parentId: 0,
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
        	
            if (result.RESULTCODE == "0") {  
            	loadSecondCategorySelected(result.RESULTJSON);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }
        
    })
	
	
}    
    // 渲染回调
function loadSecondCategorySelected(data) {
    var option = "<option selected='selected' disabled='disabled' value=''>请选择</option>";
    $("#select_secondcategory").html("");
    for (i in data) {
    	option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>"
     }
    $("#tr_secondcategory")[0].style.display="";
    $("#select_secondcategory").append(option);
//     $("#rubricList").find('tr').eq(0).before(trList);
 }

//类目名称校验

