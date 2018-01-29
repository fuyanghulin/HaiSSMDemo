package cm.cn.mapper;

import cm.cn.po.HlCheckWaybill;
import cm.cn.po.HlCheckWaybillExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HlCheckWaybillMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    int countByExample(HlCheckWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    int deleteByExample(HlCheckWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    int insert(HlCheckWaybill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    int insertSelective(HlCheckWaybill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    List<HlCheckWaybill> selectByExample(HlCheckWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    HlCheckWaybill selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    int updateByExampleSelective(@Param("record") HlCheckWaybill record, @Param("example") HlCheckWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    int updateByExample(@Param("record") HlCheckWaybill record, @Param("example") HlCheckWaybillExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    int updateByPrimaryKeySelective(HlCheckWaybill record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_check_waybill
     *
     * @mbggenerated Thu Sep 28 11:49:35 CST 2017
     */
    int updateByPrimaryKey(HlCheckWaybill record);
}