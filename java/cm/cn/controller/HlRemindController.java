package cm.cn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlCompany;
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
	/*@RequestMapping(value="/getCompanyRemind",method=RequestMethod.POST)
	@ResponseBody
	public Page<HlCompany> getCompanyRemind(Integer id){
		
	}*/
}
