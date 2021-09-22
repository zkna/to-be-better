package com.gyx.analysis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author gyx
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FundCalcResult {

    private String fundCode;

    private String fundName;

    private String last;

    private String early;

    private BigDecimal result;
}
