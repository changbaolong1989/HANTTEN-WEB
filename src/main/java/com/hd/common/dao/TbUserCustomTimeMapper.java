package com.hd.common.dao;

import com.hd.common.entity.TbUserCustomTime;
import com.hd.common.entity.TbUserCustomTimeExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbUserCustomTimeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    long countByExample(TbUserCustomTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    int deleteByExample(TbUserCustomTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String customTaskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    int insert(TbUserCustomTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    int insertSelective(TbUserCustomTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    List<TbUserCustomTime> selectByExample(TbUserCustomTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    TbUserCustomTime selectByPrimaryKey(String customTaskId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbUserCustomTime record, @Param("example") TbUserCustomTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbUserCustomTime record, @Param("example") TbUserCustomTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbUserCustomTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_user_custom_time
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbUserCustomTime record);
}