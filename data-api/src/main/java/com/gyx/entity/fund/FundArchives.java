package com.gyx.entity.fund;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

/**
 * @author gyx
 */
@Data
public class FundArchives extends BaseEntity{

    /**
     * 爬取时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crawlDateTime;

    /**
     * 基金大类
     */
    private String fundConcept;

    /**
     * 基金代码
     */
    private String fundCode;

    /**
     * 净值日期
     */
    @JSONField(format = "yyyy-MM-dd")
    private String netDateTime;

    /**
     * 单位净值
     */
    private String unitNet;

    /**
     * 累计净值
     */
    private String accumulatedNet;

    /**
     * 日增长率
     */
    private String dayGrowRate;

    /**
     * 申购状态
     */
    private String subscriptionStatus;

    /**
     * 赎回状态
     */
    private String redemptionStatus;

    /**
     * 分红配送
     */
    private String dividendDistribution;

    /**
     * 每万份收益
     */
    private String perMillionFund;

    /**
     * 7日年化收益率（%）
     */
    private String annualized7Income;
}
