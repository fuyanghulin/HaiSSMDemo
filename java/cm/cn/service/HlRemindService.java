package cm.cn.service;

import java.util.List;

import cm.cn.po.HlCompany;
import cm.cn.po.HlRemind;

public interface HlRemindService {
	public HlRemind getRemind();
	public List<HlCompany> getCompanyRemindByBusiLic();
	public List<HlCompany> getCompanyRemindByBusiCer();
}
