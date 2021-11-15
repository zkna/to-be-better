package com.gyx.entity.fund;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 基金总览
 * @author gyx
 */
@Data
public class CompanyOverview extends BaseCrawlEntity {

    /**
     * 全部基金管理规模（亿元）
     */
    private String totalAmount;

    /**
     * 全部基金数量（只）
     */
    private String totalNum;

    /**
     * 股票型规模
     */
    private String stockAmount;

    /**
     * 股票型数量
     */
    private String stockNum;

    /**
     * 混合型规模
     */
    private String mixedAmount;

    /**
     * 混合型数量
     */
    private String mixedNum;

    /**
     * 债券型规模
     */
    private String bondsAmount;

    /**
     * 债券型数量
     */
    private String bondsNum;

    /**
     * 指数型规模
     */
    private String indexAmount;

    /**
     * 指数型数量
     */
    private String indexNum;

    /**
     * QDII规模
     */
    private String QdiiAmount;

    /**
     * QDII数量
     */
    private String QdiiNum;

    /**
     * 货币型规模
     */
    private String currencyAmount;

    /**
     * 货币型数量
     */
    private String currencyNum;

    /**
     * 截止日期
     */
    private Date expiryDate;

    /**
     * 基金公司
     */
    private String fundCompanyNums;
}
