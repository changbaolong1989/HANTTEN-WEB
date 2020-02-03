package com.hd.common.dao;

import com.hd.common.entity.TbContractBaseInfoEntity;
import com.hd.common.entity.TbContractBaseInfoEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbContractBaseInfoEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    long countByExample(TbContractBaseInfoEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    int deleteByExample(TbContractBaseInfoEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String baseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    int insert(TbContractBaseInfoEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    int insertSelective(TbContractBaseInfoEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    List<TbContractBaseInfoEntity> selectByExample(TbContractBaseInfoEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    TbContractBaseInfoEntity selectByPrimaryKey(String baseId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbContractBaseInfoEntity record, @Param("example") TbContractBaseInfoEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbContractBaseInfoEntity record, @Param("example") TbContractBaseInfoEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbContractBaseInfoEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_contract_base_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbContractBaseInfoEntity record);
}