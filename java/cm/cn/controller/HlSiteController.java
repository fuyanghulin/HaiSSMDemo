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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cm.cn.po.HlSite;
import cm.cn.po.Page;
import cm.cn.service.HlSiteService;
@Controller
public class HlSiteController {
	@Autowired
	private HlSiteService hlSiteService;
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
	@RequestMapping(value="/insertSite",method=RequestMethod.POST)
	@ResponseBody
    public int insertSite(HlSite hlSite){
    	return hlSiteService.insertSite(hlSite);
    }
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
	@RequestMapping(value="/delSiteBatch",method=RequestMethod.POST)
	@ResponseBody
	public int delSiteBatch(int[] arrays){
		return hlSiteService.delSiteBatch(arrays);
	}
	@RequestMapping(value="/updateSite",method=RequestMethod.POST)
	@ResponseBody
	public int updateSite(HlSite hlSite){
		return hlSiteService.updateSite(hlSite);
	}

}
