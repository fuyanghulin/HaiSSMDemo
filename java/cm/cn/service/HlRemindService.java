package cm.cn.service;

import java.util.List;
import cm.cn.po.HlCarinfo;
import cm.cn.po.HlCompany;
import cm.cn.po.HlRemind;

public interface HlRemindService {
	public HlRemind getRemind();
	public List<HlCompany> getCompanyRemindByBusiLic();
	public List<HlCompany> getCompanyRemindByBusiCer();
	//交强险
	public List<HlCarinfo> getCarRemindByInsuranceDate();
	//第三责任险
	public List<HlCarinfo> getCarRemindByDutyInsurancedate();
	// 承运人责任险(
	public List<HlCarinfo> getCarRemindByDutypeoDate();
	//车辆行驶证年审
	public List<HlCarinfo> getCarRemindByCertyearDate();
	// 道路运输证年审
	public List<HlCarinfo> getCarRemindByRoadDate();
	//罐体年检
	public List<HlCarinfo> getCarRemindByYearcheckDate();
	//技术等级评定
	public List<HlCarinfo> getCarRemindByTecDate();
	// 二级维护
	public List<HlCarinfo> getCarRemindBySecImprovedate();
}
