package com.gyx.entity.fund;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 基金管理规模
 *
 * @author gyx
 */
@Data
public class CompanyOverview extends BaseEntity {

    @ApiModelProperty(value = "爬取时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crawlDateTime;

    @ApiModelProperty(value = "全部基金管理规模（亿元）")
    private String totalAmount;

    @ApiModelProperty(value = "全部基金数量（只）")
    private String totalNum;

    @ApiModelProperty(value = "股票型规模")
    private String stockAmount;

    @ApiModelProperty(value = "股票型数量")
    private String stockNum;

    @ApiModelProperty(value = "混合型规模")
    private String mixedAmount;

    @ApiModelProperty(value = "混合型数量")
    private String mixedNum;

    @ApiModelProperty(value = "债券型规模")
    private String bondsAmount;

    @ApiModelProperty(value = "债券型数量")
    private String bondsNum;

    @ApiModelProperty(value = "指数型规模")
    private String indexAmount;

    @ApiModelProperty(value = "指数型数量")
    private String indexNum;

    @ApiModelProperty(value = "QDII规模")
    private String QdiiAmount;

    @ApiModelProperty(value = "QDII数量")
    private String QdiiNum;

    @ApiModelProperty(value = "货币型规模")
    private String currencyAmount;

    @ApiModelProperty(value = "货币型数量")
    private String currencyNum;

    @ApiModelProperty(value = "截止日期")
    private Date expiryDate;

    @ApiModelProperty(value = "基金公司")
    private String fundCompanyNums;
}
