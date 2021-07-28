package com.gyx.entity.fund;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FundCompanyOverview {

    private BigDecimal totalAmount;

    private Integer totalNum;

    private BigDecimal stockAmount;

    private Integer stockNum;

    private BigDecimal mixedAmount;

    private Integer mixedNum;

    private BigDecimal bondsAmount;

    private Integer bondsNum;

    private BigDecimal indexAmount;

    private Integer indexNum;

    private BigDecimal QDIIAmount;

    private Integer QDIINum;

    private BigDecimal currencyAmount;

    private Integer currencyNum;

    private String expiryDate;

    private Integer fundCompanyNums;

    private Date crawlDateTime;
}
