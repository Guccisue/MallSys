package com.jjn.mall.goods.service;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TBeanGoods;
import com.jjn.mall.goods.model.BeanGoodsModel;
import com.jjn.mall.goods.model.ResultJsonModel;

public interface IBeanGoodsInfoService {
	/**
	 * 分页查询金豆商城首页商品
	 * 
	 * @param goodsInfoModel
	 * @return
	 * @throws Exception
	 */
	public BeanGoodsModel getAllBeanGoodsInfo(BeanGoodsModel beanGoodsModel) throws Exception;

	/**
	 * 新增金豆商城首页商品
	 * 
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int addBeanGoodsInfo(int goodsId, int type, int creater, int sequence) throws Exception;

	/**
	 * 修改金豆商城首页商品
	 * 
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int updateBeanGoodsInfo(int goodsId, int type, int creater, int sequence) throws Exception;

	/**
	 * 删除金豆商城首页商品
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int deleteBeanGoodsInfo(int goodsId, int modifier, int type) throws Exception;

	/**
	 * 校验是否存在重复的金豆商城首页商品
	 * 
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	public int checkBeanGoodsIsRepeat(int goodsId, int type, int sequence) throws Exception;

	/**
	 * 查询单个金豆商城首页商品
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public TBeanGoods getBeanGoodsInfo(int goodsId, int type) throws Exception;

	/**
	 * 新增商品-校验是否存在重复的金豆商城首页商品
	 * 
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	ResultJsonModel checkBeanGoodsIsRepeatAdd(int goodsId, int type, int sequence) throws Exception;
	
	/**
	 * 修改商品-校验是否存在重复的金豆商城首页商品
	 * 
	 * @param templetInfo
	 * @return
	 * @throws Exception
	 */
	ResultJsonModel checkBeanGoodsIsRepeatUpdate(int goodsId, int type, int sequence, int orlType) throws Exception;
}
