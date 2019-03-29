var setting = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false
    },
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    edit: {
        enable: true
    }
};

var zNodes = [
    { id: 1, pId: 0, name: "公司管理", open: true },
    { id: 101, pId: 1, name: "基础信息" },
    { id: 102, pId: 1, name: "会员协议" },
    // { id: 103, pId: 1, name: "不显示 连接线" },
    // { id: 104, pId: 1, name: "不显示 节点 图标" },
    // { id: 108, pId: 1, name: "异步加载 节点数据" },
    // { id: 109, pId: 1, name: "用 zTree 方法 异步加载 节点数据" },
    // { id: 110, pId: 1, name: "用 zTree 方法 更新 节点数据" },
    // { id: 111, pId: 1, name: "单击 节点 控制" },
    // { id: 112, pId: 1, name: "展开 / 折叠 父节点 控制" },
    // { id: 113, pId: 1, name: "根据 参数 查找 节点" },
    // { id: 114, pId: 1, name: "其他 鼠标 事件监听" },

    { id: 2, pId: 0, name: "员工部门", open: false },
    { id: 201, pId: 2, name: "员工管理部门" },
    { id: 202, pId: 2, name: "教练部门" },
    { id: 203, pId: 2, name: "角色管理" },
    { id: 204, pId: 2, name: "权限管理" },
    // { id: 205, pId: 2, name: "Checkbox 勾选统计" },
    // { id: 206, pId: 2, name: "用 zTree 方法 勾选 Checkbox" },
    // { id: 207, pId: 2, name: "Radio 勾选操作" },
    // { id: 208, pId: 2, name: "Radio nocheck 演示" },
    // { id: 209, pId: 2, name: "Radio chkDisabled 演示" },
    // { id: 210, pId: 2, name: "Radio halfCheck 演示" },
    // { id: 211, pId: 2, name: "用 zTree 方法 勾选 Radio" },

    { id: 3, pId: 0, name: "门店管理", open: false },
    { id: 301, pId: 3, name: "门店列表" },
    { id: 302, pId: 3, name: "门店信息" },
    { id: 303, pId: 3, name: "通知" },
    // { id: 304, pId: 3, name: "基本 增 / 删 / 改 节点" },
    // { id: 305, pId: 3, name: "高级 增 / 删 / 改 节点" },
    // { id: 306, pId: 3, name: "用 zTree 方法 增 / 删 / 改 节点" },
    // { id: 307, pId: 3, name: "异步加载 & 编辑功能 共存" },
    // { id: 308, pId: 3, name: "多棵树之间 的 数据交互" },

    { id: 4, pId: 0, name: "会员管理", open: false },
    { id: 401, pId: 4, name: "健身卡管理" },
    { id: 402, pId: 4, name: "用户信息" },
    { id: 403, pId: 4, name: "私教体验" },
    { id: 404, pId: 4, name: "会籍卡" },

    // { id: 5, pId: 0, name: "组合功能 演示", open: false },
    // { id: 501, pId: 5, name: "冻结根节点" },
    // { id: 502, pId: 5, name: "单击展开/折叠节点" },
    // { id: 503, pId: 5, name: "保持展开单一路径" },
    // { id: 504, pId: 5, name: "添加 自定义控件" },
    // { id: 505, pId: 5, name: "checkbox / radio 共存" },
    // { id: 506, pId: 5, name: "左侧菜单" },
    // { id: 507, pId: 5, name: "下拉菜单" },
    // { id: 509, pId: 5, name: "带 checkbox 的多选下拉菜单" },
    // { id: 510, pId: 5, name: "带 radio 的单选下拉菜单" },
    // { id: 508, pId: 5, name: "右键菜单 的 实现" },
    // { id: 511, pId: 5, name: "与其他 DOM 拖拽互动" },
    // { id: 512, pId: 5, name: "异步加载模式下全部展开" },

    // { id: 6, pId: 0, name: "其他扩展功能 演示", open: false },
    // { id: 601, pId: 6, name: "隐藏普通节点" },
    // { id: 602, pId: 6, name: "配合 checkbox 的隐藏" },
    // { id: 603, pId: 6, name: "配合 radio 的隐藏" }
];

$(document).ready(function() {
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
});

var newCount = 1;

function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId +
        "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_" + treeNode.tId);
    if (btn) btn.bind("click", function() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.addNodes(treeNode, { id: (100 + newCount), pId: treeNode.id, name: "new node" + (newCount++) });
        return false;
    });
};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.tId).unbind().remove();
};