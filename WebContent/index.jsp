<%@page language="java" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
String menuJson = "";
String account = "";
try{
	menuJson = session.getAttribute("menu").toString();
	account =  session.getAttribute("account").toString();
}catch(Exception e){
	response.sendRedirect("login.html");
}
%>
<!DOCTYPE html>
<html>

<head>
    <base href="">
    <title>金吉鸟商城管理系统—首页</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link href="ico/LuckyBird.ico" rel="icon">
    <link href="css/style.min862f.css?v=4.1.1" rel="stylesheet">
    <link href="css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="css/font-awesome.min93e3.css?v=4.4.1" rel="stylesheet">
    <link href="css/animate.min.css" rel="stylesheet">
    <script src="js/jquery-1.9.1.js"></script>
    <link rel="stylesheet" href="./layui/css/x-admin.css" media="all">
    <script type="text/javascript">

        $(window).resize(function () {
            resize();
        });

        function resize() {
            $("#right").css({"width": $(document).width() - $("#left").width(), "height": $("#left").height()});
            $("#head").css({"width": $(document).width()});
            $("#mainFrame").css({"height": $(document).height() - $("#head").height()});
            $("#left").css({"height": $(document).height() - $("#head").height()});
            $("#pageFrame").css({"width": $(document).width() - $("#left").width(), "height": $("#left").height() - 5});
        }

    </script>

    <style type="text/css">
        .head_title {
            text-align: center;
            font-weight: bold;
            font-size: 28px;
            background: #fff;
        }
        .layui-side{
            position: relative;
            z-index: 0;
            height:100%;
            width:220px;
        }
        .layui-nav-tree{
            width:100%;
        }
        .layui-nav-tree .layui-nav-item a{
            text-decoration: none;
        }
        .layui-nav-item a cite{
            margin:0 auto;
        }
        .layui-nav-tree .layui-nav-bar{
            display: none;
        }
        .layui-nav-itemed .layui-nav-child{
            border-left: 5px solid #009688;
            background: #293846;
        }
        .layui-bg-black,.layui-nav{
            background: #2F4050;
        }
        .layui-nav-itemed>a{
            /*background-color: #999;*/
        }
    </style>

</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse" style="height: 155px;">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="img-circle" src="img/default_headpic.png" style="width:64px;margin-left:30px"/></span>
                        <span class="text-muted text-xs block" style="padding-top:10px;margin-left:20px">当前用户： <%=account %></span>
                    </div>
                </li>
            </ul>
        </div>
        <div class="layui-side layui-bg-black x-side">
            <div class="layui-side-scroll">
                <ul class="layui-nav layui-nav-tree site-demo-nav" lay-filter="side" id="list">

                </ul>
            </div>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row content-tabs">
            <div class="head_title">金吉鸟商城管理系统</div>
            <a href="login.html" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe frameborder="0" width="100%" height="100%" src="index_v.html" class="x-iframe"></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; 2018 <a href="http://www.jinjiniao.com/" target="_blank">金吉鸟健身</a>
            </div>
        </div>
    </div>

</div>
</body>
<script src="layui/lib/layui/layui.js" charset="utf-8"></script>

<script>
    var zNodes = <%=menuJson%>;
    var $pId = [];
    for(var i = 0; i< zNodes.length; i++){
        if(zNodes[i].pId == 0){
            var $listLI = '<li class="layui-nav-item">' +
                    '<a class="javascript:;" href="javascript:;">' +
                    '<cite>'+ zNodes[i].name +'</cite>' +
                    ' </a> ' +
                    '<dl class="layui-nav-child" id="'+ zNodes[i].id +'">' +
                    ' </dl>' +
                    ' </li>';
            $('#list').append($listLI);
            $pId.push(zNodes[i].id);
        }
    }
    for(var j = 0; j< $pId.length; j++){
        for(var n = 0; n< zNodes.length; n++){
            if($pId[j] == zNodes[n].pId){
                var $listDD = '<dd class=""><a href="javascript:;" _href="'+ zNodes[n].path +'"><cite>'+ zNodes[n].name +'</cite></a></dd>';
                $("#" + $pId[j]).append($listDD);
            }
        }
    }
</script>
<script src="layui/js/x-admin.js"></script>
</html>