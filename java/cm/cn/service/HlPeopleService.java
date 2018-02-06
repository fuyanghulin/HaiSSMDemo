package cm.cn.service;

import java.util.List;

import cm.cn.po.HlPeople;

public interface HlPeopleService {
	//添加人员信息
	public int insertPeople(HlPeople hlPeople);
	//显示所有人员
	public List<HlPeople> selAllPeople();
	//批量删除人员
	public int delPeopleBatch(int[] arrays);
	//根据人员id查询照片的文件名
	public String[] selPictureNameById(int[] arrays);
	//更改人员信息
	public int updatePeople(HlPeople hlPeople);
	//根据人员名字查询信息
	public List<HlPeople> selectPeopleByName(String name);
	//通过公司 Id 查询到对应的员工
	public List<HlPeople> selectByCompanyId(int company_id);
	//通过公司id和人员姓名进行模糊查询
	public List<HlPeople> selectPeopleByCompanyIdAndName(int companyId,String name);
}
