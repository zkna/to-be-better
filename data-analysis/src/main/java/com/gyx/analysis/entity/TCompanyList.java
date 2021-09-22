package com.gyx.analysis.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author gyx
 * @since 2021-08-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TCompanyList implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime crawlDateTime;

    private String companyId;

    private String companyName;

    private String companyDetail;

    private String companyBa;

    private LocalDateTime companySetDate;

    private String companyTianXiang;

    private String scaleNumber;

    private String scaleDate;

    private String fundNums;

    private String managerNums;

    private String managerUrl;

    private LocalDateTime createDateTime;


}
