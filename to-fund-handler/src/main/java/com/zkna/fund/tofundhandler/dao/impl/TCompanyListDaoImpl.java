package com.zkna.fund.tofundhandler.dao.impl;

import cn.org.atool.fluent.mybatis.If;
import cn.org.atool.fluent.mybatis.base.crud.IQuery;
import cn.org.atool.fluent.mybatis.base.mapper.IRichMapper;
import com.zkna.fund.tofundhandler.dao.base.TCompanyListBaseDao;
import com.zkna.fund.tofundhandler.dao.intf.TCompanyListDao;
import com.zkna.fund.tofundhandler.entity.TCompanyListEntity;
import com.zkna.fund.tofundhandler.mapper.TCompanyListMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * TCompanyListDaoImpl: 数据操作接口实现
 *
 * 这只是一个减少手工创建的模板文件
 * 可以任意添加方法和实现, 更改作者和重定义类名
 * <p/>@author Powered By Fluent Mybatis
 */
@Repository
public class TCompanyListDaoImpl extends TCompanyListBaseDao implements TCompanyListDao {

    @Override
    public List<TCompanyListEntity> getCompanyByCompanyId(String companyId) {
//        return super.query();
        return null;
    }

}
