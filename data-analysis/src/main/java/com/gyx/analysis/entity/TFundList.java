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
public class TFundList implements Serializable {

    private static final long serialVersionUID = 1L;

    private LocalDateTime crawlDateTime;

    /**
     * 基金大类
     */
    private String fundConcept;

    private String fundCode;

    private String fundName;

    private String fundUrl;

    private String fundBaUrl;

    private String fundArchiveUrl;

    private String fundType;

    private LocalDateTime fundDate;

    private String netVal;

    private String accumulatedNetVal;

    private String dailyGrowthRate;

    private String fundScale;

    private String fundManager;

    private String fundManagerUrl;

    private String subscriptionStatus;

    private String handFee;

    private String purchase;

    private String marketVal;

    private String hairCut;

    private LocalDateTime createDateTime;


}
