package cm.cn.service;

import java.util.List;
import cm.cn.po.GpsTrackResult;
import cm.cn.po.GpsTrack;
import cm.cn.po.Gpsinfo;

public interface HlGpsService {
	public Gpsinfo selectGpsByPlate(String plateNo);

	public List<GpsTrackResult> selectGpsTrack(GpsTrack gpsTrack);
}
