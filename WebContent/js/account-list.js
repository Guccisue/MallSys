var pageNo = 1;
var count = 0;
var pageSize = $("#select_pagesize option:selected").val();
var totalPage = 1;
$(function() {
	doA();
	getDepart();
	getMerchants();
	getRole();
})
    // 渲染回调
function action(json) {
	var list = json;
	if(null == json || "" == json || "undefined" == json){
		return;
	}
    var trList = "";
    count = json.count;
    var list = json.userInfoList;
    totalPage = Math.ceil(count / pageSize);
    $("#label_totalpage")[0].innerHTML = totalPage;
    $("#pageNo").text(pageNo);
    
    var index = 0;
    var statusName;
    for (i in list) {
    	if(list[i].type == 0){
    		typeName = "总部";
    	}else{
    		typeName = "商户";
    	}
    	if(list[i].status == 0){
    		statusName = "待审";
    	}else if(list[i].status == 1){
    		statusName = "正常";
    	}else{
    		statusName = "注销";
    	}
        trList += "<tr>" +
            "<td>" + list[i].userId + "</td>" +
            "<td>" + typeName+ "</td>" +
            "<td>" + list[i].name + "</td>" +
            "<td>" + list[i].phone + "</td>" +
            "<td>" + list[i].account + "</td>" +
            "<td>" + list[i].departName + "</td>" +
            "<td>" + list[i].roleName + "</td>" +
            "<td>" + statusName + "</td>" +
            "<td align='center'>"+
            "<div>"+
               "<a   data-toggle='modal' data-target='#account_managementAdd' title='修改' onclick='edit("+list[i].userId+","+list[i].type+");'>"+
                    "<i class='glyphicon glyphicon-pencil' aria-hidden='true'>" +"修改"+
                    "</i>"+
                "</a>"+
                "&nbsp;&nbsp;"+
                "<a onclick='isDel("+list[i].userId+");' title='注销' style='color:#CC0000'>"+
                    "<i class='glyphicon glyphicon-remove' aria-hidden='true'>" +"注销"+
                    "</i>"
                "</a>"+
            "</div>"+
            "</td>" +
            "</tr>";
        index++;
    }
    $("#accountList").find('tr').eq(0).before(trList);
}
function addAccount(){
	$("#userId").val('');
	$("#accountType").val(-1);
	$("#account").val('');
	$("#userName").val('');
	$("#phone").val('');
	$("#departType").val('');
	$("#roleType").val('');
}

function edit(userId,type){
	$.ajax({
        url: "../../user/getUserById",
        data: {
        	userId:userId
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	if(type == 0){
            		$('#merchantType').hide();
            		$('#departType').show();
            		$('#departType').val(result.RESULTJSON.departmentId);
            	}else if(type == 1){
            		$('#departType').hide();
            		$('#merchantType').show();
            		$('#merchantType').val(result.RESULTJSON.merchantId);
            	}
            	$("#userId").val(result.RESULTJSON.userId);
            	$("#accountType").val(result.RESULTJSON.type);
            	$("#account").val(result.RESULTJSON.account);
            	$("#userName").val(result.RESULTJSON.name);
            	$("#phone").val(result.RESULTJSON.phone);
            	$("#roleType").val(result.RESULTJSON.roleId);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }

    })
}
function getDepart(){
	$.ajax({
        url: "../../user/getAllDepartment",
        data: {},
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	loadDepartSelected(result.RESULTJSON.departmentInfoList);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }

    })
}

function getMerchants(){
	$.ajax({
        url: "../../merchant/queryAllMerchant",
        data: {},
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	loadMerchantsSelected(result.RESULTJSON);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }

    })
}

function getRole(){
	$.ajax({
        url: "../../user/getAllRole",
        data: {},
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	loadRoleSelected(result.RESULTJSON.roleInfoList);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }

    })
}


function getAccountType(){
	var typeValue = $('#accountType option:selected').val();
	if(typeValue == 0){
		$('#merchantType').hide();
		$('#departType').show();
		getDepart();
	}else if(typeValue == 1){
		$('#departType').hide();
		$('#merchantType').show();
		getMerchants();
	}
}
function saveAccount(){
	var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;
	var accountType = $("#accountType option:selected").val();
	var account  = $.trim($("#account").val());
	var userName = $.trim($("#userName").val());
	var phone  = $.trim($("#phone").val());
	if(accountType == 0){
		var departType = $("#departType option:selected").val();
	}else{
		var departType = $("#merchantType option:selected").val();
	}
	var roleType  =$("#roleType option:selected").val();
	if(accountType == -1){
		alert("请选择账户类型");
		return;
	}else if(account == '' || account == undefined || account == null){
		alert("请填写登陆账户");
		return;
	}else if(userName == '' || userName == undefined || userName == null){
		alert("请填写姓名");
		return;
	}else if(phone == '' || phone == undefined || phone == null){
		alert("请填写手机号码");
		return;
	}else if(!myreg.test(phone)){
		alert("手机号码格式有误");
		return;
	}else if(departType == null){
		alert("请选择部门或组织");
		return;
	}else if(roleType == null){
		alert("请选择角色");
		return;
	}else{
		$.ajax({
	        url: "../../user/addUser",
	        data: {
	        	userId:parseInt($("#userId").val() == ""?0:$("#userId").val()),
	        	type:accountType,
	        	account:account,
	        	name:userName,
	        	phone:phone,
	        	departmentId:departType,
	        	roleId:roleType
	        },
	        type: "POST",
	        dataType: "JSON",
	        success: function(result) {
	            if (result.RESULTCODE == "0") {
	            	alert("保存成功");
	            	 window.location.reload();
	            } else {
	                alert("保存失败,请检查登陆账号是否重复")
	            }
	        }

	    })
	}

}

function loadDepartSelected(data){
	  var option = "<option selected='selected' disabled='disabled' value=''>请选择</option>";
	  $("#departType").html("");
	  for (i in data) {
		  option += "<option value='" + data[i].departmentId + "'>" + data[i].name + "</option>"
	  }
	  $("#departType").append(option);
}

function loadMerchantsSelected(data){
	  var option = "<option selected='selected' disabled='disabled' value=''>请选择</option>";
	  $("#merchantType").html("");
	  for (i in data) {
		  option += "<option value='" + data[i].merchantId + "'>" + data[i].name + "</option>"
	  }
	  $("#merchantType").append(option);
}

function loadRoleSelected(data){
	 var option = "<option selected='selected' disabled='disabled' value=''>请选择</option>";
     $("#roleType").html("");
     for (i in data) {
     	option += "<option value='" + data[i].roleId + "'>" + data[i].name + "</option>"
      }
     $("#roleType").append(option);
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
function changePageSize() {
    pageNo = 1;
    doA();
}

//搜索
function doSearch(){
	$("#accountList  tr").empty("");
	$.ajax({
        url: "../../user/getAllUser",
        data: {
        	phone : $('#phonenumber').val().replace(/(^\s*)|(\s*$)/g, ''),
        	pageNo : 1,
        	pageSize : $("#select_pagesize option:selected").val(),
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	action(result.RESULTJSON);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }

    })

}

function doA() {
	$("#accountList  tr").empty("");
    $.ajax({
        url: "../../user/getAllUser",
        data: {
        	phone :  $('#phonenumber').val().replace(/(^\s*)|(\s*$)/g, ''),
        	pageNo : pageNo,
        	pageSize :  $("#select_pagesize option:selected").val(),
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	action(result.RESULTJSON);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }

    })
}
//删除
function isDel(index) {
	if(confirm("确定注销吗")){
		 $.ajax({
 	        url: "../../user/deleteUser",
 	        data: {
 	        	userId: index
 	        },
 	        type: "POST",
 	        dataType: "JSON",
 	        success: function(result) {
 	            if (result.RESULTCODE == "0") {     
 	                alert("注销成功"); 
 	                window.location.reload();
 	            } else {
 	            	alert("注销失败");
 	            }
 	        },
 	       
 	        
 	    })
	}
};
