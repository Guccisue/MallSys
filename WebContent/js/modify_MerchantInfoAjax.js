window.onload = doInit();
// 	修改商户
function doInit() {
	var Request = new Object();
	Request = GetRequest();
	merchantId = Request['merchantId'];
	$.ajax({
		url: "../../merchant/getMerchant",
		data: { merchantId: merchantId },
		type: "POST",
		dataType: "JSON",
		success: function(result) {
		    if (result.RESULTCODE == "0") {
		    	action(result.RESULTJSON);
			} else {
			    popx(result.RESULTMSG,2);
			    }
			},
		error:function(result){
			popx("未进入正确方法",2);
		}
	})
    $('#shSave').click(function (){
    	var merchantId = $("#input_merchantid").val();
    	var merchantName = $("#input_merchantname").val();
    	var creditCode = $("#input_creditcode").val();
    	var contactName = $("#input_contactname").val();
    	var address = $("#input_address").val();
    	var phone = $("#input_phone").val();
    	var contactPhone = $("#input_contactphone").val();
    	var amQq = $('#shQq').val().replace(/(^\s*)|(\s*$)/g, '');
    	var myreg=/^[1][3,4,5,6,7,8,9][0-9]{9}$/;
    	if(merchantName == '' || merchantName == undefined || merchantName == null){
        	popx("请填写商户名称",2);
        } else if(creditCode == '' || creditCode == undefined || creditCode == null){
        	popx("请填写统一信用代码",2);
        } else if(address == '' || address == undefined || address == null){
        	popx("请填写地址",2);
        } else if(phone == '' || phone == undefined || phone == null){
        	popx("请填写公司电话",2);
        } else if(!/^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/.test(phone)){
        	popx("公司电话格式有误",2);
        } else if(contactPhone == '' || contactPhone == undefined || contactPhone == null){
        	popx("请填写手机号",2);
        } else if(!myreg.test(contactPhone)){
        	popx("手机号格式有误",2);
        } else if(amQq == '' || amQq == undefined || amQq == null){
            	popx("请填写QQ号码",2);
        }else{
        	// 验证数据ajax
	        $.ajax({
	            url:"../../merchant/updateMerchant",
	            data: { 
	            	merchantId: merchantId,
                    name: merchantName,
                    unifiedCreditCode: creditCode,
                    contacts: contactName,
                    address: address,
                    phone: phone,
                    contactsPhone: contactPhone,
                    qqNumber: amQq
	            },
	            type: "POST",
	            dataType: "JSON",
	            success: function(result) {
	                if (result.RESULTCODE == "0") {
                        popx("修改成功",2,function(){window.location.href = "Merchants_info.html"});
	                } else if(result.RESULTCODE == "-92005") {
                        popx('统一社会信用代码已存在',2);
                    }else if(result.RESULTCODE == "-92001") {
                        popx('商户名称已存在',2);
                    }
	            },
	            error:function(result){
	            	popx("修改失败",2);
	            }
	        })
        }
	});
}
function goBack(){
	history.go(-1)
}
function action(json) {
	var data = json;
	if(null == json || "" == json || "undefined" == json){
		return;
	}
    $("#input_merchantid").val(data.merchantId);
    $("#input_merchantname").val(data.name);
    $("#input_creditcode").val(data.unifiedCreditCode);
	$("#input_contactname").val(data.contacts);
	$("#input_address").val(data.address);
	$("#input_phone").val(data.phone);
	$("#input_contactphone").val(data.contactsPhone);
	$("#shQq").val(data.qqNumber);
}

function GetRequest() {
   var url = location.search; //获取url中含"?"符后的字串
   var theRequest = new Object();
   if (url.indexOf("?") != -1) {
      var str = url.substr(1);
      strs = str.split("&");
      for(var i = 0; i < strs.length; i ++) {
         theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
      }
   }
   return theRequest;
}
	//用户名校验
	//修改商户(data：name和id)
	function checkxna(){
		var na = $('#input_merchantname').val().trim();
		if(na != ""){
			$.ajax({
				type:"POST",
				url:"../../merchant/merchantNameCheck",
				data:{
					merchantId: $("#input_merchantid").val(),
					name:na
				},
				dataType:"JSON",
				success: function(result){
					if(result.RESULTCODE == "0"){
					}else{
						popx("该商户名称已存在",2);
					}
				},	
				error:
					function(result){
						popx(result.RESULTMSG,2);
					}
			})	
		}
	}
	
	//统一征信代码校验
	function checkCreditCode(){
		var creditCode = $('#input_creditcode').val().trim();
		if(creditCode != ""){
			$.ajax({
				type:"POST",
				url:"../../merchant/merchantUCCCheck",
				data:{
					merchantId: $("#input_merchantid").val(),
					unifiedCreditCode:creditCode
				},
				dataType:"JSON",
				success: function(result){
					if(result.RESULTCODE == "0"){
					}else{
						popx("该统一征信代码已存在",2);
					}
				},	
				error:
					function(result){
						popx(result.RESULTMSG,2);
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
					popx("该统一征信代码已存在",2);
				}
			},	
			error:
				function(result){
					popx(result.RESULTMSG,2);
				}
		})
	}
	
}
	