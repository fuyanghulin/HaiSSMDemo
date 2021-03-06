package cm.cn.mapper;

import cm.cn.po.HlAlarm;
import cm.cn.po.HlAlarmExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HlAlarmMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    int countByExample(HlAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    int deleteByExample(HlAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    int insert(HlAlarm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    int insertSelective(HlAlarm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    List<HlAlarm> selectByExample(HlAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    HlAlarm selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    int updateByExampleSelective(@Param("record") HlAlarm record, @Param("example") HlAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    int updateByExample(@Param("record") HlAlarm record, @Param("example") HlAlarmExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    int updateByPrimaryKeySelective(HlAlarm record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_alarm
     *
     * @mbggenerated Wed Nov 01 14:17:43 CST 2017
     */
    int updateByPrimaryKey(HlAlarm record);
}