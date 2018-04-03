package cm.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlGoodstype;
import cm.cn.po.Page;
import cm.cn.service.HlGoodsTypeService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 货物类型 
 * @author: 胡林   
 * @date: 2018年4月2日 下午8:13:45
 * @modify:  
 */
@Controller
public class HlGoodsTypeController {
	@Autowired
	private HlGoodsTypeService hlGoodsTypeService;
	/**   
	 * @Title: selectByGoodsTypeName   
	 * @Description: 根据货物类型名称查询货物类型     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param name
	 * @param: @return     
	 * @return: Page<HlGoodstype>      
	 * @throws   
	 */
	@RequestMapping("/selectGoodsTypeByName")
	@ResponseBody
	public Page<HlGoodstype> selectByGoodsTypeName(int current,int pageNum,String name){
		//String str = null ;
		//try {
		//	str = new String(name.getBytes("ISO-8859-1"),"UTF-8");
		//} catch (UnsupportedEncodingException e) {
		//	e.printStackTrace();
		//}
		List<HlGoodstype> list = hlGoodsTypeService.selectByName(name);
		Page<HlGoodstype> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: allGoodsType   
	 * @Description: 查询所有货物类型(分页)     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @return     
	 * @return: Page<HlGoodstype>      
	 * @throws   
	 */
	@RequestMapping("allGoodsType")
	@ResponseBody
	public Page<HlGoodstype> allGoodsType(int current,int pageNum){
		List<HlGoodstype> list = hlGoodsTypeService.selectAllGoodsType();
		Page<HlGoodstype> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: getSafeCardsList   
	 * @Description: 查询所有货物类型     
	 * @param: @return     
	 * @return: List<HlGoodstype>      
	 * @throws   
	 */
	@RequestMapping("/getGoodsTypeList")
	@ResponseBody
	public List<HlGoodstype> getSafeCardsList(){
		return hlGoodsTypeService.selectAllGoodsType();
	}
	/**   
	 * @Title: insertGoodsType   
	 * @Description: 插入货物类型     
	 * @param: @param hlGoodstype
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="insertGoodsType",method=RequestMethod.POST)
	@ResponseBody
	public int insertGoodsType(@RequestBody HlGoodstype hlGoodstype){
		System.out.println("++++++++++"+hlGoodstype.getId());
		hlGoodstype.setCratetime(String.valueOf(System.currentTimeMillis()));
		return hlGoodsTypeService.insertGoodsType(hlGoodstype);
	}
	/**   
	 * @Title: updateGoodsType   
	 * @Description: 更新货物类型     
	 * @param: @param hlGoodstype
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="updateGoodsType",method=RequestMethod.POST)
	@ResponseBody
	public int updateGoodsType(@RequestBody HlGoodstype hlGoodstype){
		return hlGoodsTypeService.updateGoodsType(hlGoodstype);
	}
	/**   
	 * @Title: delGoodsTypeBatch   
	 * @Description: 批量删除货物类型     
	 * @param: @param arrays
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="delGoodsTypeBatch")
	@ResponseBody
	public int delGoodsTypeBatch(int[] arrays){
		return hlGoodsTypeService.delGoodsTypeBatch(arrays);
	}
}
