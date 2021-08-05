package com.gyx.datafundcollect.entity.trans;

import lombok.Data;

@Data
public class LsjzDataList {
    /**
     * 净值日期
     */
    private String FSRQ;
    /**
     * 单位净值/每万份收益
     */
    private String DWJZ;
    /**
     * 累计净值/7日年化收益率（%）
     */
    private String LJJZ;
    /**
     *
     */
    private String SDATE;
    private String ACTUALSYI;
    private String NAVTYPE;
    /**
     * 日增长率
     */
    private String JZZZL;
    /**
     * 申购状态
     */
    private String SGZT;
    /**
     * 赎回状态
     */
    private String SHZT;
    private String FHFCZ;
    private String FHFCBZ;
    private String DTYPE;
    /**
     * 分红送配
     */
    private String FHSP;
}
