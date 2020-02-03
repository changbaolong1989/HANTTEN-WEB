package com.hd.manager.dao;

import com.hd.common.entity.TbUserJobRelation;
import com.hd.common.entity.TbUserJobRelationExample;

import java.util.List;

public interface HTUserJobRelationDao {

    /**
     * 查询用户
     *
     * @param example 查询条件
     * @return 用户集合
     */
    List<TbUserJobRelation> selectIncumbentUser(TbUserJobRelationExample example);

}
