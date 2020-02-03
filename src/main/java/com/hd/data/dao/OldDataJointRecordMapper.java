package com.hd.data.dao;

import com.hd.data.entity.OldDataJointRecord;
import com.hd.data.entity.OldDataJointRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OldDataJointRecordMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    long countByExample(OldDataJointRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    int deleteByExample(OldDataJointRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String contractId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    int insert(OldDataJointRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    int insertSelective(OldDataJointRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    List<OldDataJointRecord> selectByExample(OldDataJointRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    OldDataJointRecord selectByPrimaryKey(String contractId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") OldDataJointRecord record, @Param("example") OldDataJointRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") OldDataJointRecord record, @Param("example") OldDataJointRecordExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(OldDataJointRecord record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table old_data_joint_record
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(OldDataJointRecord record);
}