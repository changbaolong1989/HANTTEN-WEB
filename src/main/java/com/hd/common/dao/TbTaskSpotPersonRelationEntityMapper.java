package com.hd.common.dao;

import com.hd.common.entity.TbTaskSpotPersonRelationEntity;
import com.hd.common.entity.TbTaskSpotPersonRelationEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbTaskSpotPersonRelationEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    long countByExample(TbTaskSpotPersonRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    int deleteByExample(TbTaskSpotPersonRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    int insert(TbTaskSpotPersonRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    int insertSelective(TbTaskSpotPersonRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    List<TbTaskSpotPersonRelationEntity> selectByExample(TbTaskSpotPersonRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    TbTaskSpotPersonRelationEntity selectByPrimaryKey(String relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbTaskSpotPersonRelationEntity record, @Param("example") TbTaskSpotPersonRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbTaskSpotPersonRelationEntity record, @Param("example") TbTaskSpotPersonRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbTaskSpotPersonRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_spot_person_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbTaskSpotPersonRelationEntity record);
}