package com.jjn.mall.goods.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jjn.mall.goods.dao.pojo.TGoodsAttribute;
import com.jjn.mall.goods.dao.pojo.TGoodsInfo;
import com.jjn.mall.goods.dao.pojo.TGoodsPic;
import com.jjn.mall.goods.dao.pojo.TGoodsAuditingInfo;
import com.jjn.mall.goods.dao.pojo.TStandardInfo;
import com.jjn.mall.goods.model.GoodsDetailModel;
import com.jjn.mall.goods.model.GoodsInfoModel;
import com.jjn.mall.goods.model.GoodsModel;

public interface IGoodsInfoDao {
	
	/**
	 * 分页查询商品
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public List<TGoodsInfo> getAllGoods(GoodsModel goodsModel) throws Exception;
	
	/**
	 * 查询商品总数
	 * @param goodsModel
	 * @return
	 * @throws Exception
	 */
	public int getAllGoodsCount(GoodsModel goodsModel) throws Exception;
	
	/**
	 * 检查商品是否绑定了某个模板
	 * @param templateId
	 * @return
	 * @throws Exception
	 */
	public List<Integer> checkGoodsIdByTemplateId(int templateId) throws Exception;
	
	/**
	 * 新增商品
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int addGoods(GoodsInfoModel goodsInfoModel) throws Exception;
	
	/**
	 * 修改商品
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int updateGoods(GoodsInfoModel goodsInfoModel) throws Exception;
	
	/**
	 * 修改商品状态
	 * @param goodsInfo
	 * @return
	 * @throws Exception
	 */
	public int updateGoodsStatus(TGoodsInfo goodsInfo) throws Exception;
	
	/**
	 * 查看商品详情
	 * @param goodsDetailModel
	 * @return
	 * @throws Exception
	 */
	public GoodsDetailModel getGoodsInfo(GoodsDetailModel goodsDetailModel) throws Exception;
	
	/**
	 * 获取商品名称
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public String getGoodsName(int goodsId) throws Exception;
	
	/**
	 * 获取商品状态
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public Integer getGoodsStatus(int goodsId) throws Exception;
	
	/**
	 * 新增审核记录
	 * @param goodsAuditingInfo
	 * @return
	 * @throws Exception
	 */
	public int addGoodsAuditingInfo(TGoodsAuditingInfo goodsAuditingInfo) throws Exception;
	
	/**
	 * 根据商品Id查询审核记录
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<TGoodsAuditingInfo> getAllAuditingInfoByGoodsId(int goodsId) throws Exception;
	
	/**
	 * 查询结束时间是前一天的秒杀商品规格
	 * @return
	 * @throws Exception
	 */
	public List<TStandardInfo> getExpiredGoods()throws Exception;
	
	
	/**
	 * 根据商品id查询商品图片
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<TGoodsPic> getAllGoodsPicByGoodsId(int goodsId) throws Exception;
	
	/**
	 * 新增商品图片
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int addGoodsPic(List<TGoodsPic> list) throws Exception; 
	
	/**
	 * 删除商品图片
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public int delGoodsPic(int goodsId) throws Exception; 
	
	/**
	 * 根据商品id查询所有的属性
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<TGoodsAttribute> getAllGoodsAttributeById(int goodsId) throws Exception;
	
	/**
	 * 获取商品属性名称
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<String> getAttributeNameListByGoodsId(int goodsId) throws Exception;
	
	/**
	 * 根据商品属性名称获取属性值
	 * @param goodsId
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public List<TGoodsAttribute> getAttributeValueList(@Param(value="goodsId")int goodsId,@Param(value="name")String name) throws Exception;
	
	/**
	 * 新增商品属性
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int addGoodsAttribute(List<TGoodsAttribute> list) throws Exception;
	
	/**
	 * 根据商品id删除属性
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public int deleteGoodsAttributeById(int goodsId) throws Exception;
	
	/**
	 * 根据商品id查询所有的规格
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public List<TStandardInfo> getAllStandardInfoById(int goodsId) throws Exception;
	
	
	/**
	 * 新增商品规格
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public int addStandardInfo(List<TStandardInfo> list) throws Exception;
	
	/**
	 * 根据商品id删除规格
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public int deleteStandardInfoById(int goodsId) throws Exception;
	
	/**
	 * 根据商品id查询类目
	 * @param goodsId
	 * @return
	 * @throws Exception
	 */
	public int getCategoryIdByGoodsId(int goodsId) throws Exception;
	
}
