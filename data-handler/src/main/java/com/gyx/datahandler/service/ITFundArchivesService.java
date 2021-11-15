package com.gyx.datahandler.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gyx.datahandler.entity.TFundArchives;

import java.util.List;

public interface ITFundArchivesService extends IService<TFundArchives> {
    int insertBatch(List<TFundArchives> tfs);
}
