package com.hd.common.dao;

import com.hd.common.entity.TbCompanyEntity;
import com.hd.common.entity.TbCompanyEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbCompanyEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    long countByExample(TbCompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    int deleteByExample(TbCompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String companyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    int insert(TbCompanyEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    int insertSelective(TbCompanyEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    List<TbCompanyEntity> selectByExample(TbCompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    TbCompanyEntity selectByPrimaryKey(String companyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbCompanyEntity record, @Param("example") TbCompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbCompanyEntity record, @Param("example") TbCompanyEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbCompanyEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_company
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbCompanyEntity record);
}