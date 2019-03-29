package com.jjn.mall.merchant.dao;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TGoodsInfo;
import com.jjn.mall.merchant.dao.pojo.TCategoryInfo;
import com.jjn.mall.merchant.dao.pojo.TMerchantInfo;
import com.jjn.mall.merchant.model.CategoryModel;
import com.jjn.mall.merchant.model.MerchantModel;

/**
 * 商户相关DAO
 * @author 翟仁元
 *
 */
public interface IMerchantInfoDao {
	/*
	 * 商户管理部分======Start
	 */
	/**
	 * 新增商户
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public int addMerchant(TMerchantInfo merchantInfo) throws Exception;
	
	/**
	 * 校验商户名称是否存在
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public int selectMerchantByName(TMerchantInfo merchantInfo) throws Exception;
	
	/**
	 * 校验社会统一信用代码是否存在
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public int selectMerchantByUCC(TMerchantInfo merchantInfo) throws Exception;
	
	/**
	 * 校验QQ号是否存在
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public int selectMerchantByQQNumber(TMerchantInfo merchantInfo) throws Exception;
	
	/**
	 * 修改商户信息
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public int updateMerchant(TMerchantInfo merchantInfo) throws Exception;
	
	/**
	 * 得到所有商户列表
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public List<TMerchantInfo> getAllMerchant(MerchantModel merchantModel) throws Exception;
	
	/**
	 * 查询商户信息
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public List<TMerchantInfo> queryAllMerchant() throws Exception;

	/**
	 * 得到所有商户列表总数
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public int getAllMerchantCount(MerchantModel merchantModel) throws Exception;
	
	/**
	 * 得到商户信息
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public List<TMerchantInfo> getMerchant(TMerchantInfo merchantInfo) throws Exception;
	
	/**
	 * 注销商户
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteMerchant(TMerchantInfo merchantInfo) throws Exception;
	
	/**
	 * 检测商户下是否有商品
	 * @param merchantInfo
	 * @return
	 * @throws Exception
	 */
	public int checkGoodsMerchantBind(TGoodsInfo goodsInfo) throws Exception;
	
	/*
	 * 商户管理部分======End
	 */
	
	/*
	 * 类目管理部分======Start
	 */
	
	/**
	 * 新增类目
	 * @param categoryInfo
	 * @return
	 * @throws Exception
	 */
	public int addCategory(TCategoryInfo categoryInfo) throws Exception;
	
	/**
	 * 校验类目名称是否存在
	 * @param categoryInfo
	 * @return
	 * @throws Exception
	 */
	public int selectCategoryByName(TCategoryInfo categoryInfo) throws Exception;
	
	/**
	 * 修改类目
	 * @param categoryInfo
	 * @return
	 * @throws Exception
	 */
	public int updateCategory(TCategoryInfo categoryInfo) throws Exception;
	
	/**
	 * 查询类目
	 * @param categoryInfo
	 * @return
	 * @throws Exception
	 */
	public List<TCategoryInfo> getAllCategory(CategoryModel categoryModel) throws Exception;
	
	/**
	 * 查询所有二级类目
	 * @return
	 * @throws Exception
	 */
	public List<TCategoryInfo> getAllSecondCategory() throws Exception;
	
	/**
	 * 查询类目的总数
	 * @param categoryInfo
	 * @return
	 * @throws Exception
	 */
	public int getAllCategoryCount(CategoryModel categoryModel) throws Exception;
	
	/**
	 * 根据级别查询类目
	 * @param level
	 * @return
	 * @throws Exception
	 */
	public List<TCategoryInfo> getCategoryByPid(int parentId) throws Exception;
	
	/**
	 * 根据id查询类目
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public TCategoryInfo getCategoryByCategoryId(int categoryId) throws Exception;
	
	/**
	 * 查询类目树形
	 * @param categoryInfo
	 * @return
	 * @throws Exception
	 */
	public List<TCategoryInfo> getAllCategoryTree(TCategoryInfo categoryInfo) throws Exception;
	
	/**
	 * 删除类目
	 * @param categoryInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteCategory(TCategoryInfo categoryInfo) throws Exception;
	
	/**
	 * 校验类目下是否有商品
	 * @param categoryInfo
	 * @return
	 * @throws Exception
	 */
	public int checkGoodsCategoryBind(TCategoryInfo categoryInfo) throws Exception;
	/*
	 * 类目管理部分======End
	 */
}
