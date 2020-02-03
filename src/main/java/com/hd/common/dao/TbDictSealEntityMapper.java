package com.hd.common.dao;

import com.hd.common.entity.TbDictSealEntity;
import com.hd.common.entity.TbDictSealEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbDictSealEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    long countByExample(TbDictSealEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    int deleteByExample(TbDictSealEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String sealTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    int insert(TbDictSealEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    int insertSelective(TbDictSealEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    List<TbDictSealEntity> selectByExample(TbDictSealEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    TbDictSealEntity selectByPrimaryKey(String sealTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbDictSealEntity record, @Param("example") TbDictSealEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbDictSealEntity record, @Param("example") TbDictSealEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbDictSealEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_seal
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbDictSealEntity record);
}