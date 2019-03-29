package com.jjn.mall.freight.dao.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TFreightArea {
    private Integer id;

    private Integer templetid;

    private String pid;

    private String p;

    private Integer firstcount;

    private Double firstFreight;

    private Integer continuation;

    private Double continueFreight;

    private Integer creater;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    public Integer getId() {
    	if(id == null) {
    		return 0;
    	}
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTempletid() {
        return templetid;
    }

    public void setTempletid(Integer templetid) {
        this.templetid = templetid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p == null ? null : p.trim();
    }

    public Integer getFirstcount() {
        return firstcount;
    }

    public void setFirstcount(Integer firstcount) {
        this.firstcount = firstcount;
    }

    public Double getFirstFreight() {
        return firstFreight;
    }

    public void setFirstFreight(Double firstFreight) {
        this.firstFreight = firstFreight;
    }

    public Integer getContinuation() {
        return continuation;
    }

    public void setContinuation(Integer continuation) {
        this.continuation = continuation;
    }

    public Double getContinueFreight() {
        return continueFreight;
    }

    public void setContinueFreight(Double continueFreight) {
        this.continueFreight = continueFreight;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}