package com.jjn.mall.goods.service;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TTempletInfo;
import com.jjn.mall.goods.model.TempletDetailModel;
import com.jjn.mall.goods.model.TempletInfoModel;

public interface ITempletInfoService {

	/**
	 * 分页查询模板
	 * 
	 * @param templetInfoModel
	 * @return
	 * @throws Exception
	 */
	public TempletInfoModel getAllTempletInfo(TempletInfoModel templetInfoModel) throws Exception;
	
	/**
	 * 根据类目查询模板
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<TTempletInfo> getTempletInfoByCategoryId(int categoryId,int merchantId) throws Exception;

	/**
	 * 新增模板
	 * 
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public int addTempletInfo(TTempletInfo templetInfo) throws Exception;

	/**
	 * 修改模板
	 * 
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public int updateTempletInfo(TTempletInfo templetInfo) throws Exception;

	/**
	 * 删除模板
	 * 
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteTempletInfo(TTempletInfo templetInfo) throws Exception;

	/**
	 * 校验是否存在重复的模板名称
	 * 
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public int checkNameIsRepeat(TTempletInfo templetInfo) throws Exception;

	/**
	 * 查看模板详情
	 * 
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public TempletDetailModel getTempletInfo(TempletDetailModel templetDetailModel) throws Exception;
	
	/**
	 * 根据类目Id查询模板Id
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> checkTemplateByCategoryId(int categoryId) throws Exception;
}
