package com.jjn.mall.freight.model;

import com.jjn.mall.freight.dao.pojo.TFreightArea;
import com.jjn.mall.freight.dao.pojo.TFreightTemplet;

import java.util.Date;
import java.util.List;

public class TreightModel {
    private int startNum;
    private int endNum;
    private int count;
    private int merchantId;
    private List<TFreightTemplet> tFreightTemplets;
    private int pageNo;
    private int pageSize;

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TFreightTemplet> gettFreightTemplets() {
        return tFreightTemplets;
    }

    public void settFreightTemplets(List<TFreightTemplet> tFreightTemplets) {
        this.tFreightTemplets = tFreightTemplets;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    @Override
    public String toString() {
        return "BeanGoodsModel [startNum=" + startNum + ", endNum=" + endNum + ", count=" + count + ", tFreightTemplets="
                + tFreightTemplets + ", pageNo=" + pageNo + ", pageSize=" + pageSize + "]";
    }
}
