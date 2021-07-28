package com.gyx.entity.fund;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class FundArchives {
    /**
     * 基金大类
     */
    private String fundConcept;

    /**
     * 基金代码
     */
    private String fundCode;

    /**
     * 基金名称
     */
    private String fundName;

    /**
     * 基金地址
     */
    private String fundUrl;

    /**
     * 基金 吧
     */
    private String fundBaUrl;

    /**
     * 基金档案
     */
    private String fundArchiveUrl;

    /**
     * 基金类型
     */
    private String fundType;

    /**
     * 基金日期
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date fundDate;

    /**
     * 单位净值/万份收益
     */
    private String netVal;

    /**
     * 累计净值
     */
    private String accumulatedNetVal;

    /**
     * 日增长率/7日年化
     */
    private String dailyGrowthRate;

    /**
     * 规模（亿元）
     */
    private String fundScale;

    /**
     * 基金经理
     */
    private String fundManager;

    /**
     * 基金经理地址
     */
    private String fundManagerUrl;

    /**
     * 申购状态
     */
    private String subscriptionStatus;

    /**
     * 手续费
     */
    private String handFee;

    /**
     * 购买状态
     */
    private String purchase;

    /**
     * 市价
     */
    private String marketVal;

    /**
     * 折价率
     */
    private String hairCut;
}
