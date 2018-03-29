package cm.cn.po;
/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description:危运车辆行车日志
 * @author: 胡林   
 * @date: 2018年3月29日 下午3:47:34 
 */
public class HlCarDrivingLog {
    private HlCheckWaybill hlCheckWaybill;
    private HlCarinfo hlCarinfo;
    private HlPeople driver;
    private HlPeople safer;
    private HlGoodsinfo hlGoodsinfo;
	public HlGoodsinfo getHlGoodsinfo() {
		return hlGoodsinfo;
	}
	public void setHlGoodsinfo(HlGoodsinfo hlGoodsinfo) {
		this.hlGoodsinfo = hlGoodsinfo;
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

