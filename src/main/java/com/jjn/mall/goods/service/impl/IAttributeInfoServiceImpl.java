package com.jjn.mall.goods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjn.mall.goods.dao.IAttributeInfoDao;
import com.jjn.mall.goods.dao.IAttributeValueDao;
import com.jjn.mall.goods.dao.pojo.TAttributeInfo;
import com.jjn.mall.goods.model.AttributeInfoDetailModel;
import com.jjn.mall.goods.service.IAttributeInfoService;

@Service(value = "attributeInfoService")
public class IAttributeInfoServiceImpl implements IAttributeInfoService {

	@Autowired
	private IAttributeInfoDao attributeInfoDao;

	@Autowired
	private IAttributeValueDao attributeValueDao;

	@Override
	public List<TAttributeInfo> getAllAttributeInfoById(int templetId) throws Exception {
		return attributeInfoDao.getAllAttributeInfoById(templetId);
	}

	@Override
	public List<AttributeInfoDetailModel> getAttributeInfoDetailById(AttributeInfoDetailModel attributeInfoDetailModel)
			throws Exception {
		List<AttributeInfoDetailModel> list = attributeInfoDao.getAttributeInfoDetailById(attributeInfoDetailModel);
		for (AttributeInfoDetailModel data : list) {
			data.setValueList(attributeValueDao.getAllAttributeValueById(data.getId()));
		}
		return list;
	}

}
