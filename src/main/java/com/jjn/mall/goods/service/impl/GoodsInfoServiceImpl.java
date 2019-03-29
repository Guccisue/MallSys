package com.jjn.mall.goods.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.jjn.mall.common.Config;
import com.jjn.mall.common.Constants;
import com.jjn.mall.goods.common.GoodsConstants;
import com.jjn.mall.goods.dao.IBeanGoodsInfoDao;
import com.jjn.mall.goods.dao.IGoodsInfoDao;
import com.jjn.mall.goods.dao.pojo.TBeanGoods;
import com.jjn.mall.goods.dao.pojo.TGoodsAttribute;
import com.jjn.mall.goods.dao.pojo.TGoodsInfo;
import com.jjn.mall.goods.dao.pojo.TGoodsPic;
import com.jjn.mall.goods.dao.pojo.TGoodsAuditingInfo;
import com.jjn.mall.goods.dao.pojo.TStandardInfo;
import com.jjn.mall.goods.model.GoodsAttributeModel;
import com.jjn.mall.goods.model.GoodsDetailModel;
import com.jjn.mall.goods.model.GoodsInfoModel;
import com.jjn.mall.goods.model.GoodsModel;
import com.jjn.mall.goods.service.IGoodsInfoService;
import com.jjn.mall.merchant.dao.IMerchantInfoDao;
import com.jjn.mall.merchant.dao.pojo.TCategoryInfo;

@Service(value = "goodsInfoService")
public class GoodsInfoServiceImpl implements IGoodsInfoService {

	@Autowired
	private IGoodsInfoDao goodsInfoDao;

	@Autowired
	private IMerchantInfoDao merchantInfoDao;

	@Autowired
	private IBeanGoodsInfoDao beanGoodsInfoDao;

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Override
	@Transactional
	public int addGoods(GoodsInfoModel goodsInfoModel) throws Exception {

		// 保证最高抵扣金币数小于价格
		List<TStandardInfo> standardList = JSONArray.parseArray(goodsInfoModel.getStandardJson(), TStandardInfo.class);
		for (TStandardInfo tStandardInfo : standardList) {
			if (tStandardInfo.getGoldNumber() * 100 > tStandardInfo.getPrice()) {
				return Constants.ERROR_CODE_92016;
			}
		}
		// 保证商品属性中不存在/符号
		List<TGoodsAttribute> attributeList = JSONArray.parseArray(goodsInfoModel.getAttributeJson(),
				TGoodsAttribute.class);
		for (TGoodsAttribute tGoodsAttribute : attributeList) {
			if (tGoodsAttribute.getAttributeValue().contains("/")) {
				return Constants.ERROR_CODE_92017;
			}
		}

		int addGoodResult = goodsInfoDao.addGoods(goodsInfoModel);
		// 添加图片
		List<TGoodsPic> picList = JSONArray.parseArray(goodsInfoModel.getPicJson(), TGoodsPic.class);
		for (TGoodsPic tGoodsPic : picList) {
			tGoodsPic.setGoodsId(goodsInfoModel.getGoodsId());
			tGoodsPic.setCreater(goodsInfoModel.getCreater());
		}
		int addPicResult = goodsInfoDao.addGoodsPic(picList);
		// 添加属性
		for (TGoodsAttribute tGoodsAttribute : attributeList) {
			tGoodsAttribute.setAttributeValue(tGoodsAttribute.getAttributeValue().replace(" ", ""));
			tGoodsAttribute.setGoodsId(goodsInfoModel.getGoodsId());
			tGoodsAttribute.setCreater(goodsInfoModel.getCreater());
		}
		int addAttributeResult = goodsInfoDao.addGoodsAttribute(attributeList);
		// 根据商品id查询属性及属性值
		Map<String, String> map = new HashMap<String, String>();
		List<TGoodsAttribute> goodsAttributeList = goodsInfoDao.getAllGoodsAttributeById(goodsInfoModel.getGoodsId());
		for (TGoodsAttribute tGoodsAttribute : goodsAttributeList) {
			map.put(tGoodsAttribute.getAttributeValue().replace(" ", ""), String.valueOf(tGoodsAttribute.getId()));
		}
		// 添加规格
		for (TStandardInfo tStandardInfo : standardList) {
			List<String> strList = Arrays.asList(tStandardInfo.getAttributeValues().split("/"));
			for (int i = 0; i < strList.size(); i++) {
				strList.set(i, map.get(strList.get(i)));
			}
			tStandardInfo.setAttributeIds(String.join("/", strList));
			tStandardInfo.setGoodsId(goodsInfoModel.getGoodsId());
			tStandardInfo.setCreater(goodsInfoModel.getCreater());
		}
		int addStandardInfoResult = goodsInfoDao.addStandardInfo(standardList);
		// 判断是否是秒杀类的商品，按规格将库存存到redis中
		TCategoryInfo category = merchantInfoDao.getCategoryByCategoryId(goodsInfoModel.getCategoryId());
		if (category.getParentId() == Integer.parseInt(Config.getMallConfig("seckill.goods.first.category"))) {
			List<TStandardInfo> standards = goodsInfoDao.getAllStandardInfoById(goodsInfoModel.getGoodsId());
			for (TStandardInfo tStandardInfo : standards) {
				String str = "seckillGoods:" + String.valueOf(goodsInfoModel.getGoodsId()) + ":"
						+ String.valueOf(tStandardInfo.getStandardId());
				redisTemplate.opsForValue().set(str, String.valueOf(tStandardInfo.getStock()));
				redisTemplate.opsForSet().add("seckillGoodsStandards", String.valueOf(tStandardInfo.getStandardId()));
			}

			redisTemplate.opsForValue().set("seckillGoodsBeginTime:" + goodsInfoModel.getGoodsId(),
					String.valueOf(goodsInfoModel.getSeckillTime().getTime()));
			redisTemplate.opsForValue().set("seckillGoodsEndTime:" + goodsInfoModel.getGoodsId(),
					String.valueOf(goodsInfoModel.getEndTime().getTime()));
		}
		return addGoodResult > 0 && addPicResult > 0 && addAttributeResult > 0 && addStandardInfoResult > 0
				? Constants.ADD_SUCCESS
				: Constants.ADD_FAIL;
	}

	@Override
	@Transactional
	public int updateGoods(GoodsInfoModel goodsInfoModel) throws Exception {
		int addAttributeResult = 1;
		// 判断是否修改了商品属性
		if (goodsInfoModel.getIsUpdateGoodsAtt() == GoodsConstants.GOODS_IS_UPDATE) {
			// 保证商品属性中不存在/符号
			List<TGoodsAttribute> attributeList = JSONArray.parseArray(goodsInfoModel.getAttributeJson(),
					TGoodsAttribute.class);
			for (TGoodsAttribute tGoodsAttribute : attributeList) {
				if (tGoodsAttribute.getAttributeValue().contains("/")) {
					return Constants.ERROR_CODE_92017;
				}
			}
			// 先删除
			goodsInfoDao.deleteGoodsAttributeById(goodsInfoModel.getGoodsId());
			// 再添加
			for (TGoodsAttribute tGoodsAttribute : attributeList) {
				tGoodsAttribute.setGoodsId(goodsInfoModel.getGoodsId());
				tGoodsAttribute.setCreater(goodsInfoModel.getCreater());
			}
			addAttributeResult = goodsInfoDao.addGoodsAttribute(attributeList);
		}
		int updateResult = 1;
		// 对于被驳回的商品，修改完状态变为待上架
		// 查询初始状态
		if (goodsInfoDao.getGoodsStatus(goodsInfoModel.getGoodsId()) == GoodsConstants.GOODS_STATUS_REJECT) {
			TGoodsInfo goodsInfo = new TGoodsInfo();
			goodsInfo.setGoodsId(goodsInfoModel.getGoodsId());
			goodsInfo.setStatus(GoodsConstants.GOODS_STATUS_WAITING);
			goodsInfoDao.updateGoodsStatus(goodsInfo);
		}
		;
		// 判断是否修改了商品本身信息
		if (goodsInfoModel.getIsUpdateGoodsInfo() == GoodsConstants.GOODS_IS_UPDATE) {
			updateResult = goodsInfoDao.updateGoods(goodsInfoModel);
		}
		int addGoodsPicResult = 1;
		// 判断是否修改了商品图片
		if (goodsInfoModel.getIsUpdateGoodsPic() == GoodsConstants.GOODS_IS_UPDATE) {
			// 先删除
			goodsInfoDao.delGoodsPic(goodsInfoModel.getGoodsId());
			// 再添加
			List<TGoodsPic> picList = JSONArray.parseArray(goodsInfoModel.getPicJson(), TGoodsPic.class);
			for (TGoodsPic tGoodsPic : picList) {
				tGoodsPic.setGoodsId(goodsInfoModel.getGoodsId());
				tGoodsPic.setCreater(goodsInfoModel.getCreater());
			}
			addGoodsPicResult = goodsInfoDao.addGoodsPic(picList);
		}
		int addGoodsStandardResult = 1;
		TCategoryInfo category = merchantInfoDao.getCategoryByCategoryId(goodsInfoModel.getCategoryId());
		// 判断是否修改了商品规格
		if (goodsInfoModel.getIsUpdateGoodsStandard() == GoodsConstants.GOODS_IS_UPDATE) {
			// 保证最高抵扣金币数小于价格
			List<TStandardInfo> standardList = JSONArray.parseArray(goodsInfoModel.getStandardJson(),
					TStandardInfo.class);
			for (TStandardInfo tStandardInfo : standardList) {
				if (tStandardInfo.getGoldNumber() * 100 > tStandardInfo.getPrice()) {
					return Constants.ERROR_CODE_92016;
				}
			}
			Map<String, String> map = new HashMap<String, String>();
			List<TGoodsAttribute> goodsAttributeList = goodsInfoDao
					.getAllGoodsAttributeById(goodsInfoModel.getGoodsId());
			for (TGoodsAttribute tGoodsAttribute : goodsAttributeList) {
				map.put(tGoodsAttribute.getAttributeValue(), String.valueOf(tGoodsAttribute.getId()));
			}
			goodsInfoDao.deleteStandardInfoById(goodsInfoModel.getGoodsId());
			for (TStandardInfo tStandardInfo : standardList) {
				List<String> strList = Arrays.asList(tStandardInfo.getAttributeValues().split("/"));
				for (int i = 0; i < strList.size(); i++) {
					strList.set(i, map.get(strList.get(i)));
				}
				tStandardInfo.setAttributeIds(String.join("/", strList));
				tStandardInfo.setGoodsId(goodsInfoModel.getGoodsId());
				tStandardInfo.setCreater(goodsInfoModel.getCreater());
			}
			addGoodsStandardResult = goodsInfoDao.addStandardInfo(standardList);
			if (category.getParentId() == Integer.parseInt(Config.getMallConfig("seckill.goods.first.category"))) {
				List<TStandardInfo> standards = goodsInfoDao.getAllStandardInfoById(goodsInfoModel.getGoodsId());
				for (TStandardInfo tStandardInfo : standards) {
					String str = "seckillGoods:" + String.valueOf(goodsInfoModel.getGoodsId()) + ":"
							+ String.valueOf(tStandardInfo.getStandardId());
					redisTemplate.opsForValue().set(str, String.valueOf(tStandardInfo.getStock()));
					redisTemplate.opsForSet().add("seckillGoodsStandards",
							String.valueOf(tStandardInfo.getStandardId()));
				}

			}
		}
		if (category.getParentId() == Integer.parseInt(Config.getMallConfig("seckill.goods.first.category"))) {
			redisTemplate.opsForValue().set("seckillGoodsBeginTime:" + goodsInfoModel.getGoodsId(),
					String.valueOf(goodsInfoModel.getSeckillTime().getTime()));
			redisTemplate.opsForValue().set("seckillGoodsEndTime:" + goodsInfoModel.getGoodsId(),
					String.valueOf(goodsInfoModel.getEndTime().getTime()));
		}
		return addAttributeResult > 0 && updateResult > 0 && addGoodsPicResult > 0 && addGoodsStandardResult > 0
				? Constants.UPDATE_SUCCESS
				: Constants.UPDATE_FAIL;
	}

	@Override
	public GoodsModel getAllGoods(GoodsModel goodsModel) throws Exception {
		goodsModel.setGoodsInfoList(goodsInfoDao.getAllGoods(goodsModel));
		goodsModel.setCount(goodsInfoDao.getAllGoodsCount(goodsModel));
		return goodsModel;
	}

	@Override
	public GoodsDetailModel getGoodsInfo(GoodsDetailModel goodsDetailModel) throws Exception {
		goodsDetailModel = goodsInfoDao.getGoodsInfo(goodsDetailModel);
		List<String> nameList = goodsInfoDao.getAttributeNameListByGoodsId(goodsDetailModel.getGoodsId());
		List<GoodsAttributeModel> attributeList = new ArrayList<GoodsAttributeModel>();
		for (String string : nameList) {
			GoodsAttributeModel goodsAttributeModel = new GoodsAttributeModel();
			goodsAttributeModel.setName(string);
			goodsAttributeModel.setValueList(goodsInfoDao.getAttributeValueList(goodsDetailModel.getGoodsId(), string));
			attributeList.add(goodsAttributeModel);
		}
		goodsDetailModel.setAttributeList(attributeList);
		goodsDetailModel.setPicList(goodsInfoDao.getAllGoodsPicByGoodsId(goodsDetailModel.getGoodsId()));
		goodsDetailModel.setStandardList(goodsInfoDao.getAllStandardInfoById(goodsDetailModel.getGoodsId()));
		return goodsDetailModel;
	}

	@Override
	public int updateGoodsStatus(TGoodsInfo goodsInfo) throws Exception {
		int updateResult = 0;

		if (goodsInfo.getStatus() != GoodsConstants.GOODS_STATUS_DELETED) {
			updateResult = goodsInfoDao.updateGoodsStatus(goodsInfo);
		}
		if (goodsInfo.getStatus() == GoodsConstants.GOODS_STATUS_REJECT
				|| goodsInfo.getStatus() == GoodsConstants.GOODS_STATUS_NORMAL) {
			TGoodsAuditingInfo goodsAuditingInfo = new TGoodsAuditingInfo();
			goodsAuditingInfo.setGoodsId(goodsInfo.getGoodsId());
			goodsAuditingInfo.setStatus(goodsInfo.getStatus());
			goodsAuditingInfo.setRejectReason(goodsInfo.getRejectReason());
			goodsAuditingInfo.setCreater(goodsInfo.getModifier());
			goodsInfoDao.addGoodsAuditingInfo(goodsAuditingInfo);
		}
		// 如果是删除，则将该商品的属性、规格和图片全部删除
		if (goodsInfo.getStatus() == GoodsConstants.GOODS_STATUS_DELETED) {
			int delGoodsResult = goodsInfoDao.updateGoodsStatus(goodsInfo);
			int delAttResult = goodsInfoDao.deleteGoodsAttributeById(goodsInfo.getGoodsId());
			int delStandardResult = goodsInfoDao.deleteStandardInfoById(goodsInfo.getGoodsId());
			int delPicResult = goodsInfoDao.delGoodsPic(goodsInfo.getGoodsId());
			if (delGoodsResult > 0 && delAttResult > 0 && delStandardResult > 0 && delPicResult > 0) {
				updateResult = 1;
			}
		}
		return updateResult > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	/**
	 * 修改商品状态时，判断商品是否在金吉豆商城内上架
	 * 
	 * @return
	 */
	public boolean goodsIsInBeanGoods(int goodsId) throws Exception {
		List<TBeanGoods> list = beanGoodsInfoDao.getBeanGoodsListInfo(goodsId, null);
		return  list.size() != 0 ? true : false;
	}

	@Override
	public List<TStandardInfo> getExpiredGoods() throws Exception {
		return goodsInfoDao.getExpiredGoods();
	}

	@Override
	public List<TGoodsAuditingInfo> getAllAuditingInfoByGoodsId(int goodsId) throws Exception {
		return goodsInfoDao.getAllAuditingInfoByGoodsId(goodsId);
	}

	@Override
	public String getGoodsName(int goodsId) throws Exception {
		return goodsInfoDao.getGoodsName(goodsId);
	}

	@Override
	public List<Integer> checkGoodsIdByTemplateId(int templateId) throws Exception {
		return goodsInfoDao.checkGoodsIdByTemplateId(templateId);
	}

	@Override
	public int getCategoryIdByGoodsId(int goodsId) throws Exception {
		return goodsInfoDao.getCategoryIdByGoodsId(goodsId);
	}

}
