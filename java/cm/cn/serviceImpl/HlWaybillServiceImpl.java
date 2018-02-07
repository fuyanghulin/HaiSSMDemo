package cm.cn.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.cn.datasource.CustomerContextHolder;
import cm.cn.mapper.HlCarinfoMapper;
import cm.cn.mapper.HlGoodsinfoMapper;
import cm.cn.mapper.HlGoodstypeMapper;
import cm.cn.mapper.HlPeopleMapper;
import cm.cn.mapper.HlSafecardMapper;
import cm.cn.mapper.HlWaybillMapper;
import cm.cn.mapper.MyCarInfoMapper;
import cm.cn.mapper.MyGoodsInfoMapper;
import cm.cn.mapper.MyGpsMapper;
import cm.cn.mapper.MyWaybillMapper;
import cm.cn.po.HlWaybillDetail;
import cm.cn.po.Gpsinfo;
import cm.cn.po.HlCarinfo;
import cm.cn.po.HlCarinfoExample;
import cm.cn.po.HlWaybill;
import cm.cn.po.HlWaybillExample;
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
	@Override
	public List<HlWaybill> selAllWaybill() {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlWaybillExample example = new HlWaybillExample();
		return hlWaybillMapper.selectByExample(example);
	}
	@Override
	public List<HlWaybill> selWaybillByCompanyId(int company_id) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlWaybillExample example = new HlWaybillExample();
		HlWaybillExample.Criteria criteria = example.createCriteria();
		criteria.andCompayIdEqualTo(company_id);
		return hlWaybillMapper.selectByExample(example);
	}
	@Override
	public List<HlWaybill> selWaybillByCompanyIdAndShipper(int companyId, String Shipper) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlWaybillExample example = new HlWaybillExample();
		HlWaybillExample.Criteria criteria = example.createCriteria();
		criteria.andCompayIdEqualTo(companyId);
		criteria.andShipperLike("%"+Shipper+"%");
		return hlWaybillMapper.selectByExample(example);
	}
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
	@Override
	public List<HlWaybill> selWaybillByShipper(String Shipper) {
		HlWaybillExample example = new HlWaybillExample();
		HlWaybillExample.Criteria criteria = example.createCriteria();
		criteria.andShipperLike("%"+Shipper+"%");
		return hlWaybillMapper.selectByExample(example);
	}

}
