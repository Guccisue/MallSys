package com.jjn.mall.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jjn.mall.goods.dao.pojo.ChanceGoodsList;
import com.jjn.mall.goods.dao.pojo.TChanceGoods;
import com.jjn.mall.goods.dao.pojo.TGoodsInfo;
import com.jjn.mall.goods.dao.pojo.TStandardInfo;
import com.jjn.mall.goods.model.ChanceGoodsListModel;

public interface IChanceGoodsDao {

	/**
	 * 分页查询抽奖商品
	 * @param chanceGoodsListModel
	 * @return
	 * @throws Exception
	 */
	public List<ChanceGoodsList> getAllChanceGoods(ChanceGoodsListModel chanceGoodsListModel) throws Exception;
	
	/**
	 * 查询抽奖商品总数
	 * @param chanceGoodsListModel
	 * @return
	 * @throws Exception
	 */
	public int getAllChanceGoodsCount(ChanceGoodsListModel chanceGoodsListModel) throws Exception;
	
	/**
	 * 新增抽奖商品
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int addChanceGoods(List<TChanceGoods> list) throws Exception;
	
	/**
	 * 修改抽奖商品
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int updateChanceGoods(@Param(value = "list")List<TChanceGoods> list) throws Exception;
	
	/**
	 * 删除抽奖商品
	 * @param chanceGoods
	 * @return
	 * @throws Exception
	 */
	public int deleteChanceGoods(@Param(value = "id")int id,@Param(value = "modifier")int modifier) throws Exception;
	
	/**
	 * 查询抽奖商品是否已经配置
	 * @param chanceGoods
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
	public TGoodsInfo getGoodsDetail(int goodsId) throws Exception;
	
	/**
	 * 查看抽奖商品详情
	 * @param chanceGoods
	 * @return
	 * @throws Exception
	 */
	public List<TChanceGoods> getChanceGoods(int goodsId) throws Exception;
	
	/**
	 * 查询规格
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<TStandardInfo> getGoodsStandard(int goodsId) throws Exception;
	
	/**
	 * 根据规格id查询抽奖参与人数
	 * @param standardId
	 * @return
	 * @throws Exception
	 */
	public int getSumChanceGoodsRecord(int standardId) throws Exception;
	
	/**
	 * 根据规格id查询中奖人数
	 * @param standardId
	 * @return
	 * @throws Exception
	 */
	public int getSumLuckyPeoples(int standardId) throws Exception;
}
