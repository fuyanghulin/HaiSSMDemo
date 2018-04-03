package cm.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlCheckWaybill;
import cm.cn.service.HlCheckWaybillService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 用于电子运单检查的操作 
 * @author: 胡林   
 * @date: 2018年4月2日 下午7:29:32
 * @modify:  
 */
@Controller
public class HlCheckWaybillController {
	@Autowired
	private HlCheckWaybillService hlCheckWaybillService;
	
	/**   
	 * @Title: updateHlCheckWaybill   
	 * @Description: 更新电子运单检查    
	 * @param: @param hlCheckWaybill
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/updateHlCheckWaybill",method=RequestMethod.POST)
	@ResponseBody
	public int updateHlCheckWaybill(HlCheckWaybill hlCheckWaybill){
		return hlCheckWaybillService.updateHlCheckWaybill(hlCheckWaybill);
	}
	
	/**   
	 * @Title: selectHlCheckWaybillBywaybillId   
	 * @Description: 根据电子运单id查看电子运单检查的信息    
	 * @param: @param waybillId
	 * @param: @return     
	 * @return: List<HlCheckWaybill>      
	 * @throws   
	 */
	@RequestMapping("/selectHlCheckWaybillBywaybillId")
	@ResponseBody
	public List<HlCheckWaybill> selectHlCheckWaybillBywaybillId(int waybillId){
		return hlCheckWaybillService.selectBywaybillId(waybillId);
	}
}
