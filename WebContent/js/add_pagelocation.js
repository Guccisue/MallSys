var httpUrl = 'http://192.168.10.13:8080';

/*监听输入框*/
function inputOnchange(e) {
	$('#goodsName').val('');
	$('#merchantName').val('');
	 $("#type ").val(0);
	$.ajax({
		url: "../../chance/getGoodsDetail",
		data: {
			goodsId: e
		},
		type: "POST",
		dataType: "JSON",
		success: function(result) {
			$('.tr_standard_class').remove();
			if(result.RESULTCODE == "0") {
				if(result.RESULTJSON == '') {
					popx('商品为空', 2)
				} else {
					$('#goodsName').val(result.RESULTJSON.goodsName);
					$('#merchantName').val(result.RESULTJSON.merchantName);
				}
			} else {
				popx(result.RESULTMSG, 2)
			}
		}

	});
}


function addItem() {
	//商品规格
	var goodsId = $("#goodsId").val();
	var type = $("#type").val();
	if(goodsId==''){
		popx("请填写商城ID", 2);
			return;
	}else if($('#goodsName').val()==''){
		popx("商品为空", 2);
			return;
	}else if($('#merchantName').val()==''){
		popx("商品为空", 2);
			return;
	}else if(type==''||type==null){
		popx("类型为空", 2);
			return;
	}
	$.ajax({
		url: "../../beanGoods/addBeanGoodsInfo",
		data: {
			goodsId:goodsId,
			type:type
		},
		type: "POST",
		dataType: "JSON",
		success: function(result) {
			console.log(result);
			if(result.RESULTCODE == "0") {
				popx("添加成功", 2, function() {
					goBack()
				});
			} else {
				popx(result.RESULTMSG, 2)

			}
		},
		error:function(result){
				popx(result.RESULTMSG, 2)
		}
	})
}

function goBack(){
    history.go(-1);
}