package com.jjn.mall.goods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjn.mall.common.Constants;
import com.jjn.mall.goods.dao.IChanceGoodsDao;
import com.jjn.mall.goods.dao.pojo.ChanceGoodsList;
import com.jjn.mall.goods.dao.pojo.TChanceGoods;
import com.jjn.mall.goods.model.ChanceGoodsInfoModel;
import com.jjn.mall.goods.model.ChanceGoodsListModel;
import com.jjn.mall.goods.model.ChanceGoodsModel;
import com.jjn.mall.goods.service.IChanceGoodsService;

@Service(value ="chanceGoodsService")
public class ChanceGoodsServiceImpl implements IChanceGoodsService{

	@Autowired
	private IChanceGoodsDao chanceGoodsDao;
	
	@Override
	public ChanceGoodsListModel getAllChanceGoods(ChanceGoodsListModel chanceGoodsListModel) throws Exception {
		List<ChanceGoodsList> list = chanceGoodsDao.getAllChanceGoods(chanceGoodsListModel);
		for (ChanceGoodsList chanceGoodsList : list) {
			chanceGoodsList.setAllPeoples(chanceGoodsDao.getSumChanceGoodsRecord(chanceGoodsList.getStandardId()));
			chanceGoodsList.setLuckyPeoples(chanceGoodsDao.getSumLuckyPeoples(chanceGoodsList.getStandardId()));
		}
		chanceGoodsListModel.setChanceGoodsList(list); 
		chanceGoodsListModel.setCount(chanceGoodsDao.getAllChanceGoodsCount(chanceGoodsListModel));
		return chanceGoodsListModel;
	}

	@Override
	public int addChanceGoods(List<TChanceGoods> list) throws Exception {
		return chanceGoodsDao.addChanceGoods(list) > 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
	}

	@Override
	public int updateChanceGoods(List<TChanceGoods> list) throws Exception {
		return chanceGoodsDao.updateChanceGoods(list) > 0 ? Constants.UPDATE_SUCCESS : Constants.UPDATE_FAIL;
	}

	@Override
	public int deleteChanceGoods(int id,int modifier) throws Exception {
		return chanceGoodsDao.deleteChanceGoods(id,modifier) > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}

	@Override
	public ChanceGoodsModel getChanceGoods(int goodsId) throws Exception {
		ChanceGoodsModel chanceGoodsModel = new ChanceGoodsModel();
		chanceGoodsModel.setGoodsId(goodsId);
		chanceGoodsModel.setGoodsName(chanceGoodsDao.getGoodsDetail(goodsId).getName());
		chanceGoodsModel.setMerchantName(chanceGoodsDao.getGoodsDetail(goodsId).getMerchantName());
		chanceGoodsModel.setChanceGoodsList(chanceGoodsDao.getChanceGoods(goodsId));
		return chanceGoodsModel;
	}

	@Override
	public int checkIsExist(int goodsId) throws Exception {
		return chanceGoodsDao.checkIsExist(goodsId);
	}

	@Override
	public int checkNumsByStandardId(int standardId) throws Exception {
		return chanceGoodsDao.checkNumsByStandardId(standardId);
	}

	@Override
	public ChanceGoodsInfoModel getGoodsDetail(int goodsId) throws Exception {
		ChanceGoodsInfoModel chanceGoodsInfoModel = new ChanceGoodsInfoModel();
		chanceGoodsInfoModel.setGoodsId(goodsId);
		if(null != chanceGoodsDao.getGoodsDetail(goodsId)) {
			chanceGoodsInfoModel.setGoodsName(chanceGoodsDao.getGoodsDetail(goodsId).getName());
			chanceGoodsInfoModel.setMerchantName(chanceGoodsDao.getGoodsDetail(goodsId).getMerchantName());
			chanceGoodsInfoModel.setStandardInfoList(chanceGoodsDao.getGoodsStandard(goodsId));
		}else {
			return null;
		}
		return chanceGoodsInfoModel;
	}

}
