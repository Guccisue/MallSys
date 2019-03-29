package com.jjn.mall.order.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjn.mall.common.Constants;
import com.jjn.mall.merchant.model.CategoryModel;
import com.jjn.mall.order.dao.IPointDeductInfoDao;
import com.jjn.mall.order.dao.pojo.TPointDeductInfo;
import com.jjn.mall.order.model.PointDeductInfoModel;
import com.jjn.mall.order.service.IPointDeductInfoService;

@Service(value = "pointDeductInfoService")
public class PointDeductInfoServiceImpl implements IPointDeductInfoService {

	@Autowired
	private IPointDeductInfoDao pointDeductInfoDao;
	
	@Override
	public PointDeductInfoModel getAllPointDeductInfo(PointDeductInfoModel pointDeductInfoModel) throws Exception {
		pointDeductInfoModel.setCount(pointDeductInfoDao.getAllPointDeductInfoCount(pointDeductInfoModel));
		pointDeductInfoModel.setPointDeductInfoList(pointDeductInfoDao.getAllPointDeductInfo(pointDeductInfoModel));
		return pointDeductInfoModel;
	}

	@Override
	public int addPointDeductInfo(TPointDeductInfo pointDeductInfo) throws Exception {
		return pointDeductInfoDao.addPointDeductInfo(pointDeductInfo) > 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
	}

	@Override
	public int updatePointDeductInfo(TPointDeductInfo pointDeductInfo) throws Exception {
		return pointDeductInfoDao.updatePointDeductInfo(pointDeductInfo) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public int deletePointDeductInfo(TPointDeductInfo pointDeductInfo) throws Exception {
		return pointDeductInfoDao.deletePointDeductInfo(pointDeductInfo) > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}

	@Override
	public TPointDeductInfo getPointDeductInfo(int id) throws Exception {
		return pointDeductInfoDao.getPointDeductInfo(id);
	}

	@Override
	public int checkTimeRepeat(TPointDeductInfo pointDeductInfo) throws Exception {
		return pointDeductInfoDao.checkTimeRepeat(pointDeductInfo);
	}

	@Override
	public List<CategoryModel> getAllCategoryIdByMerchantId(int merchantId) throws Exception {
		List<CategoryModel> parentList = pointDeductInfoDao.getAllParentIdByMerchantId(merchantId);
		if(null != parentList && parentList.size() > 0) {
			for (CategoryModel categoryModel : parentList) {
				categoryModel.setCategoryInfoList(pointDeductInfoDao.getAllCategoryIdByMerchantIdAndParentId(merchantId,categoryModel.getParentId()));
			}
		}
		return parentList;
		
	}

}
