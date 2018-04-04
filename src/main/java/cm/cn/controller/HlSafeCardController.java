package cm.cn.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cm.cn.po.HlSafecard;
import cm.cn.po.Page;
import cm.cn.service.HlSafeCardService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 安全卡信息 
 * @author: 胡林   
 * @date: 2018年4月2日 下午8:57:51
 * @modify:  
 */
/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 
 * @author: 胡林   
 * @date: 2018年4月2日 下午9:01:56
 * @modify:  
 */
@Controller
public class HlSafeCardController {
	@Autowired
	private HlSafeCardService hlSafeCardService;
	/**   
	 * @Title: selectBySafeCardName   
	 * @Description:根据安全卡名字查询安全卡信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param safeCardName
	 * @param: @return     
	 * @return: Page<HlSafecard>      
	 * @throws   
	 */
	@RequestMapping("/selectBySafeCardName")
	@ResponseBody
	public Page<HlSafecard> selectBySafeCardName(int current,int pageNum,String safeCardName){
		
		List<HlSafecard> list = hlSafeCardService.selectBySafeCardName(safeCardName);
		Page<HlSafecard> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum, list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: allSafeCards   
	 * @Description:查询所有安全卡信息(分页)     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @return     
	 * @return: Page<HlSafecard>      
	 * @throws   
	 */
	@RequestMapping("/allSafeCards")
	@ResponseBody
	public Page<HlSafecard> allSafeCards(int current,int pageNum){
		List<HlSafecard> list = hlSafeCardService.selectAllSafeCard();
		Page<HlSafecard> page = null;
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
	 * @Description: 查询所有安全卡信息     
	 * @param: @return     
	 * @return: List<HlSafecard>      
	 * @throws   
	 */
	@RequestMapping("/getSafeCardsList")
	@ResponseBody
	public List<HlSafecard> getSafeCardsList(){
		return hlSafeCardService.selectAllSafeCard();
	}
	/**   
	 * @Title: upsafecardfiles   
	 * @Description:上传安全卡和说明书附件     
	 * @param: @param file
	 * @param: @return     
	 * @return: List<String>      
	 * @throws   
	 */
	@RequestMapping("/upfiles")
	@ResponseBody
	public List<String> upsafecardfiles(@RequestParam MultipartFile[] file){
		Calendar date=Calendar.getInstance();	
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String timeName=format.format(date.getTime());
		File safeCardDirectory=new File("D:/SafeCard/"+timeName);
		safeCardDirectory.mkdir();
		
		List<String> list = new ArrayList<>();
		 //获取文件名  
		String safeCardFileName = file[0].getOriginalFilename();
	    String introductionFileName = file[1].getOriginalFilename(); 
	    //文件扩展名
	    String newsafeCardFileName = "safeCard"+UUID.randomUUID()+safeCardFileName.substring(safeCardFileName.lastIndexOf("."));
	    String newintroductionFileName = "introduction"+UUID.randomUUID()+introductionFileName.substring(introductionFileName.lastIndexOf("."));  
	    // 存储的物理路径"D:\\"
 	 	String fileSavepath = "D:\\SafeCard\\";
		File safeCardFile = new File(fileSavepath+timeName+"/"+newsafeCardFileName);
		File introductionFile = new File(fileSavepath + timeName+"/"+ newintroductionFileName);
		
		list.add(safeCardFile.getAbsolutePath());
		list.add(introductionFile.getAbsolutePath());
		list.add(timeName+"/"+newsafeCardFileName);
		list.add(timeName+"/"+newintroductionFileName);
		try {
			file[0].transferTo(safeCardFile);
			file[1].transferTo(introductionFile);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**   
	 * @Title: insertSafeCard   
	 * @Description:插入安全卡信息     
	 * @param: @param record
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value= "/insertSafeCard",method=RequestMethod.POST)
	@ResponseBody
	public int insertSafeCard(@RequestBody HlSafecard record){
		record.setCreateTime(String.valueOf(System.currentTimeMillis()));
		return hlSafeCardService.insertSafeCard(record);
	}
	/**   
	 * @Title: updateSafeCard   
	 * @Description:更新安全卡信息     
	 * @param: @param record
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value= "/updateSafeCard",method=RequestMethod.POST)
	@ResponseBody
	public int updateSafeCard(@RequestBody HlSafecard record){
		return hlSafeCardService.updateSafeCardById(record);
	}
	/**   
	 * @Title: delSafeCardBatch   
	 * @Description:批量删除安全卡信息     
	 * @param: @param arrays
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value= "/delSafeCardBatch")
	@ResponseBody
	public int delSafeCardBatch(int[] arrays){
		//获取安全卡附件的存放路径
		String[] strAarray = hlSafeCardService.selSafeCardFilename(arrays);
		for(int i=0;i<strAarray.length;i++){
			//删除安全卡附件文件
			File f=new File("D:\\SafeCard\\"+strAarray[i]);
			f.delete();
		}
		//获取说明书附件的存放路径
		String[] strAarray2 = hlSafeCardService.selIntroFilename(arrays);
		for(int i=0;i<strAarray2.length;i++){
			//删除说明书附件的存放路径
			File f=new File("D:\\SafeCard\\"+strAarray2[i]);
			f.delete();
		}
		return hlSafeCardService.delSafeCardBatch(arrays);
	}
}
