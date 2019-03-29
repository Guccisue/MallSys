package com.jjn.mall.report.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjn.mall.order.dao.IOrderRecordDao;
import com.jjn.mall.order.model.OrderRecordModel;
import com.jjn.mall.report.dao.IReportFormDao;
import com.jjn.mall.report.dao.pojo.TReportFormInfo;
import com.jjn.mall.report.model.ReportFormInfoModel;
import com.jjn.mall.report.model.ReportModel;
import com.jjn.mall.report.service.IReportFormService;
@Service(value="reportFormService")
public class ReportFormServiceImpl implements IReportFormService {

	@Autowired
	private IReportFormDao iReportFormDao;
	
	@Autowired
	private IOrderRecordDao orderRecordDao;
	
	@Override
	public ReportFormInfoModel getSumRecordById(ReportFormInfoModel reportFormInfoModel) throws Exception {
		if(!StringUtils.isEmpty(reportFormInfoModel.getReportTime())) {
			reportFormInfoModel.setReportTime(reportFormInfoModel.getReportTime()+"-01");
		}else {
			reportFormInfoModel.setReportTime(null);
		}
		List<TReportFormInfo> list = iReportFormDao.getSumRecordById(reportFormInfoModel);
		OrderRecordModel orderRecordModel =new OrderRecordModel();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		for (TReportFormInfo tReportFormInfo : list) {
			orderRecordModel.setMerchantId(tReportFormInfo.getMerchantId());
			orderRecordModel.setQueryTime(sdf.parse(tReportFormInfo.getReportTime()));
			ReportModel reportModel = orderRecordDao.getSumCanBalance(orderRecordModel);
			if(null != reportModel) {
				tReportFormInfo.setCanBalanceBefore(reportModel.getSumCanBalanceBefore());
				tReportFormInfo.setCanBalanceAfter(reportModel.getSumCanBalanceAfter());
			}
		}
		for (TReportFormInfo tReportFormInfo : list) {
			tReportFormInfo.setReportTime(tReportFormInfo.getReportTime().substring(0, 7));
		}
		reportFormInfoModel.setReportFormInfoList(list);
		reportFormInfoModel.setCount(iReportFormDao.getSumRecordCountById(reportFormInfoModel));
		return reportFormInfoModel;
	}
}
