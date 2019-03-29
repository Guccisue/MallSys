package com.jjn.mall.report.task;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.jjn.mall.goods.dao.pojo.TStandardInfo;
import com.jjn.mall.goods.service.IGoodsInfoService;

public class SeckillGoodsTask {
	Log LOG = LogFactory.getLog(this.getClass());
	
	@Autowired
	private IGoodsInfoService goodsInfoService;
	
	@Resource(name = "redisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	
	public void doSeckillGoodsTask() {
		try {
			//每日定时任务查询已过期的商品的规格
			List<TStandardInfo> list = goodsInfoService.getExpiredGoods();
			if(null != list && list.size() > 0) {
				for (TStandardInfo standardInfo : list) {
					redisTemplate.delete("seckillGoods:" + standardInfo.getGoodsId()+":"+standardInfo.getStandardId());
					redisTemplate.delete("seckillUserList:" + standardInfo.getGoodsId()+":"+standardInfo.getStandardId());
					redisTemplate.opsForSet().remove("seckillGoodsStandards", standardInfo.getStandardId());
				}
			}
		} catch (Exception e) {
			LOG.error("清除秒杀商品redis定时任务异常");
		}
	}
}
