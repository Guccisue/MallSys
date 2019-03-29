package com.jjn.mall.order.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jjn.mall.merchant.dao.pojo.TCategoryInfo;
import com.jjn.mall.merchant.model.CategoryModel;
import com.jjn.mall.order.dao.pojo.TPointDeductInfo;
import com.jjn.mall.order.model.PointDeductInfoModel;

public interface IPointDeductInfoDao {

	/**
	 * 分页查询扣点列表
	 * @param pointDeductInfoModel
	 * @return
	 * @throws Exception
	 */
	public List<TPointDeductInfo> getAllPointDeductInfo(PointDeductInfoModel pointDeductInfoModel) throws Exception;
	
	/**
	 * 查询扣点总数
	 * @param pointDeductInfoModel
	 * @return
	 * @throws Exception
	 */
	public int getAllPointDeductInfoCount(PointDeductInfoModel pointDeductInfoModel) throws Exception;
	
	/**
	 * 新增扣点
	 * @param pointDeductInfo
	 * @return
	 * @throws Exception
	 */
	public int addPointDeductInfo(TPointDeductInfo pointDeductInfo) throws Exception;
	
	/**
	 * 修改扣点
	 * @param pointDeductInfo
	 * @return
	 * @throws Exception
	 */
	public int updatePointDeductInfo(TPointDeductInfo pointDeductInfo) throws Exception;
	
	/**
	 * 删除扣点
	 * @param pointDeductInfo
	 * @return
	 * @throws Exception
	 */
	public int deletePointDeductInfo(TPointDeductInfo pointDeductInfo) throws Exception;
	
	/**
	 * 查看扣点详情
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TPointDeductInfo getPointDeductInfo(int id) throws Exception;
	
	/**
	 * 校验同一个商户下同一个类目或者同一个商品是否存在时间重叠
	 * @param pointDeductInfo
	 * @return
	 * @throws Exception
	 */
	public int checkTimeRepeat(TPointDeductInfo pointDeductInfo) throws Exception;
	
	/**
	 * 根据商户ID查询一级类目
	 * @param merchantId
	 * @return
	 * @throws Exception
	 */
	public List<CategoryModel> getAllParentIdByMerchantId(int merchantId) throws Exception;
	
	/**
	 * 根据商户ID和一级类目ID查询二级类目
	 * @param merchantId
	 * @return
	 * @throws Exception
	 */
	public List<TCategoryInfo> getAllCategoryIdByMerchantIdAndParentId(@Param(value="merchantId") int merchantId,@Param(value="parentId") int parentId) throws Exception;
}
