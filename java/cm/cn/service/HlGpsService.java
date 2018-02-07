package cm.cn.service;


import cm.cn.po.Gpsinfo;

public interface HlGpsService {
	public Gpsinfo selectGpsByPlateNo(String plateNo);
	public Gpsinfo selectGpsByPlate(String plateNo);
}
