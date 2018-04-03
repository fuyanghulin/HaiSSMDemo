package cm.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlCheckText;
import cm.cn.po.HlDriverlogText;
import cm.cn.service.HlCheckTextService;
import cm.cn.service.HlDriverLogTextService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 电子运单检查信息 
 * @author: 胡林   
 * @date: 2018年4月2日 下午9:10:02
 * @modify:  
 */
@Controller
public class HlTextSelectController {
	@Autowired
	private HlCheckTextService hlCheckTextService;
	@Autowired
	private HlDriverLogTextService hlDriverLogTextService;
	/**   
	 * @Title: allDriverLogText   
	 * @Description:列出所有行车日志内容     
	 * @param: @return     
	 * @return: List<HlDriverlogText>      
	 * @throws   
	 */
	@RequestMapping("/allDriverLogText")
	@ResponseBody
	public List<HlDriverlogText> allDriverLogText(){
		return hlDriverLogTextService.selectAllDriverlogText();
	}
	/**   
	 * @Title: allCheckText   
	 * @Description:列出所有车况检查的内容     
	 * @param: @return     
	 * @return: List<HlCheckText>      
	 * @throws   
	 */
	@RequestMapping("/allCheckText")
	@ResponseBody
	public List<HlCheckText> allCheckText(){
		return hlCheckTextService.selectAll();
	}
	/**   
	 * @Title: selDriverLogTextArray   
	 * @Description:给出行车日志的序列id,查询出对应的行车日志信息     
	 * @param: @param array
	 * @param: @return     
	 * @return: List<HlDriverlogText>      
	 * @throws   
	 */
	@RequestMapping("/selDriverLogTextArray")
	@ResponseBody
	public List<HlDriverlogText> selDriverLogTextArray(int[] array){
		return hlDriverLogTextService.selectDriverLogsById(array);
	}
	/**   
	 * @Title: allWaybill   
	 * @Description: 给出车况检查的序列id,查询出对应的车况检查信息    
	 * @param: @param array
	 * @param: @return     
	 * @return: List<HlCheckText>      
	 * @throws   
	 */
	@RequestMapping("/selCheckTextArray")
	@ResponseBody
	public List<HlCheckText> allWaybill(int[] array){
		return hlCheckTextService.selectHlCheckTextByIdArrays(array);
	}
}
