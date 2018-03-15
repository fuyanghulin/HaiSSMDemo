package cm.cn.mapper;

import java.util.List;

import cm.cn.po.HlCarinfo;
import cm.cn.po.HlCompany;
import cm.cn.po.HlPeople;
import cm.cn.po.HlRemind;

public interface HlRemindMapper {
    public HlRemind getRemind();
    public List<HlCompany> getCompanyRemindByBusiLic();
 	public List<HlCompany> getCompanyRemindByBusiCer();
    public List<HlCarinfo> getCarRemindByInsuranceDate();
	public List<HlCarinfo> getCarRemindByDutyInsurancedate();
	public List<HlCarinfo> getCarRemindByDutypeoDate();
	public List<HlCarinfo> getCarRemindByCertyearDate();
	public List<HlCarinfo> getCarRemindByRoadDate();
	public List<HlCarinfo> getCarRemindByYearcheckDate();
	public List<HlCarinfo> getCarRemindByTecDate();
	public List<HlCarinfo> getCarRemindBySecImprovedate();
	public List<HlPeople> getPeopleRemindByDriLicenceTime();
	public List<HlPeople> getPeopleRemindByDriverzigezhengTime();
	public List<HlPeople> getPeopleRemindByDriverzigezhengTime2();
}
