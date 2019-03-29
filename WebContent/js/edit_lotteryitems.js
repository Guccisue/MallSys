var httpUrl = 'http://192.168.10.13:8080';
	var Request = GetRequest();
	var goodsId = Request['goodsId'];
$(function() {

	$.ajax({
		url: "../../chance/getChanceGoods",
		data: {
			goodsId: goodsId
		},
		type: "POST",
		dataType: "JSON",
		success: function(result) {
			console.log(result);
			if(result.RESULTCODE == "0") {
				if(result.RESULTJSON == '') {
					popx('商品为空', 2)
				} else {
					$('#goodsId').val(result.RESULTJSON.goodsId);
					$('#goodsName').val(result.RESULTJSON.goodsName);
					$('#merchantName').val(result.RESULTJSON.merchantName);
					loadData(result.RESULTJSON.chanceGoodsList);
				}
			} else {
				popx(result.RESULTMSG, 2)
			}
		}

	});
})
function GetRequest() {
    var url = location.search; //获取url中含"?"符后的字串
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1);
        strs = str.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest;
}
//  商品规格点击添加
function loadData(data) {
	var addtr;
	for(var n = 0; n < data.length; n++) {
		addtr += '<tr class="tr_standard_class">' +
			'<td style="background-color:#F5F5F6;height: 55px;text-align: center;width:10%; border-top:none;"></td>' +
			"<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input disabled='disabled' value='" + data[n].attributeValues + "'   style='width: 50%; height: 30px;text-align: center;' type='text' class='standard' data-id='" + n + "'/></td>" +
			"<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;' disabled='disabled'  value='" + data[n].price + "'  type='number' id='price" + n + "' /></td>" +
			"<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 50%; height: 30px;text-align: center;'  value='" + data[n].numbers + "'  type='number' id='numbers" + n + "'/></td>" +
			"<td class='items_sizetable_td' style='width: 10%;text-align: center;'><input style='width:55%; height: 30px;text-align: center;'  value='" + data[n].goldNumber + "' type='number' id='goldNumber" + n + "' />	</td>" +
			"<td class='items_sizetable_td' style='width: 15%;text-align: center;'><input style='width: 25%; height: 30px;text-align: center;'autocomplete='off'  type='number' value='" + data[n].chance + "'  id='chance" + n + "' />	‱（万分之一）</td>" +
			"<td class='items_sizetable_td' style='width: 10%;text-align: center;'><input style='width:55%; height: 30px;text-align: center;display: none;'  value='" + data[n].id + "' type='text' id='id" + n + "' />	</td>" +
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
//删除规格
function deleteRow(obj) {
	index--;
	obj.parents(".tr_standard_class").remove();
}

function changePrice(priceStr) {
    return Math.round(priceStr * 100);
}

function saveItem() {
	
	var tstandardjson = [];
	for(var a = 0; a < $('.standard').length; a++) {
		var attributeValues = $('.standard').eq(a).val().replace(/\s*/g, "");
		var dataId = $('.standard').eq(a).attr('data-id');
		var price = $("#price" + dataId).val();
		var numbers = $("#numbers" + dataId).val();
		var goldNumber = $("#goldNumber" + dataId).val();
		var chance = $("#chance" + dataId).val();
		var id = $("#id" + dataId).val();
		var tstandard = {
			"goodsId": goodsId,
			"numbers":numbers,
			"id": id,
			"price": price,
			"goldNumber": goldNumber,
			"chance": chance,
		};
		tstandardjson.push(tstandard);
	}
	var reg = /^\+?[0-9]\d*$/;
	var reg2 = /^([1-9]\d*(\.\d*[1-9])?)|(0\.\d*[1-9])$/;
	for(var j = 0; j < tstandardjson.length; j++) {
	/*	if(tstandardjson[j].price == "") {
			popx("请填写商城价格", 2);
			return;
		}
		if(!reg2.test(tstandardjson[j].price)) {
			popx("商城价格填写非法", 2);
			return;
		}
		if(tstandardjson[j].stock == "") {
			popx("请填写库存", 2);
			return;
		}
		if(!reg.test(tstandardjson[j].stock)) {
			popx("库存只能为大于等于0的整数", 2);
			return;
		}*/
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
		url: "../../chance/updateChanceGoods",
		data: {
			listStr: JSON.stringify(tstandardjson),
		},
		type: "POST",
		dataType: "JSON",
		success: function(result) {
			console.log(result);
			if(result.RESULTCODE == "0") {
				popx("修改成功", 2);
				
				setTimeout(function(){
					goBack()
				},2000)
			} else {
				console.log(1);
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