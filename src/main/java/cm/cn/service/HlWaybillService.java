package cm.cn.service;

import java.util.List;

import cm.cn.po.HlWaybillDetail;
import cm.cn.po.HlWaybillSite;
import cm.cn.po.HlCarDrivingLog;
import cm.cn.po.HlCarrierInfo;
import cm.cn.po.HlSite;
import cm.cn.po.HlWaybill;

public interface HlWaybillService {
	//查找所有订单
	public List<HlWaybill> selAllWaybill();
	//根据公司查找订单
	public List<HlWaybill> selWaybillByCompanyId(int companyId);
	/*//根据公司id,电子运单托运方模糊查询公司电子运单
	public List<HlWaybill> selWaybillByCompanyIdAndShipper(int companyId,String Shipper);*/
	/*//根据托运方查找订单
	public List<HlWaybill> selWaybillByShipper(String Shipper);*/
	//根据车辆信息和货物id对电子运单信息进行整合
	public HlWaybillDetail selWaybillDetail(String plateNo,int goods_id);
	//添加订单
	public int insertWaybill(HlWaybill hlWaybill);
	//批量删除订单
	public int delWaybillBatch(int[] arrays);
	//更改订单
	public int updateWaybill(HlWaybill hlWaybill);
	//根据驾驶员姓名查询他对应的订单
	public List<HlWaybill> selectWayBillByDriverName(String name);
	//根据 ID 得到运单详细信息
	public HlWaybill selectById(int id);
	//插入站点和电子运单关联
	public int insertWaybillAndSite(HlWaybillSite waybillSite);
	//根据电子运单id查询站点id
	public Integer[] selectSiteId(int waybillId);
	//根据站点号查询站点信息
	public List<HlSite> selectSiteById(Integer[] arrays);
	//根据电子运单id删除电子运单+
	public int delWaybillSite(int waybillId);
	//根据电子运单id获取承运人基本信息
	public HlCarrierInfo getCarrierByWaybillId(int waybillId);
	//根据电子运单id获取危运车辆行车日志
	public HlCarDrivingLog getCarDrivingLogByWaybillId(int waybillId);
	
}
