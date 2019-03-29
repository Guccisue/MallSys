package com.jjn.mall.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jjn.mall.goods.dao.pojo.TTempletInfo;
import com.jjn.mall.goods.model.TempletDetailModel;
import com.jjn.mall.goods.model.TempletInfoModel;

public interface ITempletInfoDao {

	/**
	 * 分页查询模板
	 * @param templetInfoModel
	 * @return
	 * @throws Exception
	 */
	public List<TTempletInfo> getAllTempletInfo(TempletInfoModel templetInfoModel) throws Exception;
	
	/**
	 * 根据类目查询模板
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<TTempletInfo> getTempletInfoByCategoryId(@Param(value="categoryId")int categoryId,@Param(value="merchantId")int merchantId) throws Exception;
	
	/**
	 * 查询模板总数
	 * @param templetInfoModel
	 * @return
	 * @throws Exception
	 */
	public int getAllTempletInfoCount(TempletInfoModel templetInfoModel) throws Exception;
	
	/**
	 * 新增模板
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public int addTempletInfo(TTempletInfo templetInfo) throws Exception;
	
	/**
	 * 修改模板
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public int updateTempletInfo(TTempletInfo templetInfo) throws Exception;
	
	/**
	 * 删除模板
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteTempletInfo(TTempletInfo templetInfo) throws Exception;
	
	/**
	 * 校验是否存在重复的模板名称
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public int checkNameIsRepeat(TTempletInfo templetInfo) throws Exception;
	
	/**
	 * 查看模板详情
	 * @param templetDetailModel
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
