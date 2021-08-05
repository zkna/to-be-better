package com.gyx.datahandler.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyx.datahandler.entity.TFundList;
import com.gyx.datahandler.mapper.TFundListMapper;
import com.gyx.datahandler.service.ITFundListService;
import org.springframework.stereotype.Service;

@Service
@DS("mfund")
public class TFundListServiceImpl extends ServiceImpl<TFundListMapper, TFundList> implements ITFundListService {
}
