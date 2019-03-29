package com.jjn.mall.freight.service.impl;

import com.jjn.mall.common.Constants;
import com.jjn.mall.freight.dao.IFreightAreaMapper;
import com.jjn.mall.freight.dao.pojo.TFreightArea;
import com.jjn.mall.freight.dao.pojo.TFreightTemplet;
import com.jjn.mall.freight.model.TreightModel;
import com.jjn.mall.freight.service.IFreightService;
import com.jjn.mall.goods.model.ResultJsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FrightService implements IFreightService {

    @Autowired
    private IFreightAreaMapper iFreightAreaMapper;

    public ResultJsonModel checkFreightTempletIsRepeat(String name) {
        ResultJsonModel resulModel = new ResultJsonModel();
        if (iFreightAreaMapper.checkFreightTempletIsRepeat(name) > 0) {
            resulModel.setResultCode(Constants.ERROR_CODE_92001);
            resulModel.setResultMsg("名称已存在");
            return resulModel;
        }
        return null;
    }

    @Override
    @Transactional
    public int addFreightTemplet(TFreightTemplet tFreightTemplet) {
        return iFreightAreaMapper.addFreightTemplet(tFreightTemplet) > 0 ? Constants.ADD_SUCCESS
                : Constants.ADD_FAIL;
    }

    @Override
    public int addFreightArea(List<TFreightArea> areaList) {
        return iFreightAreaMapper.addFreightArea(areaList) > 0 ? Constants.ADD_SUCCESS
                : Constants.ADD_FAIL;
    }

    @Override
    public TFreightTemplet getTempletSingle(Integer templetId) {
        return iFreightAreaMapper.getTempletSingle(templetId);
    }

    @Override
    public int updateFreightArea(TFreightTemplet tFreightTemplet) {
        //修改地域运费计费方式
        if (tFreightTemplet.getUpdateAreaList().size() != 0 || tFreightTemplet.getUpdateAreaList() != null) {
            iFreightAreaMapper.updateFreightArea(tFreightTemplet.getUpdateAreaList());
        }
        //新增的地域运费计费方式
        if (tFreightTemplet.getAddAreaList().size() != 0 || tFreightTemplet.getAddAreaList() != null) {
            iFreightAreaMapper.addFreightArea(tFreightTemplet.getAddAreaList());
        }
        //删除地域运费计费方式
        if (tFreightTemplet.getDelAreaList().size() != 0 || tFreightTemplet.getDelAreaList() != null) {
            iFreightAreaMapper.deleteFreightArea(tFreightTemplet.getDelAreaList());
        }
        iFreightAreaMapper.updateFreightTemplet(tFreightTemplet);
        return Constants.UPDATE_SUCCESS;
    }

    @Override
    public int deleteFreightTemplet(Integer templetId) {
        return iFreightAreaMapper.deleteFreightTemplet(templetId) > 0 ? Constants.DELETE_FAIL
                : Constants.DELETE_SUCCESS;
    }

    public TreightModel getAllFreightTempletInfo(TreightModel treightModel) {
        treightModel.settFreightTemplets(iFreightAreaMapper.getAllFreightTempletInfo(treightModel.getMerchantId()));
        treightModel.setCount(iFreightAreaMapper.getAllFreightTempletInfoCount(treightModel.getMerchantId()));
        return treightModel;
    }
}
