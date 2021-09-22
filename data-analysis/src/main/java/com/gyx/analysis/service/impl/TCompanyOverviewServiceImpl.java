package com.gyx.analysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gyx.analysis.entity.TCompanyOverview;
import com.gyx.analysis.mapper.TCompanyOverviewMapper;
import com.gyx.analysis.service.ITCompanyOverviewService;
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
public class TCompanyOverviewServiceImpl extends ServiceImpl<TCompanyOverviewMapper, TCompanyOverview> implements ITCompanyOverviewService {

}
