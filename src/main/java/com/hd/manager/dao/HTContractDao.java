package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.common.entity.TbContractEntity;
import com.hd.common.entity.TbContractSaveMaterialRelationEntity;
import com.hd.manager.dao.bean.HTContractBean;
import com.hd.manager.dao.bean.HTContractTaskHisTaskInstBean;
import java.util.List;

/**
 * 设置-合同 持久层接口
 *
 * @author jwl
 * Created in 2019/7/17 15:31
 */
public interface HTContractDao extends BaseDao {

    /**
     * 检索合同预计收款时间列表
     */
    List<HTContractBean> excSelectContractProjectTypeRelations(HTContractBean paramBean);

    /**
     * 检索预计收款时间列表
     */
    List<HTContractBean> excSelectPredictRequestRelations(HTContractBean paramBean);

    /**
     * 批量插入合同和项目类别指标列表数据
     */
    void excInsertContractProjectTypeRelations(List<HTContractBean> paramList);

    /**
     * 批量插入预计收款时间
     */
    void excInsertContractPredictRequestRelations(List<HTContractBean> paramList);

    /**
     * 删除合同和项目类别指标列表数据
     * @param paramBean
     */
    void excDeleteContractProjectTypeRelations(TbContractEntity paramBean);


    /**
     * 删除预计收款时间
     * @param paramBean
     */
    void excDeleteContractPredictRequestRelations(TbContractEntity paramBean);

    /**
     * 查询流程列表
     * @param paramBean
     * @return
     */
    List<HTContractTaskHisTaskInstBean> excSearchHTContractTaskHisTaskInstInfoList(HTContractTaskHisTaskInstBean paramBean);

    /**
     *  查询最高职位
     * @param paramBean
     */
    List<HTContractBean> excSearchJobNameByLoginUserIdAndDepartmentId(HTContractBean paramBean);

    /**
     *  根据合同id 查询合同信息
     * @param contractId 合同id
     * @return 合同信息
     */
    HTContractBean getContractInfoByContractId(String contractId);

    /**
     *  上传归档文件信息
     */
    void excInsertContractArchiveFileRelationInfo(TbContractSaveMaterialRelationEntity paramEntity);


    /**
     *  查询归档文件信息
     * @param paramBean 合同id
     * @return 归档文件信息
     */
    List<HTContractBean> searchContractArchiveFileList(HTContractBean paramBean);



    /**
     *  查询合同列表信息
     * @param paramBean 查询参数
     * @return 合同列表信息
     */
    List<HTContractBean> excSearchContractList(HTContractBean paramBean);

    /**
     *  查询合同列表总数
     * @param paramBean 查询参数
     * @return 数量
     */
    Integer excSearchContractCount(HTContractBean paramBean);

    /**
     * 通过参数查询合同信息
     * @param paramBean
     * @return
     */
    List<HTContractBean> excSearchContractListByProperty(HTContractBean paramBean);



    /**
     *  查询法务合同列表信息
     * @param paramBean 查询参数
     * @return 合同列表信息
     */
    List<HTContractBean> excSearchLegalAffairContractList(HTContractBean paramBean);

    /**
     *  查询法务合同列表总数
     * @param paramBean 查询参数
     * @return 数量
     */
    Integer excSearchLegalAffairContractCount(HTContractBean paramBean);
}
