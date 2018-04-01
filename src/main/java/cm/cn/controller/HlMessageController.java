package cm.cn.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlMessage;
import cm.cn.service.HlMessageService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description:通知信息专用controller
 * @author: 胡林   
 * @date: 2018年3月30日 下午3:17:29 
 */
@Controller
public class HlMessageController {
	
	@Autowired
	private HlMessageService hlMessageService;
	
	/**   
	 * @Title: sendMessage   
	 * @Description:用于发送通知信息    
	 * @param: @param hlMessage
	 * @param: @return      
	 * @return: Map<Integer,String>      
	 * @throws   
	 */
	@RequestMapping(value="/sendMessage")
	@ResponseBody
    public Map<Integer,String> sendMessage(HlMessage hlMessage){
    	Map<Integer, String> map = new HashMap<>();
    	 if(hlMessageService.sendMessage(hlMessage)){
    		 map.put(1, "发送成功");
    	 }else{
    		 map.put(0, "发送失败");
    	 }
    	 return map;
    }
}