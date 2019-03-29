package com.jjn.mall.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjn.mall.common.Constants;
import com.jjn.mall.order.common.OrderConstants;
import com.jjn.mall.order.dao.IOrderRecordDao;
import com.jjn.mall.order.dao.pojo.TOrderRecord;
import com.jjn.mall.order.dao.pojo.TRefundRecord;
import com.jjn.mall.order.model.OrderRecordModel;
import com.jjn.mall.order.service.IOrderRecordService;

@Service(value="orderRecordService")
public class OrderRecordServiceImpl implements IOrderRecordService {
	@Autowired
	private IOrderRecordDao orderRecordDao;
	
	@Override
	public OrderRecordModel getAllOrderRecord(OrderRecordModel orderRecordModel) throws Exception {
		List<TOrderRecord> orderRcordList = orderRecordDao.getAllOrderRecord(orderRecordModel);
		for(TOrderRecord orderRecord : orderRcordList) {
			orderRecord.setRefundTimes(0);
			List<TRefundRecord> refundRecordList = getRefundRecord(orderRecord);
			for(TRefundRecord refundRecord : refundRecordList) {
				if(refundRecord.getStatus() == OrderConstants.REFUND_STATUS_APPLY) {
					orderRecord.setRefundTimes(1);
					break;
				}
			}
		}
		orderRecordModel.setOrderRecordList(orderRcordList);
		orderRecordModel.setCount(orderRecordDao.getAllOrderRecordCount(orderRecordModel));
		return orderRecordModel;
	}

	@Override
	public List<TOrderRecord> getOrderRecord(TOrderRecord orderRecord) throws Exception {
		return orderRecordDao.getOrderRecord(orderRecord);
	}

	@Override
	public int updateOrderRecordDeliver(TOrderRecord orderRecord) throws Exception {
		return orderRecordDao.updateOrderRecordDeliver(orderRecord) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public int updateRefundRecord(TRefundRecord refundRecord) throws Exception {
		return orderRecordDao.updateRefundRecord(refundRecord) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public List<TRefundRecord> getRefundRecord(TOrderRecord orderRecord) throws Exception {
		return orderRecordDao.getRefundRecord(orderRecord);
	}

	@Override
	public int updateRefundRtatus(TOrderRecord orderRecord) throws Exception {
		TOrderRecord data = orderRecordDao.getRefundIdByOrderId(orderRecord);
		TRefundRecord  tRefundRecord =  new TRefundRecord();
		tRefundRecord.setRefundId(data.getRefundId());
		tRefundRecord.setAuditStatus(orderRecord.getAuditStatus());
		tRefundRecord.setStatus(orderRecord.getRefundStatus());
		tRefundRecord.setReason(orderRecord.getReason());
		tRefundRecord.setFee(orderRecord.getRealFee());
		return (orderRecordDao.updateRefundRtatus(orderRecord)> 0 && orderRecordDao.updateAuditStatus(tRefundRecord)> 0) ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}
	
	@Override
	public int updateRefundRtatusAfterSuccess(TOrderRecord orderRecord) throws Exception {
		TOrderRecord data = orderRecordDao.getRefundIdByOrderId(orderRecord);
		TRefundRecord  tRefundRecord =  new TRefundRecord();
		tRefundRecord.setRefundId(data.getRefundId());
		tRefundRecord.setStatus(orderRecord.getRefundStatus());
		tRefundRecord.setReason(orderRecord.getReason());
		tRefundRecord.setAuditStatus(orderRecord.getAuditStatus());
		return (orderRecordDao.updateRefundRtatus(orderRecord)> 0 && orderRecordDao.updateAuditStatus(tRefundRecord)> 0) ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}


	@Override
	public int updateAuditStatus(TRefundRecord refundRecord) throws Exception {
		return orderRecordDao.updateAuditStatus(refundRecord)> 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public TOrderRecord getRefundIdByOrderId(TOrderRecord tOrderRecord) throws Exception {
		return orderRecordDao.getRefundIdByOrderId(tOrderRecord);
	}

	@Override
	public OrderRecordModel getAllHaveFinished(OrderRecordModel orderRecordModel) throws Exception {
		orderRecordModel.setOrderRecordList(orderRecordDao.getAllHaveFinished(orderRecordModel));
		orderRecordModel.setCount(orderRecordDao.getAllHaveFinishedCount(orderRecordModel));
		return orderRecordModel;
	}

}
