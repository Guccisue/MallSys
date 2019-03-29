package com.jjn.mall.order.common;

public class OrderConstants {
	/** 退款记录状态: 申请中 */
	public static int REFUND_STATUS_APPLY = 0;
	/** 退款记录状态: 已同意 */
	public static int REFUND_STATUS_AGREE = 1;
	/** 退款记录状态: 已退款 */
	public static int REFUND_STATUS_COMP = 2;
	
	/** 订单状态: 全部 */
	public static int ORDER_STATUS_ALL = -1;
	/** 订单状态: 待支付 */
	public static int ORDER_STATUS_PAYING = 0;
	/** 订单状态: 已支付&待发货 */
	public static int ORDER_STATUS_PAYED = 1;
	/** 订单状态: 已发货 */
	public static int ORDER_STATUS_SENDING = 2;
	/** 订单状态: 申请退款 */
	public static int ORDER_STATUS_ASKREFUND = 3;
	/** 订单状态: 已退款 */
	public static int ORDER_STATUS_REFUNDED = 4;
	/** 订单状态: 已收货&已完成*/
	public static int ORDER_STATUS_RECEIVED = 5;
	/** 订单状态: 已取消 */
	public static int ORDER_STATUS_CANCEL = 6;
	/** 订单状态: 已删除*/
	public static int ORDER_STATUS_DELETE = 7;
	/** 订单状态: 退款中 */
	public static int ORDER_STATUS_REFUNDING = 8;
	/** 订单状态: 拒绝退款 */
	public static int ORDER_STATUS_REJUSEFUNDING = 9;	
	/** 订单状态: 待退货 */
	public static int ORDER_STATUS_RETURNING = 10;
	/** 订单状态: 已退货 */
	public static int ORDER_STATUS_RETURNED = 11;
}
