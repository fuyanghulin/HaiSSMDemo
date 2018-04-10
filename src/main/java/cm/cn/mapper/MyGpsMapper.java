package cm.cn.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import cm.cn.po.GpsTrackResult;
import cm.cn.po.Gpsinfo;

public interface MyGpsMapper {
	public Gpsinfo selectGpsByPlateNo(@Param("tableName") String tableName, @Param("plateNo") String plateNo);

	public String selectMaxVelocity(@Param("tableName") String tableName, @Param("plateNo") String plateNo);

	public String selectOverSpeedTimes(@Param("tableName") String tableName, @Param("plateNo") String plateNo);

	public List<GpsTrackResult> selectGpsTrack(@Param("tableName") String tableName, @Param("plateNo") String plateNo,
			                                   @Param("startTime") String startTime, @Param("finishTime") String finishTime);
}
