$(function() {
    $('#add_MerchantsBtn').click(
        function() {
            var amName = $('#shName').val().replace(/(^\s*)|(\s*$)/g, '');
            var amTrust = $('#trust').val().replace(/(^\s*)|(\s*$)/g, '');
            var mName = $('#MerChantName').val().replace(/(^\s*)|(\s*$)/g, '');
            var amAddress = $('#shAddress').val().replace(/(^\s*)|(\s*$)/g, '');
            var amTel = $('#shTel').val().replace(/(^\s*)|(\s*$)/g, '');
            var amPhone = $('#shPhone').val().replace(/(^\s*)|(\s*$)/g, '');
            var amQq = $('#shQq').val().replace(/(^\s*)|(\s*$)/g, '');
            var myreg=/^[1][3,4,5,6,7,8,9][0-9]{9}$/;
            if(amName == '' || amName == undefined || amName == null){
            	popx("请填写商户名称",2);
            }else if(amTrust == '' || amTrust == undefined || amTrust == null){
            	popx("请填写统一信用代码",2);
            }else if(mName == '' || mName == undefined || mName == null){
            	popx("请填写业务联系人",2);
            }else if(amAddress == '' || amAddress == undefined || amAddress == null){
            	popx("请填写地址",2);
            }else if(amTel == '' || amTel == undefined || amTel == null){
            	popx("请填写公司电话",2);
            }else if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(amTel)){
            	popx("公司电话格式有误",2);
            }else if(amPhone == '' || amPhone == undefined || amPhone == null){
            	popx("请填写手机号",2);
            }else if(!myreg.test(amPhone)){
            	popx("手机号格式有误",2);
            }else if(amQq == '' || amQq == undefined || amQq == null){
            	popx("请填写QQ号码",2);
            }else{
                $.ajax({
                    url:"../../merchant/addMerchant",
                    //url:"http://192.168.10.13:8080/MallSys/merchant/addMerchant",
                    data: {
                        name: amName,
                        unifiedCreditCode: amTrust,
                        contacts: mName,
                        address: amAddress,
                        phone: amTel,
                        contactsPhone: amPhone,
                        qqNumber: amQq
                    },
                    type: "POST",
                    dataType: "JSON",
                    success: function(result) {
                        if (result.RESULTCODE == "0") {
                        	popx("添加成功",2,function(){
                                window.location.href = "Merchants_info.html";
                            });
                        } else if(result.RESULTCODE == "-92005") {
                            popx('统一社会信用代码已存在',2);
                        }else if(result.RESULTCODE == "-92001") {
                            popx('商户名称已存在',2);
                        }
                    }
                    
                })
            }
            
        }
    )
})
// 渲染回调
function goBack(){
	history.go(-1)
}
function action(data) {
    var trList = "";
    var list = data.merchantInfoList;
    for (i in list) {
        trList += "<tr>" +
            "<td>" + list[i].merchantid + "</td>" +
            "<td>" + list[i].name + "</td>" +
            "<td>" + list[i].unifiedcreditcode + "</td>" +
            "<td class=''>" + list[i].address + "</td>" +
            "<td>" + list[i].phone + "</td>" +
            "<td>" + list[i].contacts + "</td>" +
            "<td>" + list[i].contactsphone + "</td>" +
            "<td align='center'>" +
            "<div>" +
            "<a href='modify_Merchants.html' title='修改'>" +
            "<i class='glyphicon glyphicon-pencil' aria-hidden='true'>" +
            "</i>" +
            "</a>" +
            "<a onclick='isDel();' title='删除' style='color:#CC0000'>" +
            "<i class='glyphicon glyphicon-remove' aria-hidden='true'>" +
            "</i>" +
            "</a>" +
            "</div>" +
            "</td>" +
            "</tr>";
    }
    $("#merchantsList").find('tr').eq(0).before(trList);
}

//用户名校验
function checkMerchantName(){
	var na = $('#shName').val().trim();
	if(na != ""){
		$.ajax({
			type:"POST",
			url:"../../merchant/merchantNameCheck",
			data:{
				name:na
			},
			dataType:"JSON",
			success: function(result){
				if(result.RESULTCODE == "0"){
				}else{
					popx("该商户名称已存在");
				}
			},	
			error:
				function(result){
					popx(result);
				}
		})	
	}
}
//QQ校验
function QQNumberCheck(){
	var qqNumber = $('#shQq').val().trim();
	if(qqNumber != ""){
		$.ajax({
			type:"POST",
			url:"../../merchant/QQNumberCheck",
			data:{
				qqNumber:qqNumber
			},
			dataType:"JSON",
			success: function(result){
				if(result.RESULTCODE == "0"){
				}else{
					popx("该统一征信代码已存在");
				}
			},	
			error:
				function(result){
					popx(result);
				}
		})
	}
	
}
//统一征信代码校验
function checkCreditCode(){
	var creditCode = $('#trust').val().trim();
	if(creditCode != ""){
		$.ajax({
			type:"POST",
			url:"../../merchant/merchantUCCCheck",
			data:{
				unifiedCreditCode:creditCode
			},
			dataType:"JSON",
			success: function(result){
				if(result.RESULTCODE == "0"){
				}else{
					popx("该统一征信代码已存在");
				}
			},	
			error:
				function(result){
					popx(result);
				}
		})
	}
	
}
