$(function() {
	getFirstCategory();
	function GetRequest() {
	    var url = location.search; //获取url中"?"符后的字串
	    var theRequest = new Object();
	    if (url.indexOf("?") != -1) {
	        var str = url.substr(1);
	        strs = str.split("&");
	        for (var i = 0; i < strs.length; i++) {
	            theRequest[strs[i].split("=")[0]] = decodeURIComponent(strs[i].split("=")[1]);
	        }
	    }
	    return theRequest;
	}
	var a = GetRequest();
	var index_1 = a['id'];
	var level_1 = a['level'];
	getDataById(index_1,level_1);
	
	$('#edit_rubricBtn').click(
			  function() {
			    var lmName = $('#lm_name').val().replace(/(^\s*)|(\s*$)/g, '');
			    var lmSelted = $("#lmSelted option:selected").val();
			    var parentid = 0;
				if(lmSelted == 1){
					parentid = $("#select_secondcategory option:selected").val();
				}
				if(lmName == '' || lmName == undefined || lmName == null){
				   alert("请填写类目名称");	
				}else{
					 $.ajax({
			             url: "../../merchant/updateCategory",
			             data: {
			            	categoryId:index_1,
			             	name: lmName,
			             	level:lmSelted,
			             	parentId:parentid
			             },
			             type: "POST",
			             dataType: "JSON",
			             success: function(result) {
			                 if (result.RESULTCODE == "0") { 
			                	 alert("修改成功");
			                     window.location.href = "rubric.html";
			                 } else if(result.RESULTCODE == "-92001"){
                            	 alert("类目不能重复")
                             }else {
			                     alert("添加失败")
			                 }
			             }
			         })
				}
			} 
		 );
})
function goBack() {
	history.go(-1);
}
function getDataById(categoryId,level){
	if(level == 0){
		$("#parent_level").hide();
    	$("#lm_level").hide();
	}
	$.ajax({
        url: "../../merchant/getCategoryByCategoryId",
        data: {
        	categoryId: categoryId
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {  
            	$("#lm_name").val(result.RESULTJSON.name);
            	$("#lmSelted").val(result.RESULTJSON.level);
                if(result.RESULTJSON.level == 0){//一级类目
                	
                }else{//二级类目
                	$("#first_lm_id").val(result.RESULTJSON.parentId);
                }
            } else {
                alert("id传递失败")
            }
        }
        
    })
}

function changeFirstCategory() {
	 var status= $("#lmSelted option:selected").val();
	 if(status == 0){
		 $("#parent_level").hide();
	 }else{
		 getFirstCategory();
	 }
	
}
//获取一级类目
function getFirstCategory() {
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

function loadSecondCategorySelected(data) {
    var option = "";
    for (i in data) {
    	option += "<option value='" + data[i].categoryId + "'>" + data[i].name + "</option>"
     }
    $("#select_secondcategory").append(option);
    var selectobj = document.getElementById("select_secondcategory");
    for(var i=0; i<selectobj.options.length; i++){ 
    	if(selectobj.options[i].value == $("#first_lm_id").val()){ 
    		selectobj.options[i].selected = true; 
    	break; 
    	} 
    }
 }
