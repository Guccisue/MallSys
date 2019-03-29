package com.jjn.mall.goods.common;

public class GoodsConstants {

	
	/** 商品状态: 待上架 */
	public static int GOODS_STATUS_WAITING = 0;
	/** 商品状态: 已上架 */
	public static int GOODS_STATUS_NORMAL = 1;
	/** 商品状态: 已下架 */
	public static int GOODS_STATUS_WITHDRAW = 2;
	/** 商品状态: 已删除 */
	public static int GOODS_STATUS_DELETED = 3;
	/** 商品状态: 审核中 */
	public static int GOODS_STATUS_AUTHING = 4;
	/** 商品状态: 已驳回 */
	public static int GOODS_STATUS_REJECT = 5;
	
	/**用于判断商品修改时某个模块是否修改 0:未修改 1:已修改 */
	public static int GOODS_NO_UPDATE = 0;
	public static int GOODS_IS_UPDATE = 1;
}
