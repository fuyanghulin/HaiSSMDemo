package cm.cn.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cm.cn.mapper.HlRemindMapper;
import cm.cn.po.HlCarinfo;
import cm.cn.po.HlCompany;
import cm.cn.po.HlPeople;
import cm.cn.po.HlRemind;
import cm.cn.service.HlRemindService;
@Service
public class HlRemindServiceImpl implements HlRemindService {
    @Autowired
    private HlRemindMapper hlRemindMapper;
	@Override
	public HlRemind getRemind() {
		return hlRemindMapper.getRemind();
	}
	@Override
	public List<HlCompany> getCompanyRemindByBusiLic() {
		return hlRemindMapper.getCompanyRemindByBusiLic();
	}
	@Override
	public List<HlCompany> getCompanyRemindByBusiCer() {
		return hlRemindMapper.getCompanyRemindByBusiCer();
	}
	@Override
	public List<HlCarinfo> getCarRemindByInsuranceDate() {
		return hlRemindMapper.getCarRemindByInsuranceDate();
	}
	@Override
	public List<HlCarinfo> getCarRemindByDutyInsurancedate() {
		return hlRemindMapper.getCarRemindByDutyInsurancedate();
	}
	@Override
	public List<HlCarinfo> getCarRemindByDutypeoDate() {
		return hlRemindMapper.getCarRemindByDutypeoDate();
	}
	@Override
	public List<HlCarinfo> getCarRemindByCertyearDate() {
		return hlRemindMapper.getCarRemindByCertyearDate();
	}
	@Override
	public List<HlCarinfo> getCarRemindByRoadDate() {
		return hlRemindMapper.getCarRemindByRoadDate();
	}
	@Override
	public List<HlCarinfo> getCarRemindByYearcheckDate() {
		return hlRemindMapper.getCarRemindByYearcheckDate();
	}
	@Override
	public List<HlCarinfo> getCarRemindByTecDate() {
		return hlRemindMapper.getCarRemindByTecDate();
	}
	@Override
	public List<HlCarinfo> getCarRemindBySecImprovedate() {
		return hlRemindMapper.getCarRemindBySecImprovedate();
	}
	@Override
	public List<HlPeople> getPeopleRemindByDriLicenceTime() {
		return hlRemindMapper.getPeopleRemindByDriLicenceTime();
	}
	@Override
	public List<HlPeople> getPeopleRemindByDriverzigezhengTime() {
		return hlRemindMapper.getPeopleRemindByDriverzigezhengTime();
	}
	@Override
	public List<HlPeople> getPeopleRemindByDriverzigezhengTime2() {
		return hlRemindMapper.getPeopleRemindByDriverzigezhengTime2();
	}
    
}
