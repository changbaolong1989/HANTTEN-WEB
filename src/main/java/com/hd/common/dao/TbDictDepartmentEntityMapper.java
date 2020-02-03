package com.hd.common.dao;

import com.hd.common.entity.TbDictDepartmentEntity;
import com.hd.common.entity.TbDictDepartmentEntityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbDictDepartmentEntityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    long countByExample(TbDictDepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    int deleteByExample(TbDictDepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String departmentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    int insert(TbDictDepartmentEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    int insertSelective(TbDictDepartmentEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    List<TbDictDepartmentEntity> selectByExample(TbDictDepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    TbDictDepartmentEntity selectByPrimaryKey(String departmentId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") TbDictDepartmentEntity record, @Param("example") TbDictDepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") TbDictDepartmentEntity record, @Param("example") TbDictDepartmentEntityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(TbDictDepartmentEntity record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_dict_department
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(TbDictDepartmentEntity record);
}