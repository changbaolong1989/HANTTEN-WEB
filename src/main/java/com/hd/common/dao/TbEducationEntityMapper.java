package com.hd.common.dao;

import com.hd.common.entity.TbEducationEntity;
import com.hd.common.entity.TbEducationEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbEducationEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    long countByExample(TbEducationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    int deleteByExample(TbEducationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String educationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    int insert(TbEducationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    int insertSelective(TbEducationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    List<TbEducationEntity> selectByExample(TbEducationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    TbEducationEntity selectByPrimaryKey(String educationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbEducationEntity record, @Param("example") TbEducationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbEducationEntity record, @Param("example") TbEducationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbEducationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_education
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbEducationEntity record);
}