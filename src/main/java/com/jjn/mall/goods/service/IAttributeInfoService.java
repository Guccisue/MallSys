package com.jjn.mall.goods.service;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TAttributeInfo;
import com.jjn.mall.goods.model.AttributeInfoDetailModel;

public interface IAttributeInfoService {

	/**
	 * 根据模板id查询所有的属性
	 * 
	 * @param attributeInfo
	 * @return
	 * @throws Exception
	 */
	public List<TAttributeInfo> getAllAttributeInfoById(int templetId) throws Exception;

	/**
	 * 根据模板id查询所有的属性详情
	 * 
	 * @param attributeInfo
	 * @return
	 * @throws Exception
	 */
	public List<AttributeInfoDetailModel> getAttributeInfoDetailById(AttributeInfoDetailModel attributeInfoDetailModel)
			throws Exception;
}
