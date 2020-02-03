package com.hd.data.dao;

import com.hd.data.entity.OldProjectInfoEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OldProjectInfoEntityMapper {

    List<OldProjectInfoEntity> selectProjectInfo(@Param("tableName") String tableName, @Param("jobLevel") String jobLevel, @Param("oldDeptId") String oldDeptId);

    List<OldProjectInfoEntity> selectToDoArchiveProjectInfo(@Param("taskKey") String taskKey, @Param("jobLevel") String jobLevel, @Param("oldDeptId") String oldDeptId);

    List<OldProjectInfoEntity> selectProjectReplenishInfo();

}