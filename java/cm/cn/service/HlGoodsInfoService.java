package cm.cn.service;

import java.util.List;

import cm.cn.po.HlGoodsinfo;

public interface HlGoodsInfoService {
	//查询所有货物信息
	public List<HlGoodsinfo> selectAllGoodsInfo();
	//根据公司Id查询货物信息
	public List<HlGoodsinfo> selectGoodsInfoByCompanyId(int companyId);
	//添加一条货物信息
	public int insertHlGoodsinfo(HlGoodsinfo goodsinfo);
	//批量删除货物信息
	public int delHlGoodsinfo(int[] arrays);
	//更改货物信息
	public int updateHlGoodsinfo(HlGoodsinfo goodsinfo);
	//根据货物名称查询货物信息
	public List<HlGoodsinfo> selectGoodsInfoByName(String name);
	//根据公司id,通过货物名称模糊查询货物信息
	public List<HlGoodsinfo> selectGoodsInfoByCompanyIdAndName(int companyId,String name);
}
