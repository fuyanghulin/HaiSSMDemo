package cm.cn.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlCarinfo;
import cm.cn.po.HlCompany;
import cm.cn.po.HlPeople;
import cm.cn.po.HlRemind;
import cm.cn.po.Page;
import cm.cn.service.HlRemindService;

@Controller
public class HlRemindController {
	@Autowired
	private HlRemindService hlRemindService;
	@RequestMapping(value="/getRemind",method=RequestMethod.GET)
	@ResponseBody
    public HlRemind getRemind(){
		return hlRemindService.getRemind();
    }
	@RequestMapping(value="/getCompanyRemind",method=RequestMethod.POST)
	@ResponseBody
	public Page<HlCompany> getCompanyRemind(int current, int pageNum,int id){
		List<HlCompany> list = new ArrayList<>();
		if(id==0){
			list = hlRemindService.getCompanyRemindByBusiLic();
		}else{
			list = hlRemindService.getCompanyRemindByBusiCer();
		}
		Page<HlCompany> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	@RequestMapping(value="/getCarRemind",method=RequestMethod.POST)
	@ResponseBody
	public Page<HlCarinfo> getCarRemind(int current, int pageNum,int id){
		List<HlCarinfo> list = new ArrayList<>();
		if(id==0){
			list = hlRemindService.getCarRemindByInsuranceDate();
		}else if(id==1){
			list =hlRemindService.getCarRemindByDutyInsurancedate();
		}else if(id==2){
			list =hlRemindService.getCarRemindByDutypeoDate();
		}else if(id==3){
			list =hlRemindService.getCarRemindByCertyearDate();
		}else if(id==4){
			list =hlRemindService.getCarRemindByRoadDate();
		}else if(id==5){
			list =hlRemindService.getCarRemindByYearcheckDate();
		}else if(id==6){
			list =hlRemindService.getCarRemindByTecDate();
		}else{
			list =hlRemindService.getCarRemindBySecImprovedate();
		}
		Page<HlCarinfo> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	@RequestMapping(value="/getPeopleRemind",method=RequestMethod.POST)
	@ResponseBody
	public Page<HlPeople> getPeopleRemind(int current, int pageNum,int id){
		List<HlPeople> list = new ArrayList<>();
		if(id==0){
			list = hlRemindService.getPeopleRemindByDriLicenceTime();
		}else if(id==1){
			list = hlRemindService.getPeopleRemindByDriverzigezhengTime();
		}else{
			list = hlRemindService.getPeopleRemindByDriverzigezhengTime2();
		}
		Page<HlPeople> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
}