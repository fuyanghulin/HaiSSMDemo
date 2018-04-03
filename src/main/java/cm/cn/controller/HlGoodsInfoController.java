package cm.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlGoodsinfo;
import cm.cn.po.Page;
import cm.cn.service.HlGoodsInfoService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 货物信息 
 * @author: 胡林   
 * @date: 2018年4月2日 下午8:01:38
 * @modify:  
 */
@Controller
public class HlGoodsInfoController {
	@Autowired
	private HlGoodsInfoService hlGoodsInfoService;
	/**   
	 * @Title: selectGoodsInfoByName   
	 * @Description: 根据货物名字查询货物信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param name
	 * @param: @return     
	 * @return: Page<HlGoodsinfo>      
	 * @throws   
	 */
	@RequestMapping("/selectGoodsInfoByName")
	@ResponseBody
	public Page<HlGoodsinfo> selectGoodsInfoByName(int current,int pageNum,String name){
		List<HlGoodsinfo> list = hlGoodsInfoService.selectGoodsInfoByName(name);
		Page<HlGoodsinfo> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: selectGoodsInfoByCompanyId   
	 * @Description: 根据公司id,查询该公司的所有货物信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param companyId
	 * @param: @return     
	 * @return: Page<HlGoodsinfo>      
	 * @throws   
	 */
	@RequestMapping("/selectGoodsInfoByCompanyId")
	@ResponseBody
	public Page<HlGoodsinfo> selectGoodsInfoByCompanyId(int current,int pageNum,int companyId){
		List<HlGoodsinfo> list = hlGoodsInfoService.selectGoodsInfoByCompanyId(companyId);
		Page<HlGoodsinfo> page = null;
		if(list.size()>0){
			page = new Page<>(current,pageNum,list);
			return page;
		}else{
			return null;
		}
	}
	/**   
	 * @Title: selectGoodsInfoByCompanyIdAndName   
	 * @Description: 根据公司id和货物名字查询货物信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param companyId
	 * @param: @param name
	 * @param: @return     
	 * @return: Page<HlGoodsinfo>      
	 * @throws   
	 */
	@RequestMapping("/selectGoodsInfoByCompanyIdAndName")
	@ResponseBody
	public Page<HlGoodsinfo> selectGoodsInfoByCompanyIdAndName(int current,int pageNum,int companyId,String name){
		List<HlGoodsinfo> list = hlGoodsInfoService.selectGoodsInfoByCompanyIdAndName(companyId, name);
		Page<HlGoodsinfo> page = null;
		if(list.size()>0){
			page = new Page<>(current,pageNum,list);
			return page;
		}else{
			return null;
		}
	}
	/**   
	 * @Title: selectGoodsInfoList   
	 * @Description: 查询所有货物信息     
	 * @param: @return     
	 * @return: List<HlGoodsinfo>      
	 * @throws   
	 */
	@RequestMapping("/selectGoodsInfoList")
	@ResponseBody
	public List<HlGoodsinfo> selectGoodsInfoList(){
		return hlGoodsInfoService.selectAllGoodsInfo();
	}
	/**   
	 * @Title: allGoodsInfo   
	 * @Description:查询所有货物信息(分页)     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @return     
	 * @return: Page<HlGoodsinfo>      
	 * @throws   
	 */
	@RequestMapping("allGoodsInfo")
	@ResponseBody
	public Page<HlGoodsinfo> allGoodsInfo(int current,int pageNum){
		List<HlGoodsinfo> list =hlGoodsInfoService.selectAllGoodsInfo();
		Page<HlGoodsinfo> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: insertGoodsInfo   
	 * @Description: 插入货物信息     
	 * @param: @param hlGoodsinfo
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="insertGoodsInfo",method=RequestMethod.POST)
	@ResponseBody
	public int insertGoodsInfo(@RequestBody HlGoodsinfo hlGoodsinfo){
		return hlGoodsInfoService.insertHlGoodsinfo(hlGoodsinfo);
	}
	/**   
	 * @Title: updateGoodsInfo   
	 * @Description: 更新货物信息     
	 * @param: @param hlGoodsinfo
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="updateGoodsInfo",method=RequestMethod.POST)
	@ResponseBody
	public int updateGoodsInfo(@RequestBody HlGoodsinfo hlGoodsinfo){
		return hlGoodsInfoService.updateHlGoodsinfo(hlGoodsinfo);
	}
	/**   
	 * @Title: delGoodsInfoBatch   
	 * @Description:批量删除获取信息     
	 * @param: @param arrays
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="delGoodsInfoBatch")
	@ResponseBody
	public int delGoodsInfoBatch(int[] arrays){
		return hlGoodsInfoService.delHlGoodsinfo(arrays);
	}
}
