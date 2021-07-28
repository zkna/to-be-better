package com.gyx.datahandler.entity;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zkna
 * @since 2021-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TCompanyOverview implements Serializable {

    private static final long serialVersionUID = 1L;

    private String totalAmount;

    private String totalNum;

    private String stockAmount;

    private String stockNum;

    private String mixedAmount;

    private String mixedNum;

    private String bondsAmount;

    private String bondsNum;

    private String indexAmount;

    private String indexNum;

    private String qdiiAmount;

    private String qdiiNum;

    private String currencyAmount;

    private String currencyNum;

    private Date expiryDate;

    private Integer fundCompanyNums;

    private Date createDateTime;

    private Date crawlDateTime;


}
