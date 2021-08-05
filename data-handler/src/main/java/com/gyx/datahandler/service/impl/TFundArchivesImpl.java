package com.gyx.datahandler.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gyx.datahandler.entity.TFundArchives;
import com.gyx.datahandler.mapper.TFundArchivesMapper;
import com.gyx.datahandler.service.ITFundArchivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("cfund")
public class TFundArchivesImpl extends ServiceImpl<TFundArchivesMapper, TFundArchives> implements ITFundArchivesService {

    @Autowired
    TFundArchivesMapper tFundArchivesMapper;

    @Override
    public int insertBatch(List<TFundArchives> tfs) {
        return tFundArchivesMapper.insertBatch(tfs);
    }
}
