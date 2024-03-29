package com.hd.common.dao;

import com.hd.common.entity.TbTaskReeditHisTaskInst;
import com.hd.common.entity.TbTaskReeditHisTaskInstExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbTaskReeditHisTaskInstMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    long countByExample(TbTaskReeditHisTaskInstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    int deleteByExample(TbTaskReeditHisTaskInstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String historyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    int insert(TbTaskReeditHisTaskInst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    int insertSelective(TbTaskReeditHisTaskInst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    List<TbTaskReeditHisTaskInst> selectByExample(TbTaskReeditHisTaskInstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    TbTaskReeditHisTaskInst selectByPrimaryKey(String historyId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbTaskReeditHisTaskInst record, @Param("example") TbTaskReeditHisTaskInstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbTaskReeditHisTaskInst record, @Param("example") TbTaskReeditHisTaskInstExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbTaskReeditHisTaskInst record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_task_reedit_his_task_inst
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbTaskReeditHisTaskInst record);
}