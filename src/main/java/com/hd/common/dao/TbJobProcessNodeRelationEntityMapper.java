package com.hd.common.dao;

import com.hd.common.entity.TbJobProcessNodeRelationEntity;
import com.hd.common.entity.TbJobProcessNodeRelationEntityExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbJobProcessNodeRelationEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    long countByExample(TbJobProcessNodeRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    int deleteByExample(TbJobProcessNodeRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(@Param("nodeId") String nodeId, @Param("departmentId") String departmentId, @Param("jobId") String jobId, @Param("processDictId") String processDictId, @Param("uuid") String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    int insert(TbJobProcessNodeRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    int insertSelective(TbJobProcessNodeRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    List<TbJobProcessNodeRelationEntity> selectByExample(TbJobProcessNodeRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    TbJobProcessNodeRelationEntity selectByPrimaryKey(@Param("nodeId") String nodeId, @Param("departmentId") String departmentId, @Param("jobId") String jobId, @Param("processDictId") String processDictId, @Param("uuid") String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbJobProcessNodeRelationEntity record, @Param("example") TbJobProcessNodeRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbJobProcessNodeRelationEntity record, @Param("example") TbJobProcessNodeRelationEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbJobProcessNodeRelationEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_job_processnode_relation
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbJobProcessNodeRelationEntity record);
}