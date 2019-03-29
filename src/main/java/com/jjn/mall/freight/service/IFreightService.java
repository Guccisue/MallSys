package com.jjn.mall.freight.service;

import com.jjn.mall.freight.dao.pojo.TFreightArea;
import com.jjn.mall.freight.dao.pojo.TFreightTemplet;
import com.jjn.mall.freight.model.TreightModel;
import com.jjn.mall.goods.model.ResultJsonModel;

import java.util.List;

public interface IFreightService {

    /**
     *
     [name]
     * @Description:根据模板名称检测是否重复添加模板
     * @Author:chenjunyan
     * @Date:下午 2:14 2019/3/27 0027
     */
    ResultJsonModel checkFreightTempletIsRepeat(String name);

    /**
     *
     [tFreightTemplet]
     * @Description:新增模板
     * @Author:chenjunyan
     * @Date:下午 2:14 2019/3/27 0027
     */
    int addFreightTemplet(TFreightTemplet tFreightTemplet);

    /**
     *
     [areaList]
     * @Description:新增
     * @Author:chenjunyan
     * @Date:下午 2:32 2019/3/27 0027
     */
    int addFreightArea(List<TFreightArea> areaList);

    /**
     *
     [templet]
     * @Description:查询单个模板信息
     * @Author:chenjunyan
     * @Date:上午 10:28 2019/3/28 0028
     */
    TFreightTemplet getTempletSingle(Integer templet);

    /**
     *
     [tFreightArea]
     * @Description:修改tFreightArea
     * @Author:chenjunyan
     * @Date:下午 2:54 2019/3/28 0028
     */
    int updateFreightArea(TFreightTemplet tFreightTemplet);

    /**
     *
     [templetId]
     * @Description:删除运费模板
     * @Author:chenjunyan
     * @Date:下午 3:49 2019/3/28 0028
     */
    int deleteFreightTemplet(Integer templetId);

    TreightModel getAllFreightTempletInfo(TreightModel treightModel);
}
