package cm.cn.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cm.cn.datasource.CustomerContextHolder;
import cm.cn.mapper.MyGpsMapper;
import cm.cn.po.GpsTrackResult;
import cm.cn.po.GpsTrack;
import cm.cn.po.Gpsinfo;
import cm.cn.service.HlGpsService;

@Service
public class HlGpsServiceImpl implements HlGpsService {
	@Autowired
	private MyGpsMapper myGpsMapper;

	@Override
	public Gpsinfo selectGpsByPlate(String plateNo) {
		Gpsinfo gpsinfo = new Gpsinfo();
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GPS);
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String tableName = "gpsinfo" + format.format(date);
		gpsinfo = myGpsMapper.selectGpsByPlateNo(tableName, plateNo);

		return gpsinfo;
	}

	/**
	 * Title: selectGpsTrack Description:
	 * 
	 * @param gpsTrack
	 * @see cm.cn.service.HlGpsService#selectGpsTrack(cm.cn.po.GpsTrack)
	 */
	@Override
	public List<GpsTrackResult> selectGpsTrack(GpsTrack gpsTrack) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GPS);
		Date date = new Date();
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String tableName = "gpsinfo" + format.format(date);

		String plateNo = gpsTrack.getPlateNo();
		Date startTime = gpsTrack.getStartTime();
		Date finishTime = gpsTrack.getFinishTime();
		double longitude = gpsTrack.getLongitude();
		double latitude = gpsTrack.getLatitude();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		String Time_1 = sdf.format(startTime);
		String Time_2 = sdf.format(finishTime);
		if (longitude == 0 || latitude == 0) {
			return myGpsMapper.selectGpsTrack(tableName, plateNo, Time_1, Time_2);
		} else {
			return null;
		}
	}
}
