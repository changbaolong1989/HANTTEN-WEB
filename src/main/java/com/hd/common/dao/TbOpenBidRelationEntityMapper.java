package com.hd.common.dao;

import com.hd.common.entity.TbOpenBidRelationEntity;
import com.hd.common.entity.TbOpenBidRelationEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbOpenBidRelationEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    long countByExample(TbOpenBidRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    int deleteByExample(TbOpenBidRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String bidId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    int insert(TbOpenBidRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    int insertSelective(TbOpenBidRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    List<TbOpenBidRelationEntity> selectByExample(TbOpenBidRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    TbOpenBidRelationEntity selectByPrimaryKey(String bidId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbOpenBidRelationEntity record, @Param("example") TbOpenBidRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbOpenBidRelationEntity record, @Param("example") TbOpenBidRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbOpenBidRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_open_bid_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbOpenBidRelationEntity record);
}