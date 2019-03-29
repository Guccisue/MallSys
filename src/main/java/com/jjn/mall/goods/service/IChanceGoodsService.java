package com.jjn.mall.goods.service;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TChanceGoods;
import com.jjn.mall.goods.model.ChanceGoodsInfoModel;
import com.jjn.mall.goods.model.ChanceGoodsListModel;
import com.jjn.mall.goods.model.ChanceGoodsModel;

public interface IChanceGoodsService {
	/**
	 * 分页查询抽奖商品
	 * @param chanceGoodsModel
	 * @return
	 * @throws Exception
	 */
	public ChanceGoodsListModel getAllChanceGoods(ChanceGoodsListModel chanceGoodsListModel) throws Exception;
	
	/**
	 * 新增抽奖商品
	 * @param chanceGoods
	 * @return
	 * @throws Exception
	 */
	public int addChanceGoods(List<TChanceGoods> list) throws Exception;
	
	/**
	 * 修改抽奖商品
	 * @param chanceGoods
	 * @return
	 * @throws Exception
	 */
	public int updateChanceGoods(List<TChanceGoods> list) throws Exception;
	
	/**
	 * 删除抽奖商品
	 * @param chanceGoods
	 * @return
	 * @throws Exception
	 */
	public int deleteChanceGoods(int id,int modifier) throws Exception;
	
	/**
	 * 查询抽奖商品是否已经配置
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public int checkIsExist(int goodsId) throws Exception;
	
	/**
	 * 根据规格ID判断数量是否超过库存数
	 * @param standardId
	 * @return
	 * @throws Exception
	 */
	public int checkNumsByStandardId(int standardId) throws Exception;
	
	/**
	 * 根据商品ID自动获取商品信息
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public ChanceGoodsInfoModel getGoodsDetail(int goodsId) throws Exception;
	
	/**
	 * 查看抽奖商品详情
	 * @param chanceGoods
	 * @return
	 * @throws Exception
	 */
	public ChanceGoodsModel getChanceGoods(int goodsId) throws Exception;
}
