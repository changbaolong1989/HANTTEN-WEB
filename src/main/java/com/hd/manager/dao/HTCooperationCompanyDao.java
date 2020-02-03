package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.common.entity.TbCompanyEntity;
import com.hd.manager.dao.bean.HTCompanyBean;
import com.hd.manager.dao.bean.HTCooperationCompanyBean;

import java.util.List;

/**
 * 设置-合作公司管理 持久层接口
 *
 * @author jwl
 * Created in 2019/7/3 15:31
 */
public interface HTCooperationCompanyDao extends BaseDao {

    /**
     * 查询合作公司列表数据的总条数
     * @return 合作公司列表数据的总条数
     */
    Integer excSearchCooperationCompanyListCount(HTCooperationCompanyBean paramBean);

    /**
     * 查询合作公司列表数据
     * @return 合作公司列表数据
     */
    List<HTCooperationCompanyBean> excSearchCooperationCompanyList(HTCooperationCompanyBean paramBean);

    /**
     * 查询合作公司名称是否重复
     * @return
     */
    Integer excSearchCooperationCompanyName(HTCooperationCompanyBean paramBean);

    /**
     * 查询序号是否重复
     * @return
     */
    Integer excSearchSerialNum(HTCooperationCompanyBean paramBean);

    /**
     * 查询合作公司详情信息
     * @return
     */
    HTCooperationCompanyBean excSearchCooperationCompanyInfo(HTCooperationCompanyBean paramBean);

}
