package cm.cn.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.cn.datasource.CustomerContextHolder;
import cm.cn.mapper.HlCarAttachMapper;
import cm.cn.mapper.HlCarinfoMapper;
import cm.cn.mapper.MyCarAttachMapper;
import cm.cn.mapper.MyCarInfoMapper;
import cm.cn.po.HlCarAttach;
import cm.cn.po.HlCarAttachExample;
import cm.cn.po.HlCarinfo;
import cm.cn.po.HlCarinfoExample;
import cm.cn.service.HlCarInfoService;
@Service
public class HlCarInfoServiceImpl implements HlCarInfoService {
	@Autowired
	private MyCarInfoMapper myCarInfoMapper;
	@Autowired
	private HlCarinfoMapper hlCarinfoMapper;
	@Autowired
	private HlCarAttachMapper hlCarAttachMapper;
	@Autowired
	private MyCarAttachMapper myCarAttachMapper;
	@Override
	public List<HlCarinfo> selAllCar() {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
//		HlCarinfoExample example = new HlCarinfoExample();
		return hlCarinfoMapper.selectByExample(null);
	}

	@Override
	public int delCarBatch(int[] arrays) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		// TODO Auto-generated method stub
		return myCarInfoMapper.delCarInfoBatch(arrays);
	}

	@Override
	public int insertCar(HlCarinfo carinfo) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		// TODO Auto-generated method stub
		return hlCarinfoMapper.insertSelective(carinfo);
	}

	@Override
	public int updateCar(HlCarinfo carinfo) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		// TODO Auto-generated method stub
		return hlCarinfoMapper.updateByPrimaryKeySelective(carinfo);
	}

	@Override
	public List<HlCarinfo> selectByCarLicense(String carLicense) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlCarinfoExample example = new HlCarinfoExample();
		HlCarinfoExample.Criteria criteria = example.createCriteria();
		criteria.andCarNumLike("%"+carLicense+"%");
		return hlCarinfoMapper.selectByExample(example);
	}

	@Override
	public List<HlCarinfo> selectByCompanyName(String company_name) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlCarinfoExample example = new HlCarinfoExample();
		HlCarinfoExample.Criteria criteria = example.createCriteria();
		criteria.andCompanyNameEqualTo(company_name);
		return hlCarinfoMapper.selectByExample(example);
	}

	@Override
	public List<HlCarinfo> selectByCompanyId(int id) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlCarinfoExample example = new HlCarinfoExample();
		HlCarinfoExample.Criteria criteria = example.createCriteria();
		criteria.andCompanyIdEqualTo(id);
		return hlCarinfoMapper.selectByExample(example);
	}

	@Override
	public List<HlCarinfo> selectCarInfoByCompanyIdAndCarNum(int companyId, String carNum) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlCarinfoExample example = new HlCarinfoExample();
		HlCarinfoExample.Criteria criteria = example.createCriteria();
		criteria.andCompanyIdEqualTo(companyId);
		criteria.andCarNumLike("%"+carNum+"%");
		return hlCarinfoMapper.selectByExample(example);
	}

	@Override
	public int insertCarAttach(HlCarAttach hlCarAttach) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return hlCarAttachMapper.insertSelective(hlCarAttach);
	}

	@Override
	public List<HlCarAttach> selectCarAttach(String carNum) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		HlCarAttachExample example = new HlCarAttachExample();
		HlCarAttachExample.Criteria criteria = example.createCriteria();
		criteria.andCarNumEqualTo(carNum);
		return hlCarAttachMapper.selectByExample(example);
	}

	@Override
	public int delCarAttachBatch(int[] arrays) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return myCarAttachMapper.delCarAttacBatch(arrays);
	}

	@Override
	public int updateCarAttach(HlCarAttach hlCarAttach) {
		CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_GENERAL);
		return hlCarAttachMapper.updateByPrimaryKeySelective(hlCarAttach);
		
	}

}
