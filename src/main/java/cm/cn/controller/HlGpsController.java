package cm.cn.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import cm.cn.po.GpsTrack;
import cm.cn.po.GpsTrackResult;
import cm.cn.po.Gpsinfo;
import cm.cn.service.HlGpsService;
/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description:gps定位信息 
 * @author: 胡林   
 * @date: 2018年4月2日 下午8:19:08
 * @modify:  
 */
@Controller
public class HlGpsController {
	@Autowired
	private HlGpsService hlGpsService;
	/**   
	 * @Title: selectGpsByPlateNo   
	 * @Description: 根据车牌号查询车辆gps信息     
	 * @param: @param plateNo
	 * @param: @return     
	 * @return: Gpsinfo      
	 * @throws   
	 */
	@RequestMapping("/selectGpsByPlateNO")
	@ResponseBody
	public Gpsinfo selectGpsByPlateNo(String plateNo){
		Gpsinfo gpsinfo = hlGpsService.selectGpsByPlate(plateNo);
		if (gpsinfo!=null) {
			return gpsinfo;
		}else {
			return null;
		}
	}
	/**   
	 * @Title: selectGpsTrack   
	 * @Description: 根据车牌及时间查询运行轨迹    
	 * @param: @param gpsTrack
	 * @param: @return     
	 * @return: List<GpsTrackResult>      
	 * @throws   
	 */
	@RequestMapping(value = "/selectGpsTrack",method = RequestMethod.POST)
	@ResponseBody
	public List<GpsTrackResult> selectGpsTrack(GpsTrack gpsTrack){
		return hlGpsService.selectGpsTrack(gpsTrack);
	}
}