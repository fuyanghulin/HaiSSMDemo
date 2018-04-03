package cm.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlPeixun;
import cm.cn.po.Page;
import cm.cn.service.HlPeixunService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description:培训信息 
 * @author: 胡林   
 * @date: 2018年4月2日 下午8:21:50
 * @modify:  
 */
@Controller 
public class HlPeixunController {
	@Autowired
	private HlPeixunService hlPeixunService;
	/**   
	 * @Title: selectByGoodsTypeName   
	 * @Description:查看所有培训信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @return     
	 * @return: Page<HlPeixun>      
	 * @throws   
	 */
	@RequestMapping("/selectPeixu")
	@ResponseBody
	public Page<HlPeixun> selectByGoodsTypeName(int current,int pageNum){
		List<HlPeixun> list = hlPeixunService.selectAllPeixun();
		Page<HlPeixun> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: selectPeixuByName   
	 * @Description:根据培训名称查询培训信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param name
	 * @param: @return     
	 * @return: Page<HlPeixun>      
	 * @throws   
	 */
	@RequestMapping("/selectPeixuByName")
	@ResponseBody
	public Page<HlPeixun> selectPeixuByName(int current,int pageNum,String name){
		List<HlPeixun> list = hlPeixunService.selectPeixunByName(name);
		Page<HlPeixun> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: insertPeixu   
	 * @Description:插入培训信息     
	 * @param: @param hlPeixun
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/insertPeixu",method=RequestMethod.POST)
	@ResponseBody
	public int insertPeixu(HlPeixun hlPeixun){
		return hlPeixunService.addPeixun(hlPeixun);
	}
	/**   
	 * @Title: delPeixu   
	 * @Description:批量删除培训信息     
	 * @param: @param array
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/delPeixu")
	@ResponseBody
	public int delPeixu(int[] array){
		return hlPeixunService.delPeixun(array);
	}
}
