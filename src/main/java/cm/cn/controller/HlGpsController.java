package cm.cn.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.Gpsinfo;
import cm.cn.service.HlGpsService;
@Controller
public class HlGpsController {
	@Autowired
	private HlGpsService hlGpsService;
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
}