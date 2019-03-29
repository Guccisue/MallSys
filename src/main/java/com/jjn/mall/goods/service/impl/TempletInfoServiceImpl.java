package com.jjn.mall.goods.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjn.mall.common.Constants;
import com.jjn.mall.goods.dao.IAttributeInfoDao;
import com.jjn.mall.goods.dao.IAttributeValueDao;
import com.jjn.mall.goods.dao.ITempletInfoDao;
import com.jjn.mall.goods.dao.pojo.TAttributeInfo;
import com.jjn.mall.goods.dao.pojo.TAttributeValue;
import com.jjn.mall.goods.dao.pojo.TTempletInfo;
import com.jjn.mall.goods.model.AttributeInfoDetailModel;
import com.jjn.mall.goods.model.TempletDetailModel;
import com.jjn.mall.goods.model.TempletInfoModel;
import com.jjn.mall.goods.service.IAttributeInfoService;
import com.jjn.mall.goods.service.ITempletInfoService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service(value = "templetInfoService")
public class TempletInfoServiceImpl implements ITempletInfoService {

	@Autowired
	private ITempletInfoDao templetInfoDao;

	@Autowired
	private IAttributeInfoDao attributeInfoDao;

	@Autowired
	private IAttributeValueDao attributeValueDao;

	@Autowired
	private IAttributeInfoService attributeInfoService;

	@Override
	public TempletInfoModel getAllTempletInfo(TempletInfoModel templetInfoModel) throws Exception {
		templetInfoModel.setTempletInfoList(templetInfoDao.getAllTempletInfo(templetInfoModel));
		templetInfoModel.setCount(templetInfoDao.getAllTempletInfoCount(templetInfoModel));
		return templetInfoModel;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int addTempletInfo(TTempletInfo templetInfo) throws Exception {
		// 新增模板数据
		JSONArray json = JSONArray.fromObject(templetInfo.getAttributeArray());
		if (null != json && json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				JSONObject jsonObj = json.getJSONObject(i);
				if (String.valueOf(jsonObj.get("value")).contains("/")) {
					return Constants.ERROR_CODE_92017;
				}
			}
			int templetResult = templetInfoDao.addTempletInfo(templetInfo);
			int result = addAttributeNameAndValue(templetInfo);
			return templetResult > 0 && result == 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
		}
		return Constants.ADD_FAIL;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateTempletInfo(TTempletInfo templetInfo) throws Exception {
		// 先修改模板本身的数据
		JSONArray json = JSONArray.fromObject(templetInfo.getAttributeArray());
		if (null != json && json.size() > 0) {
			for (int i = 0; i < json.size(); i++) {
				JSONObject jsonObj = json.getJSONObject(i);
				if (String.valueOf(jsonObj.get("value")).contains("/")) {
					return Constants.ERROR_CODE_92017;
				}
			}
			int templetResult = templetInfoDao.updateTempletInfo(templetInfo);
			int delResult = delAttributeNameAndValue(templetInfo);
			int addResult = addAttributeNameAndValue(templetInfo);

			return templetResult > 0 && delResult == 0 && addResult == 0 ? Constants.UPDATE_SUCCESS
					: Constants.UPDATE_FAIL;
		}
		return Constants.UPDATE_FAIL;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int deleteTempletInfo(TTempletInfo templetInfo) throws Exception {
		// 先删除模板本身的数据
		int templetResult = templetInfoDao.deleteTempletInfo(templetInfo);
		int result = delAttributeNameAndValue(templetInfo);

		return templetResult > 0 && result == 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}

	@Override
	public TempletDetailModel getTempletInfo(TempletDetailModel templetDetailModel) throws Exception {
		templetDetailModel = templetInfoDao.getTempletInfo(templetDetailModel);
		AttributeInfoDetailModel attributeInfoDetailModel = new AttributeInfoDetailModel();
		attributeInfoDetailModel.setTempletId(templetDetailModel.getId());
		templetDetailModel
				.setAttributeInfoList(attributeInfoService.getAttributeInfoDetailById(attributeInfoDetailModel));
		return templetDetailModel;
	}

	@Override
	public int checkNameIsRepeat(TTempletInfo templetInfo) throws Exception {
		return templetInfoDao.checkNameIsRepeat(templetInfo);
	}

	// 添加属性和属性值
	public int addAttributeNameAndValue(TTempletInfo templetInfo) throws Exception {
		// 添加新的模板属性和属性值
		int addAttributeInfoResult = 0;
		int addAttributeValueResult = 0;
		JSONArray json = JSONArray.fromObject(templetInfo.getAttributeArray());
		if (null != json && json.size() > 0) {
			TAttributeInfo attributeInfo = new TAttributeInfo();
			attributeInfo.setCreater(templetInfo.getCreater());
			attributeInfo.setTempletId(templetInfo.getId());
			for (int i = 0; i < json.size(); i++) {
				JSONObject jsonObj = json.getJSONObject(i);
				attributeInfo.setName(String.valueOf(jsonObj.get("name")));
				addAttributeInfoResult = attributeInfoDao.addAttributeInfo(attributeInfo);
				TAttributeValue attributeValue = new TAttributeValue();
				attributeValue.setCreater(templetInfo.getCreater());
				attributeValue.setAttributeId(attributeInfo.getId());
				List<String> valueList = Arrays.asList(String.valueOf(jsonObj.get("value")).split(","));
				for (String string : valueList) {
					attributeValue.setAttributeValue(string);
					addAttributeValueResult = attributeValueDao.addAttributeValue(attributeValue);
				}
			}
		}
		return addAttributeInfoResult > 0 && addAttributeValueResult > 0 ? Constants.ADD_SUCCESS : Constants.ADD_FAIL;
	}

	// 删除属性和属性值
	public int delAttributeNameAndValue(TTempletInfo templetInfo) throws Exception {
		// 根据模板id查询所有属性
		List<TAttributeInfo> list = attributeInfoDao.getAllAttributeInfoById(templetInfo.getId());
		// 根据模板id删除模板的属性
		int attributeInfoResult = attributeInfoDao.deleteAttributeInfoByTempletId(templetInfo.getId());
		// 根据属性id删除属性值
		int attributeValueResult = attributeValueDao.deleteAttributeValueByAttributeId(list);

		return attributeInfoResult > 0 && attributeValueResult > 0 ? Constants.DELETE_SUCCESS : Constants.DELETE_FAIL;
	}

	@Override
	public List<TTempletInfo> getTempletInfoByCategoryId(int categoryId, int merchantId) throws Exception {
		return templetInfoDao.getTempletInfoByCategoryId(categoryId, merchantId);
	}

	@Override
	public List<Integer> checkTemplateByCategoryId(int categoryId) throws Exception {
		return templetInfoDao.checkTemplateByCategoryId(categoryId);
	}
}
