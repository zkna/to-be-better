package com.gyx.analysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gyx.analysis.entity.TCompanyList;
import com.gyx.analysis.mapper.TCompanyListMapper;
import com.gyx.analysis.service.ITCompanyListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author gyx
 * @since 2021-08-08
 */
@DS("mfund")
@Service
public class TCompanyListServiceImpl extends ServiceImpl<TCompanyListMapper, TCompanyList> implements ITCompanyListService {

}
