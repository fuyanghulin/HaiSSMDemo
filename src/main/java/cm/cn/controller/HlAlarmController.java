package cm.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlAlarm;
import cm.cn.po.Page;
import cm.cn.service.HlAlarmService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 处理报警中心的有关信息
 * @author: 胡林   
 * @date: 2018年4月2日 下午4:11:47
 * @modify:  
 */
@Controller
public class HlAlarmController {
	@Autowired
	private HlAlarmService hlAlarmService ;
	@RequestMapping("/selectAllAlarm")
	@ResponseBody
	public Page<HlAlarm> selectAllAlarm(int current,int pageNum){
		List<HlAlarm> list = hlAlarmService.selectAllAlarm();
		Page<HlAlarm> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	@RequestMapping("/selectAlarmByCompanyId")
	@ResponseBody
	public Page<HlAlarm> selAlarmByCompanyId(int current,int pageNum,int company_id){
		List<HlAlarm> list =hlAlarmService.selectAlarmByCompanyId(company_id);
		Page<HlAlarm> page = null;
		if (list.size()>0) {
			page = new Page<>(current,pageNum,list);
			return page;
		}
		else{
			return null;
		}
	}
	@RequestMapping(value="/insertAlarm",method=RequestMethod.POST)
	@ResponseBody
	public int insertAlarm(HlAlarm hlAlarm){
		return hlAlarmService.insertAlarm(hlAlarm);
	}
}
