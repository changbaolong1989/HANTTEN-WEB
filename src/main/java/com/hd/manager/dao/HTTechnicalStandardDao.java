package com.hd.manager.dao;

import com.hd.base.dao.BaseDao;
import com.hd.common.entity.TbTechnicalStandardEntity;
import com.hd.manager.dao.bean.HTTechnicalStandardBean;
import java.util.List;

/**
 * 技术标准管理 持久层接口
 *
 * @author jwl
 * Created in 2019/7/5 11:00
 */
public interface HTTechnicalStandardDao extends BaseDao {

    /**
     * 查询公告技术标准列表数据的总条数
     * @return 技术标准列表数据
     */
    Integer excSearchTechnicalStandardListCount(HTTechnicalStandardBean paramBean);

    /**
     * 查询技术标准列表数据
     * @return 技术标准列表数据
     */
    List<HTTechnicalStandardBean> excSearchTechnicalStandardList(HTTechnicalStandardBean paramBean);

    /**
     * 添加技术标准信息
     */
    void excInsertTechnicalStandardInfo(TbTechnicalStandardEntity paramBean);

    /**
     * 查询技术标准详情信息
     * @return
     */
    HTTechnicalStandardBean excSearchTechnicalStandardInfo(HTTechnicalStandardBean paramBean);

}
