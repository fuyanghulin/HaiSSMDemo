package cm.cn.service;


import cm.cn.po.GpsMsg;
import cm.cn.po.Gpsinfo;

public interface HlGpsService {
	public Gpsinfo selectGpsByPlateNo(String plateNo);
	public GpsMsg selectGpsByPlate(String plateNo,int goods_id);
}
