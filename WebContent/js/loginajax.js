$(function() {
    $('#login_btn').click(function() {
        // 得到用户名和密码
        var uid = $('#userName').val();
        var pwd = $('#passWord').val();
        // 验证数据ajax
        $.ajax({
            url: "user/loginUser",
            data: { account: uid, password : pwd },
            type: "POST",
            dataType: "JSON",
            success: function(result) {
                if (result.RESULTCODE == "0") {
                    window.location.href = "index.jsp";
                } else {
                    alert(result.RESULTMSG);
                }
            },
            error:function(result){
            	alert("未进入正确方法");
            }
        });
    });
});