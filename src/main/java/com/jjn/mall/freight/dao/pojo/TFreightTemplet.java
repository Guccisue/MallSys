package com.jjn.mall.freight.dao.pojo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TFreightTemplet {
	private Integer id;

	private String name;

	private String pid;

	private String p;

	private String cid;

	private String c;

	private String did;

	private String d;

	private String portOfdeparture;

	private int isfreeshipping;

	private int isrestrict;

	private Integer merchantid;

	private Integer creater;

	private Date createtime;

	private Integer modifier;

	private Date modifytime;

	private String areaListStr;

	private List<TFreightArea> updateAreaList;

	private List<TFreightArea> addAreaList;

	private List<TFreightArea> delAreaList;

	private List<TFreightArea> areaList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
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

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid == null ? null : cid.trim();
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c == null ? null : c.trim();
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did == null ? null : did.trim();
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d == null ? null : d.trim();
	}

	public int getIsfreeshipping() {
		return isfreeshipping;
	}

	public void setIsfreeshipping(int isfreeshipping) {
		this.isfreeshipping = isfreeshipping;
	}

	public int getIsrestrict() {
		return isrestrict;
	}

	public void setIsrestrict(int isrestrict) {
		this.isrestrict = isrestrict;
	}

	public Integer getMerchantid() {
		return merchantid;
	}

	public void setMerchantid(Integer merchantid) {
		this.merchantid = merchantid;
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

	public Integer getModifier() {
		return modifier;
	}

	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}

	public Date getModifytime() {
		return modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public String getPortOfdeparture() {
		return portOfdeparture;
	}

	public void setPortOfdeparture(String portOfdeparture) {
		this.portOfdeparture = portOfdeparture;
	}

	public List<TFreightArea> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<TFreightArea> areaList) {
		this.areaList = areaList;
	}

	public String getAreaListStr() {
		return areaListStr;
	}

	public void setAreaListStr(String areaListStr) {
		this.areaListStr = areaListStr;
	}

	public List<TFreightArea> getUpdateAreaList() {
		return updateAreaList;
	}

	public void setUpdateAreaList(List<TFreightArea> updateAreaList) {
		this.updateAreaList = updateAreaList;
	}

	public List<TFreightArea> getAddAreaList() {
		return addAreaList;
	}

	public void setAddAreaList(List<TFreightArea> addAreaList) {
		this.addAreaList = addAreaList;
	}

	public List<TFreightArea> getDelAreaList() {
		return delAreaList;
	}

	public void setDelAreaList(List<TFreightArea> delAreaList) {
		this.delAreaList = delAreaList;
	}

	/**
	 * 判断对象中属性值是否全为空
	 *
	 * @param object
	 * @return
	 */
	public static boolean checkTempletAllFieldsIsNull(TFreightTemplet object) {
		if (null == object) {
			return true;
		}
		String param = "";
		try {
			for (Field f : object.getClass().getDeclaredFields()) {
				f.setAccessible(true);

				System.out.print(f.getName() + ":");
				System.out.println(f.get(object));
				param = f.get(object).toString();
				if (f.get(object) != null && StringUtils.isNotBlank(param)) {

					return false;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return true;
	}

	/**
	 * [tFreightTemplet]
	 *
	 * @Author:chenjunyan
	 * @Date:上午 10:39 2019/3/27 0027
	 */
	public void setAllParam(TFreightTemplet tFreightTemplet) throws Exception {
		// 发货地的属性set
		JSONObject pod = (JSONObject) JSON.parseArray(tFreightTemplet.getPortOfdeparture()).get(0);
		tFreightTemplet.setPid(pod.getString("code"));
		tFreightTemplet.setP(pod.getString("name"));
		JSONObject pod_C = (JSONObject) pod.getJSONArray("citylist").get(0);
		tFreightTemplet.setC(pod_C.getString("name"));
		tFreightTemplet.setCid("code");
		JSONObject pod_D = (JSONObject) pod_C.getJSONArray("arealist").get(0);
		tFreightTemplet.setD(pod_D.getString("name"));
		tFreightTemplet.setDid(pod_D.getString("code"));

		// 运费模板的送达地区
		List<TFreightArea> areaList = JSONArray.parseArray(tFreightTemplet.getAreaListStr(), TFreightArea.class);
		tFreightTemplet.setAreaList(areaList);
		tFreightTemplet.setPid(pod.getString("code"));
		tFreightTemplet.setP(pod.getString("name"));
	}

	/**
	 * @Description:给areaList set templetId
	 * @Author:chenjunyan
	 * @Date:下午 3:36 2019/3/27 0027
	 */
	public void setTempletIdForAreas() {
		if (this.getId() != null & areaList != null) {
			areaList.forEach(area -> {
				area.setTempletid(this.getId());
				area.setCreater(this.getCreater());
			});
		}
	}

	public void getUpdateAreas(List<TFreightArea> orlTempletAreas) {

		// 根据地域code字段查询出增加的地域和删除的地域
		List<TFreightArea> addAreas = areaList.stream().filter(item -> !orlTempletAreas.stream()
				.map(area -> area.getPid()).collect(Collectors.toList()).contains(item.getPid()))
				.collect(Collectors.toList());
		addAreas.forEach(area ->{
			area.setTempletid(this.getId());
			area.setCreater(this.getCreater());
		});
		this.setAddAreaList(addAreas);
		
		List<TFreightArea> DelAreas = orlTempletAreas.stream().filter(item -> !areaList.stream()
				.map(area -> area.getPid()).collect(Collectors.toList()).contains(item.getPid()))
				.collect(Collectors.toList());
		this.setDelAreaList(DelAreas);
		
		this.setUpdateAreaList(new ArrayList<TFreightArea>());
		// 修改前 修改后的list取交集 这部分地域集是修改部分
		// ps：这从修改后的list中去拿去
		List<TFreightArea> updateAreas = orlTempletAreas.stream().filter(item -> areaList.stream()
				.map(area -> area.getPid()).collect(Collectors.toList()).contains(item.getPid()))
				.collect(Collectors.toList());
		updateAreas.forEach(area -> {
			for (TFreightArea orlArea : orlTempletAreas) {
				if (!orlArea.getPid().equals(area.getPid()) || orlArea.getFirstcount() != area.getFirstcount()
						|| orlArea.getFirstFreight() != area.getFirstFreight()
						|| orlArea.getContinuation() != area.getContinuation()
						|| orlArea.getContinueFreight() != area.getContinueFreight()) {
					this.getUpdateAreaList().add(area);
				}
			}
		});
	}

}