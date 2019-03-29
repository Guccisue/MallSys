package com.jjn.mall.freight.dao;

import com.jjn.mall.freight.dao.pojo.TFreightArea;
import com.jjn.mall.freight.dao.pojo.TFreightTemplet;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IFreightAreaMapper {

	int checkFreightTempletIsRepeat(String name);

	int addFreightTemplet(TFreightTemplet tFreightTemplet);

	int addFreightArea(@Param(value = "areaList") List<TFreightArea> areaList);

	TFreightTemplet getTempletSingle(@Param(value = "id") Integer templetid);

	int updateFreightArea(@Param(value = "areaList") List<TFreightArea> tFreightArea);

	int deleteFreightArea(@Param(value = "areaList") List<TFreightArea> tFreightArea);

	int updateFreightTemplet(TFreightTemplet tFreightTemplet);

	int deleteFreightTemplet(@Param(value = "templetId") Integer templetId);

	List<TFreightTemplet> getAllFreightTempletInfo(Integer merchantId);

	int getAllFreightTempletInfoCount(Integer merchantId);

	int selectAreaList(@Param(value = "f_templetid") Integer id);
}