var pageNo = 1;
var count = 0;
var totalPage = 1;
var pageSize = $("#select_pagesize option:selected").val();

// 渲染回调
function action(json) {
	var data = json;
	$("#rubricList").html("");    
	$("#label_totalpage")[0].innerHTML = "1";
	if(null == json || "" == json || "undefined" == json){
		return;
	}
    var trList = "";
    var list = data.categoryInfoList;
    count = data.count;
    totalPage = Math.ceil(count / pageSize);
    $("#label_totalpage")[0].innerHTML = totalPage;
    $("#pageNo").text(pageNo);
    var index = 0;
    var levelName;
    for (i in list) {
    	if(list[i].level == 0){
    		levelName = "一级类目"
    	}else{
    		levelName = "二级类目"
    	}
        trList += "<tr>"+
        "<td id='td_id_" + index + "'>"+ list[i].categoryId +"</td>"+
        "<td>"+ list[i].name +"</td>"+
        "<td>"+levelName +"</td>"+
        "<td align='center'>"+
            "<div>"+
               "<a onclick='update("+list[i].categoryId+","+list[i].level+");' title='修改'>"+
                    "<i class='glyphicon glyphicon-pencil' aria-hidden='true'>" +"修改"+
                    "</i>"+
                "</a>"+
                "&nbsp;&nbsp;"+
                "<a onclick='isDel("+list[i].categoryId+");' title='删除' style='color:#CC0000'>"+
                    "<i class='glyphicon glyphicon-remove' aria-hidden='true'>" +"删除"+
                    "</i>"
                "</a>"+
            "</div>"+
        "</td>"+
    "</tr>"
        index++;
    }
    $("#rubricList").append(trList);
}


//下一页
function nextPage(){
	if(pageNo >= totalPage){
		return;
	}
	pageNo++;
	getAllByPage();
	$("#pageNo").text(pageNo);
};
//上一页
function lastPage(){
	if(pageNo <= 1){
		return;
	}
	pageNo--;
	getAllByPage();
	$("#pageNo").text(pageNo);
}
//搜索
function doSearch(){
	var pageNoS = 1;
	getAll();
	 $("#pageNo").text(pageNo);
	
}


function update(index,level){
	window.location.href='modify_rubric.html?id='+index+"&level="+level;
}

//删除
function isDel(index) {  
	if(confirm("确定删除吗?")){
	 $.ajax({
	        url: "../../merchant/deleteCategory",
	        data: {
	        	categoryId: index
	        },
	        type: "POST",
	        dataType: "JSON",
         success: function(result) {
             if (result.RESULTCODE == "0") {    //后端删除成功
                 alert("删除成功");
                 window.location.reload();
             }else if(result.RESULTCODE == "-92002"){
                 alert("该类目下还有商品处于上线状态，无法删除");
             }else if(result.RESULTCODE == "-92008"){
                 alert("该类目下还有二级类目，无法删除");
             }else if(result.RESULTCODE == "-92014"){
                 alert(result.RESULTMSG);
             }else if(result.RESULTCODE == "-92009"){
                 alert("固定类目，无法删除");
             } else{
                 alert("删除失败");
             }
         }
	    })
	}
};
//页面渲染

$(function() {
	getAll();
})

function getAll(){
	pageSize = $("#select_pagesize option:selected").val();
	$.ajax({
	    url: "../../merchant/queryAllCategory",
	    data: {
	    	level :-1,
	    	pageNo : pageNo,
	    	pageSize : pageSize
	    },
	    type: "POST",
	    dataType: "JSON",
	    success: function(result) {
	        if (result.RESULTCODE == "0") {
	        	action(result.RESULTJSON);
	        } else {
	            alert(result.RESULTMSG)
	        }
	    }

	})
}

//改变表格展示最大行数
function changePageSize() {
	getAllByPage();
}

function getAllByPage(){
	pageSize = $("#select_pagesize option:selected").val();
	$.ajax({
	    url: "../../merchant/queryAllCategory",
	    data: {
	    	level :-1,
	    	pageNo : pageNo,
	    	pageSize : pageSize
	    },
	    type: "POST",
	    dataType: "JSON",
	    success: function(result) {
	        if (result.RESULTCODE == "0") {
	        	action(result.RESULTJSON);
	        } else {
	            alert(result.RESULTMSG)
	        }
	    }
	    
	})
}

