package com.hd.common.dao;

import com.hd.common.entity.TbTenderTaskUser;
import com.hd.common.entity.TbTenderTaskUserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TbTenderTaskUserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    long countByExample(TbTenderTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    int deleteByExample(TbTenderTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    int insert(TbTenderTaskUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    int insertSelective(TbTenderTaskUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    List<TbTenderTaskUser> selectByExample(TbTenderTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    TbTenderTaskUser selectByPrimaryKey(String relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbTenderTaskUser record, @Param("example") TbTenderTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbTenderTaskUser record, @Param("example") TbTenderTaskUserExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbTenderTaskUser record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_tender_task_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbTenderTaskUser record);
}