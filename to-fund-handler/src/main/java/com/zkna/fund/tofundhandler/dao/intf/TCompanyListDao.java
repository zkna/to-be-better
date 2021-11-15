package com.zkna.fund.tofundhandler.dao.intf;

import cn.org.atool.fluent.mybatis.base.IBaseDao;
import com.zkna.fund.tofundhandler.entity.TCompanyListEntity;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * TCompanyListDao: 数据操作接口
 *
 * 这只是一个减少手工创建的模板文件
 * 可以任意添加方法和实现, 更改作者和重定义类名
 * <p/>@author Powered By Fluent Mybatis
 */
public interface TCompanyListDao extends IBaseDao<TCompanyListEntity> {

    /**
     * 根据公司id获取基金公司信息
     * @param companyId
     * @return
     */
    List<TCompanyListEntity> getCompanyByCompanyId(String companyId);
}
