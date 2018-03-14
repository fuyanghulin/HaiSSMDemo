package cm.cn.mapper;

import java.util.List;

import cm.cn.po.HlCompany;
import cm.cn.po.HlRemind;

public interface HlRemindMapper {
     public HlRemind getRemind();
     public List<HlCompany> getCompanyRemindByBusiLic();
 	 public List<HlCompany> getCompanyRemindByBusiCer();
}
