package com.jjn.mall.order.dao;

import java.util.List;

import com.jjn.mall.order.dao.pojo.TOrderRecord;
import com.jjn.mall.order.dao.pojo.TRefundRecord;
import com.jjn.mall.order.model.OrderRecordModel;
import com.jjn.mall.report.model.ReportModel;

/**
 * 订单相关Dao
 * @author 翟仁元
 *
 */
public interface IOrderRecordDao {
	/**
	 * 分页查询订单信息
	 * @param orderRecordModel
	 * @return
	 * @throws Exception
	 */
	public List<TOrderRecord> getAllOrderRecord(OrderRecordModel orderRecordModel) throws Exception;
	
	/**
	 * 查询订单总数
	 * @param orderRecordModel
	 * @return
	 * @throws Exception
	 */
	public int getAllOrderRecordCount(OrderRecordModel orderRecordModel) throws Exception;
	
	/**
	 * 查询可结算总额
	 * @param orderRecordModel
	 * @return
	 */
	public ReportModel getSumCanBalance(OrderRecordModel orderRecordModel);
	
	/**
	 * 查看订单详情
	 * @param orderRecord
	 * @return
	 * @throws Exception
	 */
	public List<TOrderRecord> getOrderRecord(TOrderRecord orderRecord) throws Exception;
	
	/**
	 * 发货操作
	 * @param orderRecord
	 * @return
	 * @throws Exception
	 */
	public int updateOrderRecordDeliver(TOrderRecord orderRecord) throws Exception;
	
	/**
	 * 退款操作
	 * @param refundRecord
	 * @return
	 * @throws Exception
	 */
	public int updateRefundRecord(TRefundRecord refundRecord) throws Exception;
	
	/**
	 * 修改审核状态
	 * @param refundRecord
	 * @return
	 * @throws Exception
	 */
	public int updateAuditStatus(TRefundRecord refundRecord) throws Exception;
	
	/**
	 * 查询退款记录
	 * @param refundRecord
	 * @return
	 * @throws Exception
	 */
	public List<TRefundRecord> getRefundRecord(TOrderRecord orderRecord) throws Exception;
	
	/**
	 * 修改退款状态
	 * @param refundRecord
	 * @return
	 * @throws Exception
	 */
	public int updateRefundRtatus(TOrderRecord orderRecord) throws Exception;
	
	/**
	 * 获取退款单号
	 * @param tOrderRecord
	 * @return
	 * @throws Exception
	 */
	public TOrderRecord getRefundIdByOrderId(TOrderRecord tOrderRecord) throws Exception;
	
	/**
	 * 查询所有已完成已过售后期订单
	 * @return
	 * @throws Exception
	 */
	public List<TOrderRecord> getAllHaveFinished(OrderRecordModel orderRecordModel)throws Exception;
	
	/**
	 * 查询所有已完成已过售后期订单数
	 * @return
	 * @throws Exception
	 */
	public int getAllHaveFinishedCount(OrderRecordModel orderRecordModel)throws Exception;
	
}
