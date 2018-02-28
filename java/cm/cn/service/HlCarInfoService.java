package cm.cn.service;

import java.util.List;

import cm.cn.po.HlCarAttach;
import cm.cn.po.HlCarinfo;

public interface HlCarInfoService {
	//查询所有的车信息
	public List<HlCarinfo> selAllCar();
	//批量删除车的信息
	public int delCarBatch(int[] arrays);
	//增加车的信息
	public int insertCar(HlCarinfo carinfo);
	//更改车的信息
	public int updateCar(HlCarinfo carinfo);
	//根据车牌号查询车的信息
	public List<HlCarinfo> selectByCarLicense(String carLicense);
	//根据公司名查询车的信息
	public List<HlCarinfo> selectByCompanyName(String company_name);
	//根据公司id查询车的信息
	public List<HlCarinfo> selectByCompanyId(int id);
	//根据公司id,对车牌进行模糊查询
	public List<HlCarinfo> selectCarInfoByCompanyIdAndCarNum(int companyId,String carNum);
	//增加车辆车档附件信息
	public int insertCarAttach(HlCarAttach hlCarAttach);
	//根据车牌查询车档附件信息
	public List<HlCarAttach>  selectCarAttach(String carNum);
	//批量删除车档附件信息
	public int delCarAttachBatch(int[] arrays);
	//更新车档信息
	public int updateCarAttach(HlCarAttach hlCarAttach);
}
