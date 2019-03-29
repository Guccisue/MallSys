package com.jjn.mall.goods.dao;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TAttributeInfo;
import com.jjn.mall.goods.dao.pojo.TAttributeValue;

public interface IAttributeValueDao {
	
	/**
	 * 根据属性id查询属性值
	 * @param attributeValue
	 * @return
	 * @throws Exception
	 */
	public List<TAttributeValue> getAllAttributeValueById(int attributeId) throws Exception;
	
	/**
	 * 新增属性值
	 * @param attributeValue
	 * @return
	 * @throws Exception
	 */
	public int addAttributeValue(TAttributeValue attributeValue) throws Exception;
	
	/**
	 * 删除属性值
	 * @param attributeId
	 * @return
	 * @throws Exception
	 */
	public int deleteAttributeValueByAttributeId(List<TAttributeInfo> list) throws Exception;
	
}
