package com.jjn.mall.goods.service;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TAttributeValue;

public interface IAttributeValueService {

	/**
	 * 根据模板id查询所有的属性值
	 * 
	 * @param attributeValue
	 * @return
	 * @throws Exception
	 */
	public List<TAttributeValue> getAllAttributeValueById(int templetId) throws Exception;

}
