package cm.cn.po;


public class HlWaybillDetail {
    private String maxVelocity;
    private String OverSpeedVelocityTimes;
    private String status;
	private Gpsinfo gpsinfo;
	private HlPeople driver;
	private HlPeople safer;
	private HlCarinfo hlCarinfo;
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
	private HlSafecard hlSafecard;
	private HlGoodstype hlGoodstype;
	private HlGoodsinfo hlGoodsinfo;
	public HlGoodsinfo getHlGoodsinfo() {
		return hlGoodsinfo;
	}
	public void setHlGoodsinfo(HlGoodsinfo hlGoodsinfo) {
		this.hlGoodsinfo = hlGoodsinfo;
	}
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
