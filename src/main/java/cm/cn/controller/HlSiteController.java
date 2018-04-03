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

import cm.cn.po.HlSite;
import cm.cn.po.Page;
import cm.cn.service.HlSiteService;
/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description: 站点信息 
 * @author: 胡林   
 * @date: 2018年4月2日 下午9:05:45
 * @modify:  
 */
@Controller
public class HlSiteController {
	@Autowired
	private HlSiteService hlSiteService;
	/**   
	 * @Title: SitePhoto   
	 * @Description: 上传站点负责人的图片信息     
	 * @param: @param file
	 * @param: @return     
	 * @return: List<String>      
	 * @throws   
	 */
	@RequestMapping("/upSitePhoto")
	@ResponseBody
	public List<String> SitePhoto(MultipartFile file){
		List<String> list = new ArrayList<>();
		//根据日期新建文件夹
		Calendar date=Calendar.getInstance();	
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMdd");
		String timeName=format.format(date.getTime());
		File directory=new File("D:/SitePhoto/"+timeName);
		directory.mkdir();
		String originFileName = file.getOriginalFilename();
		String newFileName = "SitePhoto"+UUID.randomUUID()+originFileName.substring(originFileName.lastIndexOf("."));
		String parentsPath = "D:\\SitePhoto\\"+timeName+"\\";
		File photo = new File(parentsPath+newFileName);
		try {
			file.transferTo(photo);
			list.add(timeName+"/"+newFileName);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**   
	 * @Title: insertSite   
	 * @Description: 插入站点     
	 * @param: @param hlSite
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/insertSite",method=RequestMethod.POST)
	@ResponseBody
    public int insertSite(HlSite hlSite){
    	return hlSiteService.insertSite(hlSite);
    }
	/**   
	 * @Title: selSiteByCompanyIdAndType   
	 * @Description:根据公司id和站点类型查询站点信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param companyId
	 * @param: @param type
	 * @param: @return     
	 * @return: Page<HlSite>      
	 * @throws   
	 */
	@RequestMapping(value="/selSiteByCompanyIdAndType",method=RequestMethod.POST)
	@ResponseBody
	public Page<HlSite> selSiteByCompanyIdAndType(int current,int pageNum,int companyId,String type){
		List<HlSite> list = hlSiteService.selSiteByCompanyIdAndType(companyId, type);
		Page<HlSite> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum,  list);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: delSiteBatch   
	 * @Description:批量删除站点信息     
	 * @param: @param arrays
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/delSiteBatch",method=RequestMethod.POST)
	@ResponseBody
	public int delSiteBatch(int[] arrays){
		return hlSiteService.delSiteBatch(arrays);
	}
	/**   
	 * @Title: updateSite   
	 * @Description:更新站点信息     
	 * @param: @param hlSite
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/updateSite",method=RequestMethod.POST)
	@ResponseBody
	public int updateSite(HlSite hlSite){
		Date date=new Date();
		hlSite.setUpdateTime(date);
		return hlSiteService.updateSite(hlSite);
	}

}
