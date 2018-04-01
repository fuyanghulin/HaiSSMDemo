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

@Controller
public class HlCarInfoController {
	@Autowired
	private HlCarInfoService HlCarinfoService;
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
	@RequestMapping("/getCarInfoList")
	@ResponseBody
	public List<HlCarinfo> getSafeCardsList(){
		return HlCarinfoService.selAllCar();
	}
	@RequestMapping(value="/insertCarInfo",method=RequestMethod.POST)
	@ResponseBody
	public int insertCarInfo(HlCarinfo HlCarinfo){
		return HlCarinfoService.insertCar(HlCarinfo);
	}
	@RequestMapping(value="/updateCarInfo",method=RequestMethod.POST)
	@ResponseBody
	public int updateCarInfo(HlCarinfo HlCarinfo){
		return HlCarinfoService.updateCar(HlCarinfo);
	}
	@RequestMapping(value="/delCarInfoBatch")
	@ResponseBody
	public int delCarInfoBatch(int[] arrays){
		return HlCarinfoService.delCarBatch(arrays);
	}
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
		String originFileName = file.getOriginalFilename();
		String newFileName = "CarAttach"+UUID.randomUUID()+originFileName.substring(originFileName.lastIndexOf("."));
		String parentsPath = "D:\\CarAttach\\"+timeName+"\\";
		File photo = new File(parentsPath+newFileName);
		try {
			file.transferTo(photo);
			list.add(timeName+"/"+newFileName);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value="/insertCarAttach",method=RequestMethod.POST)
	@ResponseBody
	public int insertCarAttach(HlCarAttach hlCarAttach){
		Date date=new Date();    
		hlCarAttach.setCreateTime(date);
		hlCarAttach.setUpdateTime(date);
		return HlCarinfoService.insertCarAttach(hlCarAttach);
	}
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
	@RequestMapping(value="/delCarAttach",method=RequestMethod.POST)
	@ResponseBody
	public int delCarAttach(int[] arrays){
		return HlCarinfoService.delCarAttachBatch(arrays);
	}
	@RequestMapping(value="/updateCarAttach",method=RequestMethod.POST)
	@ResponseBody
	public int updateCarAttacBatch(HlCarAttach hlCarAttach){
		Date date=new Date();    
		hlCarAttach.setUpdateTime(date);
		return HlCarinfoService.updateCarAttach(hlCarAttach);
	}
}
