package com.hd.common.dao;

import com.hd.common.entity.TbTableFiveRelationEntity;
import com.hd.common.entity.TbTableFiveRelationEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbTableFiveRelationEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    long countByExample(TbTableFiveRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    int deleteByExample(TbTableFiveRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    int insert(TbTableFiveRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    int insertSelective(TbTableFiveRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    List<TbTableFiveRelationEntity> selectByExample(TbTableFiveRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    TbTableFiveRelationEntity selectByPrimaryKey(String relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbTableFiveRelationEntity record, @Param("example") TbTableFiveRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbTableFiveRelationEntity record, @Param("example") TbTableFiveRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbTableFiveRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_table_five_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbTableFiveRelationEntity record);
}