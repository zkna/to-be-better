package com.gyx.entity.fund.eastmoney;

import com.alibaba.fastjson.annotation.JSONField;
import com.gyx.entity.fund.BaseCrawlEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金公司 列表
 * @author gyx
 */
@Data
public class EastmoneyCompanyListTO extends BaseCrawlEntity {

    /**
     * 公司ID
     */
    private String companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司详情
     */
    private String companyDetail;

    /**
     * 公司吧
     */
    private String companyBa;

    /**
     * 成立时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date companySetDate;

    /**
     * 天相评级
     */
    private String companyTianXiangLevel;

    /**
     * 全部管理规模（亿元）
     */
    private BigDecimal companyScaleSum;

    /**
     * 规模记录日期
     */
    private String companyScaleDate;

    /**
     * 全部基金数
     */
    private Integer companyFundSum;

    /**
     * 全部经理数
     */
    private Integer companyManagerSum;

    /**
     * 经理Url
     */
    private String companyManagerUrl;


}
