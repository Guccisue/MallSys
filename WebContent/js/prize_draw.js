// 添加奖品
$("#add_prize_time2StationIpt").click(function() {
        var BigTables = $(".prize_time2");
        var addTable = $(
            "<div class='flex_prize_time2'>" +
            "<div class='prize_time2Bor'>" +
            "<table>" +
            "<tr>" +
            "<th>奖品:</th>" +
            "<td class='prize_time2Ipt2Firtd'><input type='text' class='prize_time2Ipt1' placeholder='请输入奖品优惠券ID' style='text-align: center;'></td>" +
            "</tr>" +
            "<tr>" +
            "<th>奖品数量:</th>" +
            "<td class='prize_time2Ipt2Sectd'><input type='text' class='prize_time2Ipt2' style='text-align: center;'></td>" +
            "</tr>" +
            "<tr>" +
            "<th>中奖概率: </th>" +
            "<td class='prize_time2Ipt2Thitd'><span>每</span><input type='text' style='text-align: center;'></td>" +
            "<td> <span>人抽</span></td>" +
            "<td class='prize_time2Ipt2Fottd'><input type='text' style='text-align: center;'><span>份</span></td>" +
            "</tr>" +
            "</table>" +
            "</div>" +
            "<input type='button' value='删除' class='prize_time2Ipt2Delete' onclick=' deleteRows($(this));'>" +
            "</div>"

        )
        $(BigTables).append(addTable);
    })
    //删除
function deleteRows(obj) {
    obj.parents(".flex_prize_time2").remove();
}