package com.gyx.datahandler.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyx.datahandler.entity.TEastmoneyCompanyTO;
import com.gyx.datahandler.mapper.TCompanyListMapper;
import com.gyx.datahandler.service.ITCompanyListService;
import org.springframework.stereotype.Service;

@Service
@DS("mfund")
public class TCompanyListServiceImpl extends ServiceImpl<TCompanyListMapper, TEastmoneyCompanyTO> implements ITCompanyListService {
}
