package com.hd.common.dao;

import com.hd.common.entity.TbMessageEntity;
import com.hd.common.entity.TbMessageEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbMessageEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    long countByExample(TbMessageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    int deleteByExample(TbMessageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String messageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    int insert(TbMessageEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    int insertSelective(TbMessageEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    List<TbMessageEntity> selectByExample(TbMessageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    TbMessageEntity selectByPrimaryKey(String messageId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbMessageEntity record, @Param("example") TbMessageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbMessageEntity record, @Param("example") TbMessageEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbMessageEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_message
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbMessageEntity record);
}