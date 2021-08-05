package com.gyx.entity.fund;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 基金档案 概述
 * @author gyx
 */
@Data
public class FundList extends BaseEntity{

    @ApiModelProperty(value = "爬取时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crawlDateTime;

    @ApiModelProperty(value = "基金大类")
    private String fundConcept;

    @ApiModelProperty(value = "基金代码")
    private String fundCode;

    @ApiModelProperty(value = "基金名称")
    private String fundName;

    @ApiModelProperty(value = "基金地址")
    private String fundUrl;

    @ApiModelProperty(value = "吧")
    private String fundBaUrl;

    @ApiModelProperty(value = "基金档案")
    private String fundArchiveUrl;

    @ApiModelProperty(value = "基金类型")
    private String fundType;

    @ApiModelProperty(value = "基金日期")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date fundDate;

    @ApiModelProperty(value = "万份收益")
    private String netVal;

    @ApiModelProperty(value = "累计净值")
    private String accumulatedNetVal;

    @ApiModelProperty(value = "7日年化")
    private String dailyGrowthRate;

    @ApiModelProperty(value = "基金规模")
    private String fundScale;

    @ApiModelProperty(value = "基金经理")
    private String fundManager;

    @ApiModelProperty(value = "基金经理地址")
    private String fundManagerUrl;

    @ApiModelProperty(value = "申购状态")
    private String subscriptionStatus;

    @ApiModelProperty(value = "手续费")
    private String handFee;

    @ApiModelProperty(value = "购买状态")
    private String purchase;

    @ApiModelProperty(value = "市价")
    private String marketVal;

    @ApiModelProperty(value = "折价率")
    private String hairCut;
}
