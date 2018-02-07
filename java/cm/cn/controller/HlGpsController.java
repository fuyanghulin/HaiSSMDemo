package cm.cn.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlWaybillDetail;
import cm.cn.po.Gpsinfo;
import cm.cn.service.HlGpsService;
@Controller
public class HlGpsController {
	@Autowired
	private HlGpsService hlGpsService;
	@RequestMapping("/selectGpsByPlateNO")
	@ResponseBody
	public Gpsinfo selectGpsByPlateNo(String plateNo){
	
		Gpsinfo list = hlGpsService.selectGpsByPlateNo(plateNo);
		//Page<Gpsinfo> page = null;
		if (list!=null) {
			return list;
			//page = new Page<>(current, pageNum, list);
			//return page;
		}
		else {
			return null;
		}
	}
	@RequestMapping("/selectGpsByPlateNO2")
	@ResponseBody
	public Gpsinfo selectGpsByPlateNo2(String plateNo,int goods_id){
	
		Gpsinfo gpsinfo = hlGpsService.selectGpsByPlate(plateNo);
		if (gpsinfo!=null) {
			return gpsinfo;
		}else {
			return null;
		}
	}
}
