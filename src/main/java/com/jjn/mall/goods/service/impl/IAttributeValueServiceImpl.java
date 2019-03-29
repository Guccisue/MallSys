package com.jjn.mall.goods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjn.mall.goods.dao.IAttributeValueDao;
import com.jjn.mall.goods.dao.pojo.TAttributeValue;
import com.jjn.mall.goods.service.IAttributeValueService;

@Service(value = "attributeValueService")
public class IAttributeValueServiceImpl implements IAttributeValueService {

	@Autowired
	private IAttributeValueDao attributeValueDao;

	@Override
	public List<TAttributeValue> getAllAttributeValueById(int templetId) throws Exception {
		return attributeValueDao.getAllAttributeValueById(templetId);
	}

}
