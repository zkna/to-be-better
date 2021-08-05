package com.gyx.entity.fund;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author gyx
 */
@Data
public class FundArchives extends BaseEntity{

    @ApiModelProperty(value = "爬取时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crawlDateTime;

    @ApiModelProperty(value = "基金大类")
    private String fundConcept;

    @ApiModelProperty(value = "基金代码")
    private String fundCode;

    @ApiModelProperty(value = "净值日期")
    @JSONField(format = "yyyy-MM-dd")
    private String netDateTime;

    @ApiModelProperty(value = "单位净值")
    private String unitNet;

    @ApiModelProperty(value = "累计净值")
    private String accumulatedNet;

    @ApiModelProperty(value = "日增长率")
    private String dayGrowRate;

    @ApiModelProperty(value = "申购状态")
    private String subscriptionStatus;

    @ApiModelProperty(value = "赎回状态")
    private String redemptionStatus;

    @ApiModelProperty(value = "分红配送")
    private String dividendDistribution;

    @ApiModelProperty(value = "每万份收益")
    private String perMillionFund;

    @ApiModelProperty(value = "7日年化收益率（%）")
    private String annualized7Income;
}
