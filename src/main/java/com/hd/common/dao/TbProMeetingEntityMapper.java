package com.hd.common.dao;

import com.hd.common.entity.TbProMeetingEntity;
import com.hd.common.entity.TbProMeetingEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbProMeetingEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    long countByExample(TbProMeetingEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    int deleteByExample(TbProMeetingEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String meetingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    int insert(TbProMeetingEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    int insertSelective(TbProMeetingEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    List<TbProMeetingEntity> selectByExample(TbProMeetingEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    TbProMeetingEntity selectByPrimaryKey(String meetingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbProMeetingEntity record, @Param("example") TbProMeetingEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbProMeetingEntity record, @Param("example") TbProMeetingEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbProMeetingEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_pro_meeting
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbProMeetingEntity record);
}