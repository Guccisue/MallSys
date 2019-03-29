package com.jjn.mall.goods.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjn.mall.common.Constants;
import com.jjn.mall.goods.dao.IBeanGoodsInfoDao;
import com.jjn.mall.goods.dao.pojo.TBeanGoods;
import com.jjn.mall.goods.model.BeanGoodsModel;
import com.jjn.mall.goods.model.GoodsModel;
import com.jjn.mall.goods.model.ResultJsonModel;
import com.jjn.mall.goods.service.IBeanGoodsInfoService;

@Service(value = "beanGoodsInfoService")
public class BeanGoodsServiceImpl implements IBeanGoodsInfoService {

	@Autowired
	private IBeanGoodsInfoDao beanGoodsInfoDao;

	@Override
	public BeanGoodsModel getAllBeanGoodsInfo(BeanGoodsModel beanGoodsModel) throws Exception {
		beanGoodsModel.setGoodsInfoList(beanGoodsInfoDao.getAllBeanGoodsInfo(beanGoodsModel));
		beanGoodsModel.setCount(beanGoodsInfoDao.getAllBeanGoodsInfoCount(beanGoodsModel));
		return beanGoodsModel;
	}

	@Override
	public int addBeanGoodsInfo(int goodsId, int type, int creater, int sequence) throws Exception {
		return beanGoodsInfoDao.addBeanGoodsInfo(goodsId, type, creater, sequence) > 0 ? Constants.ADD_SUCCESS
				: Constants.ADD_FAIL;
	}

	@Override
	public int updateBeanGoodsInfo(int goodsId, int type, int creater, int sequence) throws Exception {
		return beanGoodsInfoDao.updateBeanGoodsInfo(goodsId, type, creater, sequence) > 0 ? Constants.UPDATE_SUCCESS
				: Constants.UPDATE_FAIL;
	}

	@Override
	public int deleteBeanGoodsInfo(int goodsId, int modifier, int type) throws Exception {
		return beanGoodsInfoDao.deleteBeanGoodsInfo(goodsId, modifier, type) > 0 ? Constants.DELETE_SUCCESS
				: Constants.DELETE_FAIL;
	}

	@Override
	public int checkBeanGoodsIsRepeat(int goodsId, int type, int sequence) throws Exception {
		return beanGoodsInfoDao.checkBeanGoodsIsRepeat(goodsId, type, sequence);
	}

	/**
	 * 新增金吉豆商品 如果是超值抵扣商品 要已有超值抵扣判断数量是不是大于3
	 */
	@Override
	public ResultJsonModel checkBeanGoodsIsRepeatAdd(int goodsId, int type, int sequence) throws Exception {
		ResultJsonModel resulModel = new ResultJsonModel();
		if (type == 1) {
			if (beanGoodsInfoDao.checkBeanGoodsIsRepeat(null, type, null) >= 3) {
				resulModel.setResultCode(Constants.ERROR_CODE_92018);
				resulModel.setResultMsg("该商品数量过多");
				return resulModel;
			}
		}
		// 先判断商品和类型不重复
		// 这里在判断重复商品做了两个判断是为了根据操作失误提示不同的错误信息
		if (beanGoodsInfoDao.checkBeanGoodsIsRepeat(goodsId, type, null) > 0) {
			resulModel.setResultCode(Constants.ERROR_CODE_92001);
			resulModel.setResultMsg("名称已存在");
			return resulModel;
		}
		if (beanGoodsInfoDao.checkBeanGoodsIsRepeat(null, type, sequence) > 0) {
			resulModel.setResultCode(Constants.ERROR_CODE_92019);
			resulModel.setResultMsg("序号无法相同");
			return resulModel;
		}
		return null;
	}

	@Override
	public TBeanGoods getBeanGoodsInfo(int goodsId, int type) throws Exception {
		return beanGoodsInfoDao.getBeanGoodsInfo(goodsId, type);
	}

	/**
	 * 修改金吉豆商品 如果是超值抵扣商品 要已有超值抵扣判断数量是不是大于3
	 */
	@Override
	public ResultJsonModel checkBeanGoodsIsRepeatUpdate(int goodsId, int type, int sequence, int orlType)
			throws Exception {
		ResultJsonModel resulModel = new ResultJsonModel();

		// 如果什么都没有修改
		TBeanGoods tBeanGood = beanGoodsInfoDao.getBeanGoodsInfo(goodsId, orlType);
		if (tBeanGood.getType() == type & tBeanGood.getSequence() == sequence) {
			return null;
		}
		// 如果修改后为超值抵扣类型
		if (type == 1) {

			int typeOneNums = beanGoodsInfoDao.checkBeanGoodsIsRepeat(null, type, null);

			// 如果之前的类型不是超值抵扣，并且超值抵扣类型的数量已经在3个或者三个以上
			if (tBeanGood.getType() != 1 & typeOneNums >= 3) {
				resulModel.setResultCode(Constants.ERROR_CODE_92018);
				resulModel.setResultMsg("该商品数量过多");
			}
			// 获取超值抵扣类型的数量

		}
		// 先判断商品和类型不重复
		if (orlType != type) {
			if (beanGoodsInfoDao.checkBeanGoodsIsRepeat(goodsId, type, null) > 0) {
				resulModel.setResultCode(Constants.ERROR_CODE_92001);
				resulModel.setResultMsg("名称已存在");
				return resulModel;
			}
		}

		if (tBeanGood.getSequence() != sequence) {
			if (beanGoodsInfoDao.checkBeanGoodsIsRepeat(null, type, sequence) > 0) {
				resulModel.setResultCode(Constants.ERROR_CODE_92019);
				resulModel.setResultMsg("已有的序号");
				return resulModel;
			}
		}
		return null;
	}
}
