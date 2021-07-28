package com.gyx.entity.fund;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金公司名称
 */
@Data
public class FundCompany {

    private String companyId;

    private String companyName;

    private String companyDetail;

    private String companyBa;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date companySetDate;

    private String companyTianXiang;

    private BigDecimal scaleNumber;

    private String scaleDate;

    private Integer fundNums;

    private Integer managerNums;

    private String managerUrl;
}
