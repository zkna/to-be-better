package com.gyx.entity.fund.eastmoney;

import com.alibaba.fastjson.annotation.JSONField;
import com.gyx.entity.fund.BaseCrawlEntity;
import lombok.Data;

import java.util.Date;

/**
 * 天天基金 基金列表
 * @date
 * @author gyx
 */
@Data
public class EastmoneyFundListTO extends BaseCrawlEntity {

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
     * 公司代码
     */
    private String companyCode;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 基金地址
     */
    private String fundUrl;

    /**
     * 基金吧
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
     * 基金日期（获取基金的日期，非成立日）
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date fundDate;

    /**
     * 万份收益
     */
    private String fundNetVal;

    /**
     * 累计净值
     */
    private String fundAccumulatedNetVal;

    /**
     * 7日年化
     */
    private String fundDailyGrowthRate;

    /**
     * 基金规模
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
    private String fundSubscriptionStatus;

    /**
     * 手续费
     */
    private String fundHandFee;

    /**
     * 购买状态
     */
    private String fundPurchase;

    /**
     * 市价
     */
    private String fundMarketVal;

    /**
     * 折价率
     */
    private String fundHairCut;

}
