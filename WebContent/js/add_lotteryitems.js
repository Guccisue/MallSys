var httpUrl = 'http://192.168.10.13:8080';

//  商品规格点击添加
function loadData(data) {
	var addtr;
	for(var n = 0; n < data.length; n++) {
		addtr += '<tr class="tr_standard_class">' +
			'<td style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;"></td>' +
			"<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input disabled='disabled' value='" + data[n].attributeValues + "'   style='width: 50%; height: 30px;text-align: center;' type='text' class='standard' data-id='" + n + "'/></td>" +
			"<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;'  value='" + data[n].price + "'  type='number' id='price" + n + "' /></td>" +
			"<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;'  value='" + data[n].numbers + "'  type='number' id='numbers" + n + "'/></td>" +
			"<td class='items_sizetable_td' style='width: 10%;text-align: center;'><input style='width:55%; height: 30px;text-align: center;'  value='" + data[n].goldNumber + "' type='number' id='goldNumber" + n + "' />	</td>" +
			"<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 25%; height: 30px;text-align: center;'autocomplete='off'  type='number' id='chance" + n + "' />	‱（万分之一）</td>" +
			"<td class='items_sizetable_td' style='width: 10%;text-align: center;'><input style='width:55%; height: 30px;text-align: center;display: none;'  value='" + data[n].standardId + "' type='text' id='standardId" + n + "' />	</td>" +
			/*"<td class='items_sizetable_td' style='width: 15%;text-align: center;'>" +
			  "<div class='item itm_addimgs' style='height:80px;width:100%;left:0; margin-right: 0px; '>" +
			  "<img class='icon addImg addImg_xq' onclick='clickImg(this);' src='../../img/standard_default.png'/>" +
			  "<input name='standardpic" + n + "' id='standardpic" + n + "' type='file'class='upload_input' onchange='addchange(this)' />" +
			  "<input type='hidden' id='spic" + n + "' data-type='3'/>" +
			  "<div class='preBlock addImg_xq'>" +
			  "<img class='preview' alt='' name='pic' style='height: 60px;width: 60px;' data-type='3'/>" +
			  "</div>" +
			  "<img class='delete deleteSmall' onclick='deleteImg(this)' src='../../img/delete.png' />" +
			  "</div>" +
			  "</td>" +*/
			'</tr>';

	}
	$('#table_standard').append(addtr);
	/*<input class='btn btn-outline btn-default' type='button'value='删除' id='items_sizeBtn3' onclick=' deleteRow($(this));' style='background:red; color:white;border:none;' />*/
};
/*监听输入框*/
function inputOnchange(e) {
	$('#goodsName').val('');
	$('#merchantName').val('');
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
					loadData(result.RESULTJSON.standardInfoList);
				}
			} else {
				popx(result.RESULTMSG, 2)
			}
		}

	});
}
//删除规格
function deleteRow(obj) {
	index--;
	obj.parents(".tr_standard_class").remove();
}

function changePrice(priceStr) {
    return Math.round(priceStr * 100);
}

function addItem() {
	
	//商品规格
	var goodsId = $("#goodsId").val();
	if(goodsId==''){
		popx("请填写商城ID", 2);
			return;
	}else if($('#goodsName').val()==''){
		popx("商品为空", 2);
			return;
	}else if($('#merchantName').val()==''){
		popx("商品为空", 2);
			return;
	}
	var tstandardjson = [];
	for(var a = 0; a < $('.standard').length; a++) {
		var attributeValues = $('.standard').eq(a).val().replace(/\s*/g, "");
		var dataId = $('.standard').eq(a).attr('data-id');
		var price = $("#price" + dataId).val();
		var numbers = $("#numbers" + dataId).val();
		var goldNumber = $("#goldNumber" + dataId).val();
		var chance = $("#chance" + dataId).val();
		var standardId = $("#standardId" + dataId).val();
		var tstandard = {
			"goodsId": goodsId,
			"standardId": standardId,
			"price": price,
			"numbers": numbers,
			"goldNumber": goldNumber,
			"chance": chance,
		};
		tstandardjson.push(tstandard);
	}
	var reg = /^\+?[0-9]\d*$/;
	var reg2 = /^([1-9]\d*(\.\d*[1-9])?)|(0\.\d*[1-9])$/;
	for(var j = 0; j < tstandardjson.length; j++) {
		if(tstandardjson[j].price == "") {
			popx("请填写商城价格", 2);
			return;
		}
		if(!reg2.test(tstandardjson[j].price)) {
			popx("商城价格填写非法", 2);
			return;
		}
		if(tstandardjson[j].numbers == "") {
			popx("请填写库存", 2);
			return;
		}
		if(!reg.test(tstandardjson[j].numbers)) {
			popx("库存只能为大于等于0的整数", 2);
			return;
		}
		if(tstandardjson[j].goldNumber == "") {
			popx("请填写抽奖需要金币数量", 2);
			return;
		}
		if(!reg.test(tstandardjson[j].goldNumber)) {
			popx("金币数量只能为大于等于0的整数", 2);
			return;
		}
		if(tstandardjson[j].chance == "") {
			popx("请填写中奖概率", 2);
			return;
		}
		if(!reg.test(tstandardjson[j].chance)) {
			popx("中奖概率只能为大于等于0的整数", 2);
			return;
		}
		tstandardjson[j].price = changePrice(tstandardjson[j].price);
	}
	if( tstandardjson ==''){
		popx("商品规格为空", 2);
			return;
	}
	$.ajax({
		url: "../../chance/addChanceGoods",
		data: {
			listStr: JSON.stringify(tstandardjson),
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