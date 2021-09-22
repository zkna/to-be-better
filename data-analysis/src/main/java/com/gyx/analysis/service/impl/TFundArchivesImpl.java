package com.gyx.analysis.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gyx.analysis.entity.TFundArchives;
import com.gyx.analysis.mapper.TFundArchivesMapper;
import com.gyx.analysis.service.ITFundArchivesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@DS("cfund")
public class TFundArchivesImpl extends ServiceImpl<TFundArchivesMapper, TFundArchives> implements ITFundArchivesService {

}
