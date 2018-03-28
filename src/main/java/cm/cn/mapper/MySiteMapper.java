package cm.cn.mapper;

import java.util.List;

import cm.cn.po.HlSite;

public interface MySiteMapper {
	public int delSiteBatch(int[] arrays);
	public List<HlSite> selSiteById(Integer[] arrays);
}
