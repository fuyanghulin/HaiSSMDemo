package cm.cn.service;


import cm.cn.po.Gpsinfo;

public interface HlGpsService {
	public Gpsinfo selectGpsByPlate(String plateNo);
}
