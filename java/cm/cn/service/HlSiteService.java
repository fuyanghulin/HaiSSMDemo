package cm.cn.service;

import java.util.List;

import cm.cn.po.HlSite;

public interface HlSiteService {
	//添加站点信息
	public int insertSite(HlSite hlSite);
	//根据公司id和站点类型查询站点
	public List<HlSite> selSiteByCompanyIdAndType(int companyId,String type);
	//删除站点
	public int delSiteBatch(int[] arrays);
	//更新站点
	public int updateSite(HlSite hlSite);
}
