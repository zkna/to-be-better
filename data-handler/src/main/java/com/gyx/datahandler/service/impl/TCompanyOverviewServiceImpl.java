package com.gyx.datahandler.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyx.datahandler.entity.TEastmoneyFundOverviewTO;
import com.gyx.datahandler.mapper.TCompanyOverviewMapper;
import com.gyx.datahandler.service.ITCompanyOverviewService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zkna
 * @since 2021-07-14
 */
@Service
@DS("mfund")
public class TCompanyOverviewServiceImpl extends ServiceImpl<TCompanyOverviewMapper, TEastmoneyFundOverviewTO> implements ITCompanyOverviewService {
    public void testOne(){
        QueryWrapper<TEastmoneyFundOverviewTO> qw = new QueryWrapper<>();
        baseMapper.selectOne(qw);
    }
}
