var pageNo = 1;
var count = 0;
var pageSize = $("#select_pagesize option:selected").val();
var totalPage = 1;
$(function() {
	doA();
	doB();
})
    // 渲染回调
function action(json) {
	var list = json;
	if(null == json || "" == json || "undefined" == json){
		return;
	}
    var trList = "";
    count = json.count;
    var list = json.roleInfoList;
    totalPage = Math.ceil(count / pageSize);
     $("#label_totalpage")[0].innerHTML = totalPage;
    $("#pageNo").text(pageNo);
    count = list.count;   
    var index = 0;
    var statusName;
    for (i in list) {
    	if(list[i].status == 0){
    		statusName = "待审";
    	}else if(list[i].status == 1){
    		statusName = "正常";
    	}else{
    		statusName = "注销";
    	}
        trList += "<tr>" +
            "<td>" + list[i].roleId + "</td>" +
            "<td>" + list[i].name+ "</td>" +
            "<td>" + statusName + "</td>" +
            "<td>" + "<div id='exampleTableEventsToolbar' role='group'>"+
            "<a type='button' data-toggle='modal' data-target='#authority' onclick='queryByRoleId("+list[i].roleId+");' class='btn btn-outline btn-default' style='background:#1ab394;color:white;'>"+
            "设置权限</a></div>" +
            "</td>" +
            "<td align='center'>"+
            "<div>"+
               "<a   data-toggle='modal' data-target='#account_managementUpdate' title='修改' onclick='edit("+list[i].roleId+");'>"+
                    "<i class='glyphicon glyphicon-pencil' aria-hidden='true'>" +"修改"+
                    "</i>"+
                "</a>"+
                "&nbsp;&nbsp;"+
                "<a onclick='isDel("+list[i].roleId+");' title='删除' style='color:#CC0000'>"+
                    "<i class='glyphicon glyphicon-remove' aria-hidden='true'>" +"删除"+
                    "</i>"+
                "</a>"+
            "</div>"+
            "</td>" +
            "</tr>";
        index++;
    }
    $("#roleList").find('tr').eq(0).before(trList);
}

function edit(roleId){
	$.ajax({
        url: "../../user/getRoleById",
        data: {
        	roleId:roleId
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {  
            	$('#roleId').val(result.RESULTJSON.roleId);
            	$('#roleName_update').val(result.RESULTJSON.name);
            } else {
            	alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }
        
    })
}

function queryByRoleId(roleId){
	$("#uls").hide();
	$("#roleId_update").val(roleId);
	//先清空checkbox
	$("input[id^='checkbox']").each(function(){
        $(this).prop('checked',false);
    })
	$.ajax({
        url: "../../user/getAuthByRoleId",
        data: {
        	roleId:roleId
        },
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {  
            	if(result.RESULTJSON != ""){
            		var resultData = result.RESULTJSON[0].child;
                	for(var k=0;k<resultData.length;k++){
                		for(i in resultData[k].child){
                			if(resultData[k].child[i].binded == 0){
                				$("#checkbox"+resultData[k].child[i].authid).prop("checked",true);
                			}
                		}
                	}
            	}
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }
        
    })
}
function saveRole(){
	 var roleName = $("#roleName_add").val().replace(/(^\s*)|(\s*$)/g, '');
	 if(roleName == '' || roleName == undefined || roleName == null){
		alert("请填写角色名称");
	 }else{
		$.ajax({
	        url: "../../user/roleNameCheck",
	        data: {
	        	name:roleName
	        },
	        type: "POST",
	        dataType: "JSON",
	        success: function(result) {
	            if (result.RESULTCODE == "1") {  
	            	alert("角色名称不能重复");
	            	// window.location.reload();
	            } else {
	            	$.ajax({
	        	        url: "../../user/addRole",
	        	        data: {
	        	        	name:roleName
	        	        },
	        	        type: "POST",
	        	        dataType: "JSON",
	        	        success: function(result) {
	        	            if (result.RESULTCODE == "0") {  
	        	            	 alert("保存成功");
	        	            	 window.location.reload();
	        	            } else {
	        	            	alert(result.RESULTCODE + ":" + result.RESULTMSG)
	        	            }
	        	        }
	        	        
	        	    })
	            }
	        }
	        
	    })
	}
	
}

function updateRole(){
	 var roleName = $("#roleName_update").val().replace(/(^\s*)|(\s*$)/g, '');
	 if(roleName == '' || roleName == undefined || roleName == null){
		alert("请填写角色名称");
	 }else{
		$.ajax({
	        url: "../../user/roleNameCheck",
	        data: {
	        	name:roleName
	        },
	        type: "POST",
	        dataType: "JSON",
	        success: function(result) {
	            if (result.RESULTCODE == "1") {  
	            	alert("角色名称不能重复");
	            } else {
	            	$.ajax({
	        	        url: "../../user/updateRole",
	        	        data: {
	        	        	roleId:parseInt($("#roleId").val()),
	        	        	name:roleName
	        	        },
	        	        type: "POST",
	        	        dataType: "JSON",
	        	        success: function(result) {
	        	            if (result.RESULTCODE == "0") {  
	        	            	 alert("修改成功");
	        	            	 window.location.reload();
	        	            } else {
	        	            	alert(result.RESULTCODE + ":" + result.RESULTMSG)
	        	            }
	        	        }
	        	        
	        	    })
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

//改变表格展示最大行数
function changePageSize() {
    doA();
}




//搜索
function doSearch(){
	$("#roleList  tr").empty("");
	$.ajax({
        url: "../../user/getAllUser",
        data: { 
        	phone : $('#phonenumber').val(),
        	pageNo : 1,
        	pageSize :  $("#select_pagesize option:selected").val()
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
	$("#roleList  tr").empty("");
    $.ajax({
        url: "../../user/getAllRole",
        data: {
        	pageNo : pageNo,
        	pageSize : pageSize
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
	if(confirm("确定删除吗")){
		 $.ajax({
 	        url: "../../user/deleteRole",
 	        data: {
 	        	roleId: index
 	        },
 	        type: "POST",
 	        dataType: "JSON",
 	        success: function(result) {
 	            if (result.RESULTCODE == "0") {     
 	                alert("删除成功"); 
 	                window.location.reload();
 	            } else if(esult.RESULTCODE == "-92002"){
 	            	alert("用户存在绑定关系,无法删除！"); 
 	            	
 	            }
 	            else {
 	            	alert("删除失败");
 	            }
 	        }
 	       
 	        
 	    })
	}
};

function doB() {
	$("#tree_root").empty("");
    $.ajax({
        url: "../../user/getAllAuth",
        data: {},
        type: "POST",
        dataType: "JSON",
        success: function(result) {
            if (result.RESULTCODE == "0") {
            	action2(result.RESULTJSON[0].child);
            } else {
                alert(result.RESULTCODE + ":" + result.RESULTMSG)
            }
        }
        
    })
}


function action2(json) {
	var list = json;
	if(null == json || "" == json || "undefined" == json){
		return;
	}
    var ulList = "";
    var index = 0;
    for (i in list) {
    	ulList += "<li>" +
    	    "<span class='tree2'><b class='Off'>"+list[i].name+"</b></span>"+
            " <ul style='display:none;' id='uls'>";
    	     var liList = "";
             for(j in list[i].child){
            	 liList +="<li><span class='tree3'><input type='checkbox' id='checkbox"+list[i].child[j].authid+"' name='checkbox' value='"+list[i].child[j].authid+"'><b>"+list[i].child[j].name+"</b></span></li>"
             }
             ulList  += liList+"</ul>"+
            "</li>";
        index++;
    }
    $("#tree_root").html(ulList);
    $("#tree_root").find("li").children("span").click(function() {
	    setTreeStyle($(this));
	});
}


//目录树折叠按钮 -------------------------------
function setTreeStyle(obj) {
    var objStyle = obj.children("b");
    var objList = obj.siblings("ul");
    if (objList.length == 1) {
        var style = objStyle.attr("class");
        objStyle.attr("class", "On2Off");
        setTimeout(
            function() {
                if (style == "Off") {
                    objList.parent().siblings("li").children("span").children(".On").each(function() {
                        setTreeStyle($(this).parent());
                    });
                    var H = objList.innerHeight()
                    objList.css({
                        display: "block",
                        height: "0"
                    });
                    objList.animate({
                        height: H
                    }, 300, function() {
                        $(this).css({
                            height: "auto"
                        });
                    });
                    objStyle.attr("class", "On");
                } else if (style == "On") {
                    objList.find("li").children("span").children(".On").each(function() {
                        setTreeStyle($(this).parent());
                    });
                    var H = objList.innerHeight()
                    objList.animate({
                        height: 0
                    }, 300, function() {
                        $(this).css({
                            height: "auto",
                            display: "none"
                        })
                    });
                    objStyle.attr("class", "Off");
                }
            },
            42
        );
    }
}

function saveAuthority(){
	 var obj = document.getElementsByName('checkbox'); //选择所有name="'checkbox'"的对象，返回数组 
     //取到对象数组后，循环检测它是不是被选中 
     var s = '';
     for (var i = 0; i < obj.length; i++) {
         if (obj[i].checked) s += obj[i].value + ','; //如果选中，将value添加到变量s中 
     }
     if(s != ""){
    	 s= s.substr(0,s.length-1)
     }
     if(s == ''){
    	 alert("请至少选择一个权限");
     }else{
    	 $.ajax({
             url: "../../user/doRoleAuthBind",
             data: {roleId:$("#roleId_update").val(),authIds:s},
             type: "POST",
             dataType: "JSON",
             success: function(result) {
                 if (result.RESULTCODE == "0") {
                 	alert("设置成功");
                 	 window.location.reload();
                 } else {
                     alert(result.RESULTCODE + ":" + result.RESULTMSG)
                 }
             }
             
         }) 
     }
     
}
