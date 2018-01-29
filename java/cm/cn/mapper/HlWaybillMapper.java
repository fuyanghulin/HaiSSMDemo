package cm.cn.mapper;

import cm.cn.po.HlWaybill;
import cm.cn.po.HlWaybillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HlWaybillMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    int countByExample(HlWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    int deleteByExample(HlWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    int insert(HlWaybill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    int insertSelective(HlWaybill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    List<HlWaybill> selectByExample(HlWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    HlWaybill selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    int updateByExampleSelective(@Param("record") HlWaybill record, @Param("example") HlWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    int updateByExample(@Param("record") HlWaybill record, @Param("example") HlWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    int updateByPrimaryKeySelective(HlWaybill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_waybill
     *
     * @mbggenerated Tue Oct 31 15:26:30 CST 2017
     */
    int updateByPrimaryKey(HlWaybill record);
}