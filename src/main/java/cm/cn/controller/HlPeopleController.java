package cm.cn.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cm.cn.po.HlDriveruser;
import cm.cn.po.HlPeople;
import cm.cn.po.Page;
import cm.cn.service.HlDriveruserService;
import cm.cn.service.HlPeopleService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 人员信息 
 * @author: 胡林   
 * @date: 2018年4月2日 下午8:31:03
 * @modify:  
 */
@Controller
public class HlPeopleController {
	@Autowired
	private HlPeopleService hlPeopleService;
	@Autowired
	private HlDriveruserService hlDriveruserService;
	/**   
	 * @Title: selectPeopleByCompanyId   
	 * @Description: 根据公司id查询当前企业的人员信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param id
	 * @param: @return     
	 * @return: Page<HlPeople>      
	 * @throws   
	 */
	@RequestMapping("/selectPeopleByCompanyId")
	@ResponseBody
	public Page<HlPeople> selectPeopleByCompanyId(int current,int pageNum,int id){
		List<HlPeople> list = hlPeopleService.selectByCompanyId(id);
		Page<HlPeople> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: selectPeopleById   
	 * @Description: 根据人员id查询人员信息     
	 * @param: @param id
	 * @param: @return     
	 * @return: HlPeople      
	 * @throws   
	 */
	@RequestMapping("/selectPeopleById")
	@ResponseBody
	public HlPeople selectPeopleById(int id){
		HlPeople hlPeople = hlPeopleService.selectPeopleById(id);
		if(hlPeople!=null){
			return hlPeople;
		}else{
			return null;
		}
	}
	/**   
	 * @Title: selectPeopleByName   
	 * @Description:根据人员姓名模糊查询人员信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param name
	 * @param: @return     
	 * @return: Page<HlPeople>      
	 * @throws   
	 */
	@RequestMapping("/selectPeopleByName")
	@ResponseBody
	public Page<HlPeople> selectPeopleByName(int current,int pageNum,String name){
		//String str = null ;
		//try {
		//	str = new String(name.getBytes("ISO-8859-1"),"UTF-8");
		//} catch (UnsupportedEncodingException e) {
			//e.printStackTrace();
		//}
		List<HlPeople> list = hlPeopleService.selectPeopleByName(name);
		Page<HlPeople> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: selectPeopleByCompanyIdAndName   
	 * @Description: 根据公司id和名字,模糊查询该企业该人员的信息      
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param companyId
	 * @param: @param name
	 * @param: @return     
	 * @return: Page<HlPeople>      
	 * @throws   
	 */
	@RequestMapping("/selectPeopleByCompanyIdAndName")
	@ResponseBody
	public Page<HlPeople> selectPeopleByCompanyIdAndName(int current,int pageNum,int companyId,String name){
		List<HlPeople> list = hlPeopleService.selectPeopleByCompanyIdAndName(companyId, name);
		Page<HlPeople> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: upPicture   
	 * @Description: 上传人员信息图片     
	 * @param: @param file
	 * @param: @return     
	 * @return: List<String>      
	 * @throws   
	 */
	@RequestMapping("/upPicture")
	@ResponseBody
	public List<String> upPicture(MultipartFile file){
		List<String> list = new ArrayList<>();
		String originFileName = file.getOriginalFilename();
		String newFileName = "photo"+UUID.randomUUID()+originFileName.substring(originFileName.lastIndexOf("."));
		String parentsPath = "D:\\hailiangpic\\";
		File photo = new File(parentsPath+newFileName);
		try {
			file.transferTo(photo);
			list.add(newFileName);
			list.add(parentsPath+newFileName);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**   
	 * @Title: allPeople   
	 * @Description: 查询所有人员信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @return     
	 * @return: Page<HlPeople>      
	 * @throws   
	 */
	@RequestMapping("/allPeople")
	@ResponseBody
	public Page<HlPeople> allPeople(int current,int pageNum){
		List<HlPeople> list =hlPeopleService.selAllPeople();
		Page<HlPeople> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum,  list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: insertPeople   
	 * @Description: 插入人员信息     
	 * @param: @param hlPeople
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/insertPeople",method=RequestMethod.POST)
	@ResponseBody
	public int insertPeople(HlPeople hlPeople){
		System.out.println("+++++++++++++++++++++++"+hlPeople.getBirthday());
		HlDriveruser hlDriveruser = new HlDriveruser();
		int num = hlPeopleService.insertPeople(hlPeople);
		if (num>0&&hlPeople.getPeopleType().equals("1")) {
			hlDriveruser.setHlPeopleId(hlPeople.getId());
			System.out.println(hlPeople.getId());
			hlDriveruser.setPhone(hlPeople.getPhone());
			hlDriveruser.setName(hlPeople.getName());
			hlDriveruserService.insertHlDriveruser(hlDriveruser);
		}
		return num;
	}
	/**   
	 * @Title: updatePeople   
	 * @Description: 更新人员信息     
	 * @param: @param hlPeople
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/updatePeople",method=RequestMethod.POST)
	@ResponseBody
	public int updatePeople(HlPeople hlPeople){
		return hlPeopleService.updatePeople(hlPeople);
	}
	/**   
	 * @Title: delPeopleBatch   
	 * @Description: 批量删除人员信息     
	 * @param: @param arrays
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/delPeopleBatch")
	@ResponseBody
	public int delPeopleBatch(int[] arrays){
		hlDriveruserService.delHlDriveruser(arrays);
		//通过人员id获取人员图片存放路径
		String[] strAarray = hlPeopleService.selPictureNameById(arrays);
		for(int i=0;i<strAarray.length;i++){
			//删除人员图片的文件
			File f=new File("D:\\hailiangpic\\"+strAarray[i]);
			f.delete();
		}
		return hlPeopleService.delPeopleBatch(arrays);
	}
}
