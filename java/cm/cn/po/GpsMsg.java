package cm.cn.po;

import java.util.List;

public class GpsMsg {
    private String maxVelocity;
    private String OverSpeedVelocityTimes;
    private String status;
	private Gpsinfo gpsinfo;
	private List<HlPeople> hlPeople;
	private HlSafecard hlSafecard;
	private HlGoodstype hlGoodstype;
	public HlSafecard getHlSafecard() {
		return hlSafecard;
	}
	public void setHlSafecard(HlSafecard hlSafecard) {
		this.hlSafecard = hlSafecard;
	}
	public HlGoodstype getHlGoodstype() {
		return hlGoodstype;
	}
	public void setHlGoodstype(HlGoodstype hlGoodstype) {
		this.hlGoodstype = hlGoodstype;
	}
	public List<HlPeople> getHlPeople() {
		return hlPeople;
	}
	public void setHlPeople(List<HlPeople> hlPeople) {
		this.hlPeople = hlPeople;
	}
	public String getMaxVelocity() {
		return maxVelocity;
	}
	public void setMaxVelocity(String maxVelocity) {
		this.maxVelocity = maxVelocity;
	}
	public String getOverSpeedVelocityTimes() {
		return OverSpeedVelocityTimes;
	}
	public void setOverSpeedVelocityTimes(String overSpeedVelocityTimes) {
		OverSpeedVelocityTimes = overSpeedVelocityTimes;
	}
    public String getStatus() {
	  	return status;
	}
	public void setStatus(String status) {
	  	this.status = status;
	}
	public Gpsinfo getGpsinfo() {
		return gpsinfo;
	}
	public void setGpsinfo(Gpsinfo gpsinfo) {
		this.gpsinfo = gpsinfo;
	}
}
