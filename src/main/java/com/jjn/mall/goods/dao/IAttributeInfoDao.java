package com.jjn.mall.goods.dao;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TAttributeInfo;
import com.jjn.mall.goods.model.AttributeInfoDetailModel;

public interface IAttributeInfoDao {
	
	/**
	 * 根据模板id查询所有的属性
	 * @param attributeInfo
	 * @return
	 * @throws Exception
	 */
	public List<TAttributeInfo> getAllAttributeInfoById(int templetId) throws Exception;
	
	/**
	 * 根据模板id查询所有的属性详情
	 * @param attributeInfo
	 * @return
	 * @throws Exception
	 */
	public List<AttributeInfoDetailModel> getAttributeInfoDetailById(AttributeInfoDetailModel attributeInfoDetailModel) throws Exception;
	
	/**
	 * 新增属性
	 * @param attributeInfo
	 * @return
	 * @throws Exception
	 */
	public int addAttributeInfo(TAttributeInfo attributeInfo) throws Exception;
	
	/**
	 * 根据模板id删除属性
	 * @param attributeInfo
	 * @return
	 * @throws Exception
	 */
	public int deleteAttributeInfoByTempletId(int templetId) throws Exception;
	
	
}
