package com.jjn.mall.goods.service;

import java.util.List;

import com.jjn.mall.goods.dao.pojo.TGoodsAuditingInfo;
import com.jjn.mall.goods.dao.pojo.TGoodsInfo;
import com.jjn.mall.goods.dao.pojo.TStandardInfo;
import com.jjn.mall.goods.model.GoodsDetailModel;
import com.jjn.mall.goods.model.GoodsInfoModel;
import com.jjn.mall.goods.model.GoodsModel;

public interface IGoodsInfoService {

	/**
	 * 新增商品
	 * 
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int addGoods(GoodsInfoModel goodsInfoModel) throws Exception;

	/**
	 * 修改商品
	 * 
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int updateGoods(GoodsInfoModel goodsInfoModel) throws Exception;

	/**
	 * 检查商品是否绑定了某个模板
	 * 
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> checkGoodsIdByTemplateId(int templateId) throws Exception;

	/**
	 * 修改商品状态
	 * 
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int updateGoodsStatus(TGoodsInfo goodsInfo) throws Exception;

	/**
	 * 查询商品
	 * 
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public GoodsModel getAllGoods(GoodsModel goodsModel) throws Exception;

	/**
	 * 获取商品名称
	 * 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public String getGoodsName(int goodsId) throws Exception;

	/**
	 * 查看商品详情
	 * 
	 * @param goodsDetailModel
	 * @return
	 * @throws Exception
	 */
	public GoodsDetailModel getGoodsInfo(GoodsDetailModel goodsDetailModel) throws Exception;

	/**
	 * 查询结束时间是前一天的秒杀商品规格
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<TStandardInfo> getExpiredGoods() throws Exception;

	/**
	 * 根据商品Id查询审核记录
	 * 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<TGoodsAuditingInfo> getAllAuditingInfoByGoodsId(int goodsId) throws Exception;

	/**
	 * 根据商品id查询类目
	 * 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public int getCategoryIdByGoodsId(int goodsId) throws Exception;

	/**
	 * 查询商品是否在金吉豆商城中上架
	 * 
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public boolean goodsIsInBeanGoods(int goodsId) throws Exception;

}
