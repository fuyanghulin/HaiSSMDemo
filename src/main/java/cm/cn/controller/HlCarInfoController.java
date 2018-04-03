package cm.cn.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cm.cn.po.HlCarAttach;
import cm.cn.po.HlCarinfo;
import cm.cn.po.Page;
import cm.cn.service.HlCarInfoService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 车辆信息 
 * @author: 胡林   
 * @date: 2018年4月2日 下午4:13:21
 * @modify:  
 */
@Controller
public class HlCarInfoController {
	@Autowired
	private HlCarInfoService HlCarinfoService;
	
	/**   
	 * @Title: selectCarByCarLicense   
	 * @Description: 根据车牌号查找车辆信息   
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param carLicense
	 * @param: @return     
	 * @return: Page<HlCarinfo>      
	 * @throws   
	 */
	@RequestMapping("/selectCarByCarLicense")
	@ResponseBody
	public Page<HlCarinfo> selectCarByCarLicense(int current,int pageNum,String carLicense){
	
		List<HlCarinfo> list = HlCarinfoService.selectByCarLicense(carLicense);
		Page<HlCarinfo> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: allCarInfo   
	 * @Description: 查询所有企业所有车辆信息   
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @return     
	 * @return: Page<HlCarinfo>      
	 * @throws   
	 */
	@RequestMapping("/allCarInfo")
	@ResponseBody
	public Page<HlCarinfo> allCarInfo(int current,int pageNum){
		List<HlCarinfo> list =HlCarinfoService.selAllCar();
		Page<HlCarinfo> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum,  list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: selectCarInfoByCompanyName   
	 * @Description: 根据企业名字查询该企业的车辆信息    
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param company_name
	 * @param: @return     
	 * @return: Page<HlCarinfo>      
	 * @throws   
	 */
	@RequestMapping("/selectCarInfoByCompanyName")
	@ResponseBody
	public Page<HlCarinfo> selectCarInfoByCompanyName(int current,int pageNum,String company_name){
		//String str = null ;
		//try {
		//	str = new String(company_name.getBytes("ISO-8859-1"),"UTF-8");
		//} catch (UnsupportedEncodingException e) {
		//	e.printStackTrace();
		//}
		List<HlCarinfo> list = HlCarinfoService.selectByCompanyName(company_name);
		Page<HlCarinfo> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum,  list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: selectCarInfoByCompanyId   
	 * @Description: 根据公司id,查询该企业的所有车辆信息    
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param company_id
	 * @param: @return     
	 * @return: Page<HlCarinfo>      
	 * @throws   
	 */
	@RequestMapping("/selectCarInfoByCompanyId")
	@ResponseBody
	public Page<HlCarinfo> selectCarInfoByCompanyId(int current,int pageNum,int company_id){
		List<HlCarinfo> list = HlCarinfoService.selectByCompanyId(company_id);
		Page<HlCarinfo> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: selectCarInfoByCompanyIdAndCarNum   
	 * @Description: 根据车牌号查询当前企业的这辆车的信息    
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param companyId
	 * @param: @param carNum
	 * @param: @return     
	 * @return: Page<HlCarinfo>      
	 * @throws   
	 */
	@RequestMapping("/selectCarInfoByCompanyIdAndCarNum")
	@ResponseBody
	public Page<HlCarinfo> selectCarInfoByCompanyIdAndCarNum(int current,int pageNum,int companyId,String carNum){
		List<HlCarinfo> list = HlCarinfoService.selectCarInfoByCompanyIdAndCarNum(companyId, carNum);
		Page<HlCarinfo> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: getCarInfoList   
	 * @Description: 获取所有车辆信息(可能是僵尸接口)   
	 * @param: @return     
	 * @return: List<HlCarinfo>      
	 * @throws   
	 */
	@RequestMapping("/getCarInfoList")
	@ResponseBody
	public List<HlCarinfo> getCarInfoList(){
		return HlCarinfoService.selAllCar();
	}
	/**   
	 * @Title: insertCarInfo   
	 * @Description: 插入车辆信息    
	 * @param: @param HlCarinfo
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/insertCarInfo",method=RequestMethod.POST)
	@ResponseBody
	public int insertCarInfo(HlCarinfo HlCarinfo){
		return HlCarinfoService.insertCar(HlCarinfo);
	}
	/**   
	 * @Title: updateCarInfo   
 	 * @Description: 更新车辆信息    
	 * @param: @param HlCarinfo
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/updateCarInfo",method=RequestMethod.POST)
	@ResponseBody
	public int updateCarInfo(HlCarinfo HlCarinfo){
		return HlCarinfoService.updateCar(HlCarinfo);
	}
	/**   
	 * @Title: delCarInfoBatch   
	 * @Description: 批量删除车辆信息    
	 * @param: @param arrays
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/delCarInfoBatch")
	@ResponseBody
	public int delCarInfoBatch(int[] arrays){
		return HlCarinfoService.delCarBatch(arrays);
	}
	/**   
	 * @Title: upCarAttach   
	 * @Description: 上传车档信息的附件    
	 * @param: @param file
	 * @param: @return     
	 * @return: List<String>      
	 * @throws   
	 */
	@RequestMapping("/upCarAttach")
	@ResponseBody
	public List<String> upCarAttach(MultipartFile file){
		List<String> list = new ArrayList<>();
		//根据日期新建文件夹
		Calendar date=Calendar.getInstance();	
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String timeName=format.format(date.getTime());
		File directory=new File("D:/CarAttach/"+timeName);
		directory.mkdir();
		
		//获取上传文件的文件名
		String originFileName = file.getOriginalFilename();
		//取出文件的后缀,拼接新的文件名称
		String newFileName = "CarAttach"+UUID.randomUUID()+originFileName.substring(originFileName.lastIndexOf("."));
		//文件存放目录
		String parentsPath = "D:\\CarAttach\\"+timeName+"\\";
		File photo = new File(parentsPath+newFileName);
		try {
			//存入照片
			file.transferTo(photo);
			list.add(timeName+"/"+newFileName);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**   
	 * @Title: insertCarAttach   
	 * @Description: 插入车档信息    
	 * @param: @param hlCarAttach
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/insertCarAttach",method=RequestMethod.POST)
	@ResponseBody
	public int insertCarAttach(HlCarAttach hlCarAttach){
		Date date=new Date();    
		hlCarAttach.setCreateTime(date);
		hlCarAttach.setUpdateTime(date);
		return HlCarinfoService.insertCarAttach(hlCarAttach);
	}
	/**   
	 * @Title: selectCarAttach   
	 * @Description: 根据车牌号查询车档信息    
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param carNum
	 * @param: @return     
	 * @return: Page<HlCarAttach>      
	 * @throws   
	 */
	@RequestMapping(value="/selectCarAttach",method=RequestMethod.POST)
	@ResponseBody
	public Page<HlCarAttach> selectCarAttach(int current,int pageNum,String carNum){
		List<HlCarAttach> list = HlCarinfoService.selectCarAttach(carNum);
		Page<HlCarAttach> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum,  list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: delCarAttach   
	 * @Description: 批量删除车档信息    
	 * @param: @param arrays
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/delCarAttach",method=RequestMethod.POST)
	@ResponseBody
	public int delCarAttach(int[] arrays){
		return HlCarinfoService.delCarAttachBatch(arrays);
	}
	/**   
	 * @Title: updateCarAttacBatch   
	 * @Description: 更新车辆信息的车档信息       
	 * @param: @param hlCarAttach
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/updateCarAttach",method=RequestMethod.POST)
	@ResponseBody
	public int updateCarAttacBatch(HlCarAttach hlCarAttach){
		Date date=new Date();    
		hlCarAttach.setUpdateTime(date);
		return HlCarinfoService.updateCarAttach(hlCarAttach);
	}
}
