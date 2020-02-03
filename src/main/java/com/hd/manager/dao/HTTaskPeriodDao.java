package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.manager.dao.bean.HTTaskPeriodBean;

import java.util.List;

public interface HTTaskPeriodDao extends BaseDao {


    /**
     * 查询工作内容说明列表
     * @return 部门列表
     */
    List<HTTaskPeriodBean> excSearchTaskPeriodList(HTTaskPeriodBean paramBean);

    /**
     * 查询工作内容说明列表数据的总条数
     * @return 总条数
     */
    Integer excSearchTaskPeriodListCount(HTTaskPeriodBean paramBean);

    /**
     * 修改工作内容说明信息
     *
     */
    void excUpdateTaskPeriodInfo(HTTaskPeriodBean paramBean);

    /**
     * 查询所有部门类型
     * @return 所有部门类型
     */
    List<HTTaskPeriodBean> excSearchAllDictDepartmentList();

    /**
     * 查询部门信息
     *
     */
    HTTaskPeriodBean excSearchTaskPeriodInfo (HTTaskPeriodBean paramBean);

    /**
     * 通过咨询类型ID查询任务阶段列表
     * @return 任务阶段列表
     */
    List<HTTaskPeriodBean> excSearchTaskPeriodListByCounselTypeId(String counselTypeId);


    /**
     *根据部门类型ID查询任务阶段ID列表
     * @return 任务阶段ID列表
     */
    List<String> excSearchPeriodIdsByDepartmentId(String departmentId);
}
