package com.jjn.mall.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jjn.mall.goods.dao.pojo.TBeanGoods;
import com.jjn.mall.goods.model.BeanGoodsModel;
import com.jjn.mall.goods.model.GoodsModel;

public interface IBeanGoodsInfoDao {

	/**
	 * 分页查询金豆商城首页商品
	 * 
	 * @param goodsModel
	 * @return
	 * @throws Exception
	 */
	public List<TBeanGoods> getAllBeanGoodsInfo(BeanGoodsModel beanGoodsModel) throws Exception;

	/**
	 * 查询金豆商城首页商品总数
	 * 
	 * @param goodsModel
	 * @return
	 * @throws Exception
	 */
	public int getAllBeanGoodsInfoCount(BeanGoodsModel beanGoodsModel) throws Exception;

	/**
	 * 新增金豆商城首页商品
	 * 
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int addBeanGoodsInfo(@Param(value = "goodsId") int goodsId, @Param(value = "type") int type,
			@Param(value = "creater") int creater, @Param(value = "sequence") int sequence) throws Exception;

	/**
	 * 修改金豆商城首页商品
	 * 
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int updateBeanGoodsInfo(@Param(value = "goodsId") int goodsId, @Param(value = "type") int type,
			@Param(value = "creater") int creater, @Param(value = "sequence") int sequence) throws Exception;

	
	/**
	 * 删除金豆商城首页商品
	 * 
	 * @param id
	 * @returns
	 * @throws Exception
	 */
	public int deleteBeanGoodsInfo(@Param(value = "goodsId") int goodsId, @Param(value = "modifier") int modifier,
			@Param(value = "type") int type)
			throws Exception;

	/**
	 * 校验是否存在重复的金豆商城首页商品
	 * 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public int checkBeanGoodsIsRepeat(@Param(value = "goodsid") Integer goodsId, @Param(value = "type") Integer type,
			@Param(value = "sequence") Integer sequence) throws Exception;
	
	/**
	 * 校验是否存在重复的金豆商城首页商品-修改商品
	 * 修改时将自身商品排除
	 * 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public int checkBeanGoodsIsRepeatUpdate(@Param(value = "goodsid") Integer goodsId, @Param(value = "type") Integer type,
			@Param(value = "sequence") Integer sequence) throws Exception;
	
	
	/**
	 * 查询单个金豆商城首页商品
	 * 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public TBeanGoods getBeanGoodsInfo(@Param(value = "goodsId") int goodsId, @Param(value = "type") Integer type) throws Exception;
	
	
	/**
	 * 查询单个金豆商城首页商品
	 * 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<TBeanGoods> getBeanGoodsListInfo(@Param(value = "goodsId") int goodsId, @Param(value = "type") Integer type) throws Exception;
	
}
