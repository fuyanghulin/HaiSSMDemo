package cm.cn.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.cn.datasource.CustomerContextHolder;
import cm.cn.mapper.HlCarinfoMapper;
import cm.cn.mapper.HlCheckWaybillMapper;
import cm.cn.mapper.HlGoodsinfoMapper;
import cm.cn.mapper.HlGoodstypeMapper;
import cm.cn.mapper.HlPeopleMapper;
import cm.cn.mapper.HlSafecardMapper;
import cm.cn.mapper.HlWaybillMapper;
import cm.cn.mapper.HlWaybillSiteMapper;
import cm.cn.mapper.MyCarInfoMapper;
import cm.cn.mapper.MyGoodsInfoMapper;
import cm.cn.mapper.MyGpsMapper;
import cm.cn.mapper.MySiteMapper;
import cm.cn.mapper.MyWaybillMapper;
import cm.cn.mapper.MyWaybillSiteMapper;
import cm.cn.po.HlWaybillDetail;
import cm.cn.po.Gpsinfo;
import cm.cn.po.HlCarDrivingLog;
import cm.cn.po.HlCarinfo;
import cm.cn.po.HlCarinfoExample;
import cm.cn.po.HlCarrierInfo;
import cm.cn.po.HlCheckWaybill;
import cm.cn.po.HlCheckWaybillExample;
import cm.cn.po.HlGoodsinfo;
import cm.cn.po.HlGoodstype;
import cm.cn.po.HlPeople;
import cm.cn.po.HlSite;
import cm.cn.po.HlWaybill;
import cm.cn.po.HlWaybillExample;
import cm.cn.po.HlWaybillSite;
import cm.cn.service.HlWaybillService;
import cm.cn.util.GpsStatus;
@Service
public class HlWaybillServiceImpl implements HlWaybillService {
	@Autowired
	private MyWaybillMapper myWaybillMapper;
	@Autowired
	private HlWaybillMapper hlWaybillMapper;
	@Autowired
    private MyGpsMapper myGpsMapper;
    @Autowired 
    private MyCarInfoMapper myCarInfoMapper;
    @Autowired
    private HlPeopleMapper hlPeopleMapper;
    @Autowired
    private MyGoodsInfoMapper myGoodsInfoMapper;
    @Autowired
    private HlGoodstypeMapper hlGoodstypeMapper;
    @Autowired
    private HlGoodsinfoMapper hlGoodsInfoMapper;
    @Autowired
    private HlSafecardMapper hlSafecardMapper;
    @Autowired
    private HlCarinfoMapper hlCarinfoMapper;
    @Autowired
    private HlWaybillSiteMapper hlWaybillSiteMapper;
    @Autowired 
    private MySiteMapper mySiteMapper;
    @Autowired
    private MyWaybillSiteMapper myWaybillSiteMapper;
    @Autowired
    private HlCheckWaybillMapper hlCheckWaybillMapper;
	@Override
	public List<HlWaybill> selAllWaybill() {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlWaybillExample example = new HlWaybillExample();
		return hlWaybillMapper.selectByExample(example);
	}
	@Override
	public List<HlWaybill> selWaybillByCompanyId(int companyId) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlWaybillExample example = new HlWaybillExample();
		HlWaybillExample.Criteria criteria = example.createCriteria();
		criteria.andCompanyIdEqualTo(companyId);
		return hlWaybillMapper.selectByExample(example);
	}
	/*@Override
	public List<HlWaybill> selWaybillByCompanyIdAndShipper(int companyId, String Shipper) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlWaybillExample example = new HlWaybillExample();
		HlWaybillExample.Criteria criteria = example.createCriteria();
		criteria.andCompanyIdEqualTo(companyId);
		criteria.andShipperLike("%"+Shipper+"%");
		return hlWaybillMapper.selectByExample(example);
	}*/
	@Override
	public HlWaybillDetail selWaybillDetail(String plateNo, int goods_id) {
		Gpsinfo gpsinfo = new Gpsinfo();
		HlWaybillDetail hlWaybillDetail = new HlWaybillDetail();
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GPS);
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyyMMdd");
		String tableName="gpsinfo"+format.format(date);
		gpsinfo = myGpsMapper.selectGpsByPlateNo(tableName,plateNo);
		if(gpsinfo!=null){
		hlWaybillDetail.setMaxVelocity(myGpsMapper.selectMaxVelocity(tableName,plateNo));
		hlWaybillDetail.setOverSpeedVelocityTimes(myGpsMapper.selectOverSpeedTimes(tableName,plateNo));
		hlWaybillDetail.setGpsinfo(gpsinfo);
		Integer status = gpsinfo.getStatus();
		hlWaybillDetail.setStatus(GpsStatus.statusdeal(status));
		}
        CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		
		HlCarinfoExample example = new HlCarinfoExample();
		HlCarinfoExample.Criteria criteria = example.createCriteria();
		criteria.andCarNumEqualTo(plateNo);
		List<HlCarinfo> list = hlCarinfoMapper.selectByExample(example);
		if(list.size()>0){
			hlWaybillDetail.setHlCarinfo(list.get(0));
		}
		int driverId = myCarInfoMapper.selDriver(plateNo);
		int saferId = myCarInfoMapper.selSafer(plateNo);
		hlWaybillDetail.setDriver(hlPeopleMapper.selectByPrimaryKey(driverId));
		hlWaybillDetail.setSafer(hlPeopleMapper.selectByPrimaryKey(saferId));
		
		int goodsTypeId = myGoodsInfoMapper.selGoodsTypeIdByGoodsId(goods_id);
		int safeCardid = myGoodsInfoMapper.selSafeCardIdByGoodsId(goods_id);
		hlWaybillDetail.setHlGoodsinfo(hlGoodsInfoMapper.selectByPrimaryKey(goods_id));
		hlWaybillDetail.setHlGoodstype(hlGoodstypeMapper.selectByPrimaryKey(goodsTypeId));
		hlWaybillDetail.setHlSafecard(hlSafecardMapper.selectByPrimaryKey(safeCardid));
		return hlWaybillDetail;
	}
	@Override
	public int insertWaybill(HlWaybill hlWaybill) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return hlWaybillMapper.insertSelective(hlWaybill);
	}

	@Override
	public int delWaybillBatch(int[] arrays) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return myWaybillMapper.delWaybillBatch(arrays);
	}

	@Override
	public int updateWaybill(HlWaybill hlWaybill) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return hlWaybillMapper.updateByPrimaryKeySelective(hlWaybill);
	}

	@Override
	public List<HlWaybill> selectWayBillByDriverName(String name) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return myWaybillMapper.selectWayBillByDriverName(name);
	}

	@Override
	public HlWaybill selectById(int id) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return hlWaybillMapper.selectByPrimaryKey(id);
	}
	/*@Override
	public List<HlWaybill> selWaybillByShipper(String Shipper) {
		HlWaybillExample example = new HlWaybillExample();
		HlWaybillExample.Criteria criteria = example.createCriteria();
		criteria.andShipperLike("%"+Shipper+"%");
		return hlWaybillMapper.selectByExample(example);
	}*/
	@Override
	public int insertWaybillAndSite(HlWaybillSite waybillSite) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return hlWaybillSiteMapper.insertSelective(waybillSite);
	}
	@Override
	public Integer[] selectSiteId(int waybillId) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return myWaybillMapper.selectSiteId(waybillId);
	}
	@Override
	public List<HlSite> selectSiteById(Integer[] arrays) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return mySiteMapper.selSiteById(arrays);
	}
	@Override
	public int delWaybillSite(int waybillId) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return myWaybillSiteMapper.delWaybillSite(waybillId);
	}
	@Override
	public HlCarrierInfo getCarrierByWaybillId(int waybillId) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		
		HlCarrierInfo hlCarrierInfo = new HlCarrierInfo();
		
		//通过电子运单id查询电子运单
		HlWaybill hlWaybill = hlWaybillMapper.selectByPrimaryKey(waybillId);
		
		//通过电子运单id查询电子运单(Check)
		HlCheckWaybillExample checkWaybillExample = new HlCheckWaybillExample();
		HlCheckWaybillExample.Criteria checkWaybillCriteria = checkWaybillExample.createCriteria();
		checkWaybillCriteria.andWaybillIdEqualTo(waybillId);
		HlCheckWaybill hlCheckWaybill = hlCheckWaybillMapper.selectByExample(checkWaybillExample).get(0);
		
		//获取电子运单中车牌号,同多车牌号查询车辆信息
		HlCarinfoExample carinfoExample = new HlCarinfoExample();
		HlCarinfoExample.Criteria carinfoCriteria = carinfoExample.createCriteria();
		carinfoCriteria.andCarNumEqualTo(hlWaybill.getCarNum());
		HlCarinfo hlCarinfo = hlCarinfoMapper.selectByExample(carinfoExample).get(0);
		
		//获取车辆信息中驾驶员和押运员的id,同多id查询驾驶员和押运员的信息
		HlPeople driver = hlPeopleMapper.selectByPrimaryKey(hlCarinfo.getDriverId());
		HlPeople safer = hlPeopleMapper.selectByPrimaryKey(hlCarinfo.getSaferId());
		
		//获取电子运单中货物信息的id,通过id查询货物信息
		HlGoodsinfo hlGoodsinfo = hlGoodsInfoMapper.selectByPrimaryKey(hlWaybill.getGoodsId());
		
		//获取货物信息中货物类型的id,通过id查询货物类型
		HlGoodstype hlGoodstype = hlGoodstypeMapper.selectByPrimaryKey(hlGoodsinfo.getGoodstyleId());
		
		hlCarrierInfo.setHlCarinfo(hlCarinfo);
		hlCarrierInfo.setDriver(driver);
		hlCarrierInfo.setSafer(safer);
		hlCarrierInfo.setHlCheckWaybill(hlCheckWaybill);
		hlCarrierInfo.setHlGoodstype(hlGoodstype);
		return hlCarrierInfo;
	}
	/**   
	 * Title: getCarDrivingLogByWaybillId   
	 * Description: 根据电子运单id获取危运车辆行车日志台账 
	 * @param waybillId   
	 * @see cm.cn.service.HlWaybillService#getCarDrivingLogByWaybillId(int)   
	 */
	@Override
	public HlCarDrivingLog getCarDrivingLogByWaybillId(int waybillId) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		
		//通过电子运单id查询电子运单
        HlWaybill hlWaybill = hlWaybillMapper.selectByPrimaryKey(waybillId);
        
		//通过电子运单id查询电子运单(Check)
		HlCheckWaybillExample checkWaybillExample = new HlCheckWaybillExample();
		HlCheckWaybillExample.Criteria checkWaybillCriteria = checkWaybillExample.createCriteria();
		checkWaybillCriteria.andWaybillIdEqualTo(waybillId);
		HlCheckWaybill hlCheckWaybill = hlCheckWaybillMapper.selectByExample(checkWaybillExample).get(0);
		
		//获取电子运单中车牌号,同多车牌号查询车辆信息
		HlCarinfoExample carinfoExample = new HlCarinfoExample();
		HlCarinfoExample.Criteria carinfoCriteria = carinfoExample.createCriteria();
		carinfoCriteria.andCarNumEqualTo(hlWaybill.getCarNum());
		HlCarinfo hlCarinfo = hlCarinfoMapper.selectByExample(carinfoExample).get(0);
		
		//获取电子运单中货物信息的id,通过id查询货物信息
		HlGoodsinfo hlGoodsinfo = hlGoodsInfoMapper.selectByPrimaryKey(hlWaybill.getGoodsId());
				
		//获取车辆信息中驾驶员和押运员的id,同多id查询驾驶员和押运员的信息
		HlPeople driver = hlPeopleMapper.selectByPrimaryKey(hlCarinfo.getDriverId());
		HlPeople safer = hlPeopleMapper.selectByPrimaryKey(hlCarinfo.getSaferId());
		
		
		HlCarDrivingLog hlCarDrivingLog = new HlCarDrivingLog();
		hlCarDrivingLog.setHlCheckWaybill(hlCheckWaybill);
		hlCarDrivingLog.setHlCarinfo(hlCarinfo);
		hlCarDrivingLog.setDriver(driver);
		hlCarDrivingLog.setSafer(safer);
		hlCarDrivingLog.setHlGoodsinfo(hlGoodsinfo);
		return hlCarDrivingLog;
	}

}
