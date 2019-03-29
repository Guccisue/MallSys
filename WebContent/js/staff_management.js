// Tab切换
$(document).ready(function() {

    var $wrapper = $('.tab-wrapper'),
        $allTabs = $wrapper.find('.tab-content > div'),
        $tabMenu = $wrapper.find('.tab-menu li'),
        $line = $('<div class="line"></div>').appendTo($tabMenu);

    $allTabs.not(':first-of-type').hide();
    $tabMenu.filter(':first-of-type').find(':first').width('100%')

    $tabMenu.each(function(i) {
        $(this).attr('data-tab', 'tab' + i);
    });

    $allTabs.each(function(i) {
        $(this).attr('data-tab', 'tab' + i);
    });

    $tabMenu.on('click', function() {

        var dataTab = $(this).data('tab'),
            $getWrapper = $(this).closest($wrapper);

        $getWrapper.find($tabMenu).removeClass('active');
        $(this).addClass('active');

        $getWrapper.find('.line').width(0);
        $(this).find($line).animate({ 'width': '100%' }, 'fast');
        $getWrapper.find($allTabs).hide();
        $getWrapper.find($allTabs).filter('[data-tab=' + dataTab + ']').show();
    });

});
//  左右框选择值

//移到右边
$('#add').click(function() {
    //获取选中的选项，删除并追加给对方
    $('#select1 option:selected').appendTo('#select2');
});

//移到左边
$('#remove').click(function() {
    $('#select2 option:selected').appendTo('#select1');
});

//全部移到右边
$('#add_all').click(function() {
    //获取全部的选项,删除并追加给对方
    $('#select1 option').appendTo('#select2');
});

//全部移到左边
$('#remove_all').click(function() {
    $('#select2 option').appendTo('#select1');
});

//双击选项
$('#select1').dblclick(function() { //绑定双击事件
    //获取全部的选项,删除并追加给对方
    $("option:selected", this).appendTo('#select2'); //追加给对方
});

//双击选项
$('#select2').dblclick(function() {
    $("option:selected", this).appendTo('#select1');
});