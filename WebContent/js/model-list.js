/**
 * Created by pc on 2018/12/1.
 */
//$(function () {
//
//});
    var $url = 'http://192.168.10.108:8080';
    var pageNo = 1;
    var count = 0;
    var pageSize = $("#select_pagesize option:selected").val();
    var totalPage = 1;
    doA();
    $('#addNew').on('click', function () {
        window.location.href = 'add_model.html'
    });
// 渲染回调
    function action(json) {
        $("#goodsList").html('');
        //console.log(json)
        var data = json;
        $("#label_totalpage")[0].innerHTML = "1";
        if (null == json || "" == json || "undefined" == json) {
            return;
        }
        var trList = "";
        var list = data.templetInfoList;
        count = data.count;
        totalPage = Math.ceil(count / pageSize);
        $("#label_totalpage")[0].innerHTML = totalPage;
        $("#pageNo").text(pageNo);
        var index = 0;
        for (i in list) {
            trList += "<tr>" +
            "<td>" + list[i].id + "</td>" +
            "<td>" + list[i].name + "</td>" +
            "<td style='text-align: center;'>" +
            "<a href='#' onclick='doEdit(" + list[i].id + ")' >" +
            "<i class='glyphicon glyphicon-pencil' aria-hidden='true'></i> 编辑" +
            "</a>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "<a href='#' onclick='isDel(" + list[i].id + ")' >" +
            "<i class='glyphicon glyphicon-trash' aria-hidden='true'></i> 删除" +
            "</a>" +
            "</td>" +
            "</tr>";
            index++;
        }
        $("#goodsList").append(trList);
    }

    function lastPage() {
        if (pageNo <= 1) {
            return;
        }
        pageNo--;
        doA();
        $("#pageNo").text(pageNo);
    }

    function nextPage() {
        if (pageNo >= totalPage) {
            return;
        }
        pageNo++;
        doA();
        $("#pageNo").text(pageNo);
    }

//改变表格展示最大行数
    function changePageSize() {
        doA();
    }
    function doA() {
        pageSize = $("#select_pagesize option:selected").val();
        $.ajax({
            url: "../../templetInfo/getAllTempletInfo",
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            type: "POST",
            dataType: "JSON",
            success: function (result) {
                //console.log(result);
                if (result.RESULTCODE == "0") {
                    action(result.RESULTJSON);
                } else {
                    popx(result.RESULTMSG,2)
                }
            }

        })
    }
//编辑
    function doEdit(index){
        window.location.href = 'edit_model.html?id='+index;
    };
//删除
    function isDel(index) {
        if (confirm("确定删除吗")) {
            $.ajax({
                url: "../../templetInfo/deleteTempletInfo",
                data: {
                    id: index
                },
                type: "POST",
                dataType: "JSON",
                success: function (result) {
                    if (result.RESULTCODE == "0") {
                        popx("删除成功",2, doA());
                    } else if(result.RESULTCODE == "-92014"){
                        alert(result.RESULTMSG);
                    } else {
                        popx("删除失败",2);
                    }
                }


            })
        }
    };
