package cm.cn.po;
/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description:承运人基本信息
 * @author: 胡林   
 * @date: 2018年3月29日 下午3:47:55 
 */
public class HlCarrierInfo {
    private HlCheckWaybill hlCheckWaybill;
    private HlCarinfo hlCarinfo;
    private HlPeople driver;
    private HlPeople safer;
    private HlGoodstype hlGoodstype;
	public HlGoodstype getHlGoodstype() {
		return hlGoodstype;
	}
	public void setHlGoodstype(HlGoodstype hlGoodstype) {
		this.hlGoodstype = hlGoodstype;
	}
	public HlCheckWaybill getHlCheckWaybill() {
		return hlCheckWaybill;
	}
	public void setHlCheckWaybill(HlCheckWaybill hlCheckWaybill) {
		this.hlCheckWaybill = hlCheckWaybill;
	}
	public HlCarinfo getHlCarinfo() {
		return hlCarinfo;
	}
	public void setHlCarinfo(HlCarinfo hlCarinfo) {
		this.hlCarinfo = hlCarinfo;
	}
	public HlPeople getDriver() {
		return driver;
	}
	public void setDriver(HlPeople driver) {
		this.driver = driver;
	}
	public HlPeople getSafer() {
		return safer;
	}
	public void setSafer(HlPeople safer) {
		this.safer = safer;
	}
    
}

