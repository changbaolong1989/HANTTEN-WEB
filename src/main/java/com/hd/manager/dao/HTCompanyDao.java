package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.common.entity.TbCompanyEntity;
import com.hd.manager.dao.bean.HTCompanyBean;
import com.hd.manager.dao.bean.HTUserBean;

import java.util.List;

/**
 * 设置-公司管理 持久层接口
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
public interface HTCompanyDao extends BaseDao {

    /**
     * 查询公司列表数据的总条数
     * @return 公司列表数据
     */
    Integer excSearchCompanyListCount(HTCompanyBean paramBean);

    /**
     * 查询公司列表数据
     * @return 公司列表数据
     */
    List<HTCompanyBean> excSearchCompanyList(HTCompanyBean paramBean);

    /**
     * 查询公司名称是否重复
     * @return
     */
    Integer excSearchCompanyName(HTCompanyBean paramBean);

    /**
     * 查询公司缩写是否重复
     * @return
     */
    Integer excSearchCondense(HTCompanyBean paramBean);

    /**
     * 查询公司详情信息
     * @return
     */
    HTCompanyBean excSearchCompanyInfo(HTCompanyBean paramBean);

    /**
     * 查询该公司是否已经和项目绑定
     * @return
     */
    Integer excSearchCompanyIdFromProject(TbCompanyEntity tbCompanyEntity);

}
