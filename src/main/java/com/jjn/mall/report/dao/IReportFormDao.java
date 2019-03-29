package com.jjn.mall.report.dao;

import java.util.List;

import com.jjn.mall.report.dao.pojo.TReportFormInfo;
import com.jjn.mall.report.model.ReportFormInfoModel;

public interface IReportFormDao {
	
	/**
	 *  根据商户id对已完成已过售后期订单进行求和
	 * @return
	 * @throws Exception
	 */
	public List<TReportFormInfo> getSumRecordById(ReportFormInfoModel reportFormInfoModel)throws Exception;
	
	/**
	 * 查询报表总数
	 * @param reportFormInfo
	 * @return
	 * @throws Exception
	 */
	public int getSumRecordCountById(ReportFormInfoModel reportFormInfoModel)throws Exception;
}
