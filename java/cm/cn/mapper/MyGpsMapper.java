package cm.cn.mapper;


import org.apache.ibatis.annotations.Param;

import cm.cn.po.Gpsinfo;

public interface MyGpsMapper {
   public Gpsinfo selectGpsByPlateNo(@Param("tableName")String tableName,@Param("plateNo")String plateNo);
   public String selectMaxVelocity(String plateNo);
   public String selectOverSpeedTimes(String plateNo);
}
