package cm.cn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlCompany;
import cm.cn.po.Page;
import cm.cn.service.HlCompanyService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 用于对于企业信息的管理  
 * @author: 胡林   
 * @date: 2018年4月2日 下午7:33:20
 * @modify:  
 */
@Controller
public class HlCompanyController {
	@Autowired
	private HlCompanyService hlCompanyService;
	/**   
	 * @Title: selectCompanyByName   
	 * @Description: 根据公司的名称查询公司信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param name
	 * @param: @return     
	 * @return: Page<HlCompany>      
	 * @throws   
	 */
	@RequestMapping("/selectCompanyByName")
	@ResponseBody
	public Page<HlCompany> selectCompanyByName(int current,int pageNum,String name){
		//String str = null ;
		//try {
		//	str = new String(name.getBytes("ISO-8859-1"),"UTF-8");
		//} catch (UnsupportedEncodingException e) {
		//	e.printStackTrace();
		//}
		List<HlCompany> list = hlCompanyService.selectCompanyByName(name);
		Page<HlCompany> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: allCompany   
	 * @Description: 查询所有企业信息(分页)    
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @return     
	 * @return: Page<HlCompany>      
	 * @throws   
	 */
	@RequestMapping("/allCompany")
	@ResponseBody
	public Page<HlCompany> allCompany(int current,int pageNum){
		List<HlCompany> list =hlCompanyService.selAllCompays();
		Page<HlCompany> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum,  list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: getCompanyList   
	 * @Description: 查询所有企业信息    
	 * @param: @return     
	 * @return: List<HlCompany>      
	 * @throws   
	 */
	@RequestMapping("/getCompanyList")
	@ResponseBody
	public List<HlCompany> getCompanyList(){
		return hlCompanyService.selAllCompays();
	}
	/**   
	 * @Title: insertCompany   
	 * @Description: 插入企业信息    
	 * @param: @param hlCompany
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/insertCompany",method=RequestMethod.POST)
	@ResponseBody
	public int insertCompany(HlCompany hlCompany){
		return hlCompanyService.insertCompany(hlCompany);
	}
	/**   
	 * @Title: updateCompany   
	 * @Description: 更新企业信息    
	 * @param: @param hlCompany
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/updateCompany",method=RequestMethod.POST)
	@ResponseBody
	public int updateCompany(HlCompany hlCompany){
		return hlCompanyService.updateCompany(hlCompany);
	}
	/**   
	 * @Title: delCompanyBatch   
	 * @Description: 批量删除企业    
	 * @param: @param arrays
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/delCompanyBatch")
	@ResponseBody
	public int delCompanyBatch(int[] arrays){
		return hlCompanyService.delCompanyBatch(arrays);
	}
	/**   
	 * @Title: selectCompanyByID   
	 * @Description: 根据公司id查询公司信息    
	 * @param: @param id
	 * @param: @return     
	 * @return: HlCompany      
	 * @throws   
	 */
	@RequestMapping("/selectCompanyByID")
	@ResponseBody
	public HlCompany selectCompanyByID(int id){
		return hlCompanyService.selectCompanyById(id);
	}
}
