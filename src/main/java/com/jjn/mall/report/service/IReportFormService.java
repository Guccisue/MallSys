package com.jjn.mall.report.service;

import com.jjn.mall.report.model.ReportFormInfoModel;

public interface IReportFormService {
	/**
	 * 分页查询报表信息
	 * @param ReportFormInfoModel
	 * @return
	 * @throws Exception
	 */
	public ReportFormInfoModel getSumRecordById(ReportFormInfoModel reportFormInfoModel) throws Exception;
}
