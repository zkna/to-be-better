package com.zkna.fund.tofundhandler.entity;

import cn.org.atool.fluent.mybatis.annotation.FluentMybatis;
import cn.org.atool.fluent.mybatis.annotation.TableField;
import cn.org.atool.fluent.mybatis.annotation.TableId;
import cn.org.atool.fluent.mybatis.base.RichEntity;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * TFundListEntity: 数据映射实体定义
 *
 * @author Powered By Fluent Mybatis
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Data
@Accessors(
    chain = true
)
@EqualsAndHashCode(
    callSuper = false
)
@FluentMybatis(
    table = "t_fund_list",
    schema = "fund_eastmoney"
)
public class TFundListEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @TableId(
      value = "id",
      auto = false
  )
  private String id;

  /**
   * 创建时间
   */
  @TableField(
      value = "create_date_time",
      insert = "now()"
  )
  private Date createDateTime;

  /**
   * 修改时间
   */
  @TableField(
      value = "modify_date_time",
      insert = "now()",
      update = "now()"
  )
  private Date modifyDateTime;

  /**
   * 累计净值
   */
  @TableField("fund_accumulate_net_val")
  private String fundAccumulateNetVal;

  /**
   * 基金档案地址
   */
  @TableField("fund_archive")
  private String fundArchive;

  /**
   * 基金吧地址
   */
  @TableField("fund_ba")
  private String fundBa;

  /**
   * 基金代码
   */
  @TableField("fund_code")
  private String fundCode;

  /**
   * 基金大类
   */
  @TableField("fund_concept")
  private String fundConcept;

  /**
   * 日增长率
   */
  @TableField("fund_daily_growth_rate")
  private String fundDailyGrowthRate;

  /**
   * 基金日期(这条数据的日期)
   */
  @TableField("fund_date")
  private Date fundDate;

  /**
   * 折价率
   */
  @TableField("fund_hair_cut")
  private String fundHairCut;

  /**
   * 手续费
   */
  @TableField("fund_hand_fee")
  private String fundHandFee;

  /**
   * 基金经理
   */
  @TableField("fund_manager")
  private String fundManager;

  /**
   * 基金经理url
   */
  @TableField("fund_manager_url")
  private String fundManagerUrl;

  /**
   * 市价
   */
  @TableField("fund_market_val")
  private String fundMarketVal;

  /**
   * 基金名称
   */
  @TableField("fund_name")
  private String fundName;

  /**
   * 基金公司id
   */
  @TableField("company_code")
  private String companyCode;

  /**
   * 基金公司名称
   */
  @TableField("company_name")
  private String companyName;

  /**
   * 万份收益
   */
  @TableField("fund_net_val")
  private String fundNetVal;

  /**
   * 购买状态
   */
  @TableField("fund_purchase_status")
  private String fundPurchaseStatus;

  /**
   * 基金规模
   */
  @TableField("fund_scale")
  private String fundScale;

  /**
   * 申购状态
   */
  @TableField("fund_subscription_status")
  private String fundSubscriptionStatus;

  /**
   * 基金类型
   */
  @TableField("fund_type")
  private String fundType;

  /**
   * 基金url
   */
  @TableField("fund_url")
  private String fundUrl;

  /**
   * 爬取日期
   */
  @TableField("crawl_date")
  private String crawlDate;

  @Override
  public final Class entityClass() {
    return TFundListEntity.class;
  }
}
