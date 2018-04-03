package cm.cn.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cm.cn.po.HlWaybillDetail;
import cm.cn.po.HlWaybillSite;
import cm.cn.po.HlCarDrivingLog;
import cm.cn.po.HlCarrierInfo;
import cm.cn.po.HlCheckWaybill;
import cm.cn.po.HlSite;
import cm.cn.po.HlWaybill;
import cm.cn.po.HlWaybillAndSite;
import cm.cn.po.Page;
import cm.cn.service.HlCheckWaybillService;
import cm.cn.service.HlWaybillService;

/**   
 * Copyright © 2018 HAILIANG Info. Tech Ltd. All rights reserved.
 * @Description:电子运单信息 
 * @author: 胡林   
 * @date: 2018年4月2日 下午9:16:06
 * @modify:  
 */
@Controller
public class HlWaybillController {
	@Autowired
	private HlWaybillService hlWaybillService;
	@Autowired
	private HlCheckWaybillService hlCheckWaybillService;
	/**   
	 * @Title: allWaybill   
	 * @Description: 查询所有 电子运单     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @return     
	 * @return: Page<HlWaybillAndSite>      
	 * @throws   
	 */
	@RequestMapping(value="/allWaybill",method=RequestMethod.GET)
	@ResponseBody
	public Page<HlWaybillAndSite> allWaybill(int current,int pageNum){
		List<HlWaybill> list =hlWaybillService.selAllWaybill();
		List<HlWaybillAndSite> listWaybillAndSite = new ArrayList<HlWaybillAndSite>();
		for(int i=0;i<list.size();i++){
			HlWaybillAndSite hlWaybillAndSite = new HlWaybillAndSite();
			hlWaybillAndSite.setHlWaybill(list.get(i));
			Integer[] a = hlWaybillService.selectSiteId(list.get(i).getId());
			List<HlSite> listSite= hlWaybillService.selectSiteById(a);
			hlWaybillAndSite.setListSite(listSite);
			listWaybillAndSite.add(hlWaybillAndSite);
		}
		System.out.println(listWaybillAndSite);
		Page<HlWaybillAndSite> page = null;
		if(listWaybillAndSite.size()>0){
			page = new Page<>(current, pageNum,  listWaybillAndSite);
			return page;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: selWaybillByCompanyId   
	 * @Description: 根据企业id查询该企业的电子运单信息     
	 * @param: @param current
	 * @param: @param pageNum
	 * @param: @param companyId
	 * @param: @return     
	 * @return: Page<HlWaybillAndSite>      
	 * @throws   
	 */
	@RequestMapping(value="/selWaybillByCompanyId",method=RequestMethod.POST)
	@ResponseBody
	public Page<HlWaybillAndSite> selWaybillByCompanyId(int current,int pageNum,int companyId){
		List<HlWaybill> list = hlWaybillService.selWaybillByCompanyId(companyId);
		List<HlWaybillAndSite> listWaybillAndSite = new ArrayList<HlWaybillAndSite>();
		for(int i=0;i<list.size();i++){
			HlWaybillAndSite hlWaybillAndSite = new HlWaybillAndSite();
			hlWaybillAndSite.setHlWaybill(list.get(i));
			Integer[] a = hlWaybillService.selectSiteId(list.get(i).getId());
			List<HlSite> listSite= hlWaybillService.selectSiteById(a);
			hlWaybillAndSite.setListSite(listSite);
			listWaybillAndSite.add(hlWaybillAndSite);
		}
		System.out.println(listWaybillAndSite);
		Page<HlWaybillAndSite> page = null;
		if(listWaybillAndSite.size()>0){
			page = new Page<>(current, pageNum,  listWaybillAndSite);
			return page;
		}
		else {
			return null;
		}
	}
	/*@RequestMapping("/selWaybillByCompanyIdAndShipper")
	@ResponseBody
	public Page<HlWaybill> selWaybillByCompanyIdAndShipper(int current,int pageNum,int companyId,String Shipper){
		List<HlWaybill> list = hlWaybillService.selWaybillByCompanyIdAndShipper(companyId, Shipper);
		Page<HlWaybill> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum,  list);
			return page;
		}
		else {
			return null;
		}
	}*/
	/*@RequestMapping("/selWaybillByShipper")
	@ResponseBody
	public Page<HlWaybill> selWaybillByShipper(int current,int pageNum,String Shipper){
		List<HlWaybill> list = hlWaybillService.selWaybillByShipper(Shipper);
		Page<HlWaybill> page = null;
		if (list.size()>0) {
			page = new Page<>(current, pageNum,  list);
			return page;
		}
		else {
			return null;
		}
	}*/
	/**   
	 * @Title: selectGpsByPlateNo2   
	 * @Description: 查询电子运单一览     
	 * @param: @param plateNo
	 * @param: @param goods_id
	 * @param: @return     
	 * @return: HlWaybillDetail      
	 * @throws   
	 */
	@RequestMapping("/selWaybillDetail")
	@ResponseBody
	public HlWaybillDetail selectGpsByPlateNo2(String plateNo,int goods_id){
		HlWaybillDetail hlWaybillDetail = hlWaybillService.selWaybillDetail(plateNo,goods_id);
		if (hlWaybillDetail!=null) {
			return hlWaybillDetail;
		}
		else {
			return null;
		}
	}
	/**   
	 * @Title: insertWaybill   
	 * @Description: 插入电子运单     
	 * @param: @param hlWaybill
	 * @param: @param siteId
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/insertWaybill",method=RequestMethod.POST)
	@ResponseBody
	public int insertWaybill(HlWaybill hlWaybill,int[] siteId){
		Date date=new Date();
		hlWaybill.setCreateTime(date);
		hlWaybill.setUpdateTime(date);
		int i = hlWaybillService.insertWaybill(hlWaybill);
		for(i=0;i<siteId.length;i++){
			HlWaybillSite waybillSite = new HlWaybillSite();
			waybillSite.setWaybillId(hlWaybill.getId());
			waybillSite.setSiteId(siteId[i]);
			waybillSite.setCreateTime(date);
			waybillSite.setUpdateTime(date);
			hlWaybillService.insertWaybillAndSite(waybillSite);
		}
		HlCheckWaybill hlCheckWaybill = new HlCheckWaybill();
		hlCheckWaybill.setWaybillId(hlWaybill.getId());
		hlCheckWaybill.setWaybillState("订单创建");
		hlCheckWaybillService.insertHlCheckWaybill(hlCheckWaybill);
		return i;
	}
	/**   
	 * @Title: updateWaybill   
	 * @Description: 更新电子运单     
	 * @param: @param hlWaybill
	 * @param: @param siteId
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/updateWaybill",method=RequestMethod.POST)
	@ResponseBody
	public int updateWaybill(HlWaybill hlWaybill,int[] siteId){
		Date date=new Date();
		hlWaybillService.delWaybillSite(hlWaybill.getId());
		for(int i=0;i<siteId.length;i++){
			HlWaybillSite waybillSite = new HlWaybillSite();
			waybillSite.setWaybillId(hlWaybill.getId());
			waybillSite.setSiteId(siteId[i]);
			waybillSite.setCreateTime(date);
			waybillSite.setUpdateTime(date);
			hlWaybillService.insertWaybillAndSite(waybillSite);
		}
		hlWaybill.setUpdateTime(date);
		return hlWaybillService.updateWaybill(hlWaybill);
	}
	/**   
	 * @Title: delWaybillBatch   
	 * @Description: 批量删除电子运单     
	 * @param: @param arrays
	 * @param: @return     
	 * @return: int      
	 * @throws   
	 */
	@RequestMapping(value="/delWaybillBatch",method=RequestMethod.POST)
	@ResponseBody
	public int delWaybillBatch(int[] arrays){
		for(int i=0;i<arrays.length;i++){
			hlWaybillService.delWaybillSite(arrays[i]);
		}
		
		return hlWaybillService.delWaybillBatch(arrays);
	}
	/**   
	 * @Title: selectWayBillByDriverName   
	 * @Description: 根据驾驶员姓名查询电子运单(X)     
	 * @param: @param name
	 * @param: @return     
	 * @return: List<HlWaybill>      
	 * @throws   
	 */
	@RequestMapping(value="/selectWayBillByDriverName")
	@ResponseBody
	public List<HlWaybill> selectWayBillByDriverName(String name){
		//String str = null;
		//try {
		//	str = new String(name.getBytes("ISO-8859-1"),"UTF-8");
		//} catch (UnsupportedEncodingException e) {
		//	e.printStackTrace();
		//}
		return hlWaybillService.selectWayBillByDriverName(name);
	}
	/**   
	 * @Title: getWaybillBatchById   
	 * @Description: 根据电子运单id查询电子运单信息     
	 * @param: @param id
	 * @param: @return     
	 * @return: HlWaybill      
	 * @throws   
	 */
	@RequestMapping(value="/getWaybillBatchById")
	@ResponseBody
	public HlWaybill getWaybillBatchById(int id){
		return hlWaybillService.selectById(id);
	}
	/**   
	 * @Title: getCarrierByWaybillId   
	 * @Description: 通过电子运单id获取承运人基本信息   
	 * @param: @param waybillId
	 * @param: @return      
	 * @return: HlCarrierInfo      
	 * @throws   
	 */
	@RequestMapping(value="/getCarrierByWaybillId")
	@ResponseBody
	public HlCarrierInfo getCarrierByWaybillId(int waybillId){
		return hlWaybillService.getCarrierByWaybillId(waybillId);
	}
	/**   
	 * @Title: getCarDrivingLogByWaybillId   
	 * @Description:根据电子运单id获取危运车辆行车日志台账   
	 * @param: @param waybillId
	 * @param: @return      
	 * @return: HlCarDrivingLog      
	 * @throws   
	 */
	@RequestMapping(value="/getCarDrivingLogByWaybillId")
	@ResponseBody
	public HlCarDrivingLog getCarDrivingLogByWaybillId(int waybillId){
		return hlWaybillService.getCarDrivingLogByWaybillId(waybillId);
	}
}
