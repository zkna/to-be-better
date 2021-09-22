package com.gyx.analysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.gyx.analysis.entity.TFundList;
import com.gyx.analysis.mapper.TFundListMapper;
import com.gyx.analysis.service.ITFundListService;
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
public class TFundListServiceImpl extends ServiceImpl<TFundListMapper, TFundList> implements ITFundListService {

}
