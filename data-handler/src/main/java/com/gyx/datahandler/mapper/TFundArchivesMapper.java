package com.gyx.datahandler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gyx.datahandler.entity.TFundArchives;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author gyx
 */
@Mapper
public interface TFundArchivesMapper extends BaseMapper<TFundArchives> {
    public int insertBatch(@Param("archives") List<TFundArchives> tfs);
}
