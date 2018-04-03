package cm.cn.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlDriveruser;
import cm.cn.po.HlPeople;
import cm.cn.service.HlDriveruserService;
import cm.cn.util.GetCheckCode;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 用于驾驶员的信息查询和手机登陆处理
 * @author: 胡林   
 * @date: 2018年4月2日 下午3:30:36
 * @modify:  
 */
@Controller
public class DriverUseInfoController {
	@Autowired
	private HlDriveruserService hlDriveruserService;
	
	/**   
	 * @Title: selectDriverUserMsgByPhone   
	 * @Description: 根据手机号查询驾驶员的人员信息   
	 * @param: @param phone
	 * @param: @return     
	 * @return: HlPeople      
	 * @throws   
	 */
	@RequestMapping("/selectDriverUserMsgByPhone")
	@ResponseBody
	public HlPeople selectDriverUserMsgByPhone(String phone){
		return hlDriveruserService.selectDriverUserMsgByPhone(phone);
	}
	/**   
	 * @Title: selectCompanyLinkManPhone   
	 * @Description: 根据手机号查询到人员的企业联系人电话    
	 * @param: @param phone
	 * @param: @return     
	 * @return: String      
	 * @throws   
	 */
	@RequestMapping("/selectCompanyLinkManPhone")
	@ResponseBody
	public String selectCompanyLinkManPhone(String phone){
		return hlDriveruserService.selectCompanyLinkManPhone(phone);
	}
	/**   
	 * @Title: beforeLogin   
	 * @Description: 驾驶员使用手机app登陆前的手机号核验和验证码获取   
	 * @param: @param phone
	 * @param: @return     
	 * @return: Map<Integer,String>      
	 * @throws   
	 */
	@RequestMapping(value="/beforeLogin")
	@ResponseBody
	public Map<Integer, String> beforeLogin(String phone){
		Map<Integer, String> map = new HashMap<>();
		//随机生成6位短信验证码
		String check_code = String.valueOf((int)((Math.random()*9+1)*100000));
		//通过手机号查找驾驶员的信息
		List<HlDriveruser> list = hlDriveruserService.selByPhone(phone);
		if (list.size()>0) {
			HlDriveruser driveruser = list.get(0);
			//条件内位验证码发送,如果为true则向数据库中插入验证码内容和时间(用于判断验证码在有效时间内可用)
			if (GetCheckCode.getCode(phone, check_code)) {
				//设置验证码内容
				driveruser.setCode(check_code);
				//设置验证码时间
				driveruser.setChecktime(String.valueOf(System.currentTimeMillis()));
				hlDriveruserService.updateHlDriveruser(driveruser);
				map.put(0, "验证码发送正确");
			}
			else{
				map.put(1, "验证码发送错误");
			}
		}
		else
			map.put(2, "手机号码错误");
		return map;
	}
	
	/**   
	 * @Title: dologin   
	 * @Description: 驾驶员使用手机号和验证码进行登陆   
	 * @param: @param phone
	 * @param: @param chenk_code
	 * @param: @return     
	 * @return: Map<Integer,String>      
	 * @throws   
	 */
	@RequestMapping(value="/doLogin",method=RequestMethod.GET)
	@ResponseBody
	public Map<Integer, String> dologin(String phone,String chenk_code){
		Map<Integer, String> map = new HashMap<>();
		//根据手机号取出驾驶员信息,信息内包括需要得到的验证码和验证码的有效时间
		List<HlDriveruser> list = hlDriveruserService.selByPhone(phone);
		if(list.size()>0){
			HlDriveruser driveruser = list.get(0);
			//当前系统时间
			long nowTime = System.currentTimeMillis();
			//当前时间与验证码的有限时间的时间差,精确到秒
			long cha = (nowTime-Long.parseLong(driveruser.getChecktime()))/1000;
			//如果输入的验证码等于数据库中查询到的....
			if(driveruser.getCode().equals(chenk_code)){
				//而且时间在300/60=5分钟内,则正确验证通过,其它情况不通过
				if(cha<=300){
					map.put(0, "成功");
				}
				else {
					map.put(1, "验证码超时");
				}
			}
			else
				map.put(2, "验证码错误");
		}else {
			map.put(3, "号码不存在");
		}
		return map;
	}
}
