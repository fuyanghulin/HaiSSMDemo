package cm.cn.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cm.cn.datasource.CustomerContextHolder;
import cm.cn.mapper.MyGpsMapper;
import cm.cn.po.Gpsinfo;
import cm.cn.service.HlGpsService;
@Service
public class HlGpsServiceImpl  implements HlGpsService {
    @Autowired
    private MyGpsMapper myGpsMapper;
	@Override
	public Gpsinfo selectGpsByPlate(String plateNo) {
		Gpsinfo gpsinfo = new Gpsinfo();
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GPS);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMdd");
		String tableName="gpsinfo"+format.format(date);
		gpsinfo = myGpsMapper.selectGpsByPlateNo(tableName,plateNo);

		return gpsinfo;
	}
 }


