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
 * TCompanyListEntity: 数据映射实体定义
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
    table = "t_company_list",
    schema = "fund_eastmoney"
)
public class TCompanyListEntity extends RichEntity {
  private static final long serialVersionUID = 1L;

  /**
   * id
   */
  @TableId("id")
  private Integer id;

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
   * 基金公司吧
   */
  @TableField("company_ba")
  private String companyBa;

  /**
   * 基金公司详情地址
   */
  @TableField("company_details")
  private String companyDetails;

  /**
   * 基金数
   */
  @TableField("company_fund_sum")
  private Integer companyFundSum;

  /**
   * 基金公司id
   */
  @TableField("company_code")
  private String companyCode;

  /**
   * 经理数
   */
  @TableField("company_manager_sum")
  private Integer companyManagerSum;

  /**
   * 经理url
   */
  @TableField("company_manager_url")
  private String companyManagerUrl;

  /**
   * 基金公司名称
   */
  @TableField("company_name")
  private String companyName;

  /**
   * 全部管理规模（亿元）
   */
  @TableField("company_scale")
  private String companyScale;

  /**
   * 规模纪录日期
   */
  @TableField("company_scale_date")
  private String companyScaleDate;

  /**
   * 基金成立日期
   */
  @TableField("company_set_date")
  private Date companySetDate;

  /**
   * 天相评级
   */
  @TableField("company_tianxiang_level")
  private String companyTianxiangLevel;

  /**
   * 爬取日期
   */
  @TableField("crawl_date")
  private String crawlDate;

  @Override
  public final Class entityClass() {
    return TCompanyListEntity.class;
  }
}
