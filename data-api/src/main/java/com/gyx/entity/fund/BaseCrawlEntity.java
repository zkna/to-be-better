package com.gyx.entity.fund;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * 爬虫基类
 */
@Data
public class BaseCrawlEntity extends BaseEntity{
    /**
     * id
     */
    private Integer id;

    /**
     * 爬取时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crawlDateTime;
}
