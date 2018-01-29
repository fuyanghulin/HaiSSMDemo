package cm.cn.mapper;

import cm.cn.po.HlGoodstype;
import cm.cn.po.HlGoodstypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface HlGoodstypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    int countByExample(HlGoodstypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    int deleteByExample(HlGoodstypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    int insert(HlGoodstype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    int insertSelective(HlGoodstype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    List<HlGoodstype> selectByExample(HlGoodstypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    HlGoodstype selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    int updateByExampleSelective(@Param("record") HlGoodstype record, @Param("example") HlGoodstypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    int updateByExample(@Param("record") HlGoodstype record, @Param("example") HlGoodstypeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    int updateByPrimaryKeySelective(HlGoodstype record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table hl_goodstype
     *
     * @mbggenerated Tue Sep 12 11:21:55 CST 2017
     */
    int updateByPrimaryKey(HlGoodstype record);
}