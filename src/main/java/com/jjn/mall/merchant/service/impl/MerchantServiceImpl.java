package com.jjn.mall.merchant.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjn.mall.common.Constants;
import com.jjn.mall.goods.dao.pojo.TGoodsInfo;
import com.jjn.mall.merchant.dao.IMerchantInfoDao;
import com.jjn.mall.merchant.dao.pojo.TCategoryInfo;
import com.jjn.mall.merchant.dao.pojo.TMerchantInfo;
import com.jjn.mall.merchant.model.CategoryModel;
import com.jjn.mall.merchant.model.MerchantModel;
import com.jjn.mall.merchant.service.IMerchantService;

/**
 * 
 * @author 翟仁元
 *
 */
@Service(value="merchantService")
public class MerchantServiceImpl implements IMerchantService {
	@Autowired
	private IMerchantInfoDao merchantInfoDao;
	/*
	 * 商户管理部分======Start
	 */
	@Override
	public int addMerchant(TMerchantInfo merchantInfo) throws Exception {
		if(selectMerchantByName(merchantInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		if(selectMerchantByUCC(merchantInfo) > 0) {
			return Constants.ERROR_CODE_92005;
		}
		return merchantInfoDao.addMerchant(merchantInfo) > 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
	}

	@Override
	public int selectMerchantByName(TMerchantInfo merchantInfo) throws Exception {
		return merchantInfoDao.selectMerchantByName(merchantInfo) > 0 ? Constants.SELECT_FAIL : Constants.SELECT_SUCCESS;
	}

	@Override
	public int selectMerchantByUCC(TMerchantInfo merchantInfo) throws Exception {
		return merchantInfoDao.selectMerchantByUCC(merchantInfo) > 0 ? Constants.SELECT_FAIL : Constants.SELECT_SUCCESS;
	}

	@Override
	public int updateMerchant(TMerchantInfo merchantInfo) throws Exception {
		if(selectMerchantByName(merchantInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		if(selectMerchantByUCC(merchantInfo) > 0) {
			return Constants.ERROR_CODE_92005;
		}
		return merchantInfoDao.updateMerchant(merchantInfo) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public MerchantModel getAllMerchant(MerchantModel merchantModel) throws Exception {
		merchantModel.setMerchantInfoList(merchantInfoDao.getAllMerchant(merchantModel));
		merchantModel.setCount(merchantInfoDao.getAllMerchantCount(merchantModel));
		return merchantModel;
	}

	@Override
	public List<TMerchantInfo> getMerchant(TMerchantInfo merchantInfo) throws Exception {
		return merchantInfoDao.getMerchant(merchantInfo);
	}

	@Transactional
	@Override
	public int deleteMerchant(TMerchantInfo merchantInfo) throws Exception {
		
		return merchantInfoDao.deleteMerchant(merchantInfo) > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}

	@Override
	public int checkGoodsMerchantBind(TGoodsInfo goodsInfo) throws Exception {
		return merchantInfoDao.checkGoodsMerchantBind(goodsInfo);
	}
	
	/*
	 * 商户管理部分======End
	 */

	/*
	 * 类目管理部分======Start
	 */
	@Override
	public int addCategory(TCategoryInfo categoryInfo) throws Exception {
		if(selectCategoryByName(categoryInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return merchantInfoDao.addCategory(categoryInfo) > 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
	}

	@Override
	public int selectCategoryByName(TCategoryInfo categoryInfo) throws Exception {
		return merchantInfoDao.selectCategoryByName(categoryInfo) > 0 ? Constants.SELECT_FAIL : Constants.SELECT_SUCCESS;
	}

	@Override
	public int updateCategory(TCategoryInfo categoryInfo) throws Exception {
		if(selectCategoryByName(categoryInfo) > 0) {
			return Constants.ERROR_CODE_92001;
		}
		return merchantInfoDao.updateCategory(categoryInfo) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public CategoryModel getAllCategory(CategoryModel categoryModel) throws Exception {
		categoryModel.setCategoryInfoList(merchantInfoDao.getAllCategory(categoryModel));
		categoryModel.setCount(merchantInfoDao.getAllCategoryCount(categoryModel));
		return categoryModel;
	}
	
	@Override
	public List<TCategoryInfo> getCategoryByPid(int parentid) throws Exception {
		return merchantInfoDao.getCategoryByPid(parentid);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List getAllCategoryTree(List<TCategoryInfo> categoryInfoList) throws Exception {
		List<Map> listMap = new ArrayList();
		for(TCategoryInfo categoryInfo : categoryInfoList) {
			List<TCategoryInfo> tciList = merchantInfoDao.getAllCategoryTree(categoryInfo);
			Map tempMap = new HashMap();
			tempMap.put("categoryid", categoryInfo.getCategoryId());
			tempMap.put("parentid", categoryInfo.getParentId());
			tempMap.put("level", categoryInfo.getLevel());
			tempMap.put("name", categoryInfo.getName());
			if(null != tciList && tciList.size() > 0) {
				tempMap.put("child", getAllCategoryTree(tciList));
			}
			listMap.add(tempMap);
		}
		return listMap;
	}

	@Override
	public int deleteCategory(TCategoryInfo categoryInfo) throws Exception {
		if(checkGoodsCategoryBind(categoryInfo) > 0) {
			return Constants.ERROR_CODE_92002;
		}
		List<TCategoryInfo> categoryInfoList = merchantInfoDao.getAllCategoryTree(categoryInfo);
		if(null != categoryInfoList && categoryInfoList.size() > 0) {
			return Constants.ERROR_CODE_92008;
		}
		return merchantInfoDao.deleteCategory(categoryInfo) > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}

	@Override
	public int checkGoodsCategoryBind(TCategoryInfo categoryInfo) throws Exception {
		return merchantInfoDao.checkGoodsCategoryBind(categoryInfo);
	}
	
	@Override
	public TCategoryInfo getCategoryByCategoryId(int categoryId) throws Exception {
		return merchantInfoDao.getCategoryByCategoryId(categoryId);
	}
	/*
	 * 类目管理部分======End
	 */

	@Override
	public List<TMerchantInfo> queryAllMerchant() throws Exception {
		return merchantInfoDao.queryAllMerchant();
	}

	@Override
	public int selectMerchantByQQNumber(TMerchantInfo merchantInfo) throws Exception {
		return merchantInfoDao.selectMerchantByQQNumber(merchantInfo) ;
	}

	@Override
	public List<TCategoryInfo> getAllSecondCategory() throws Exception {
		return merchantInfoDao.getAllSecondCategory();
	}

}
