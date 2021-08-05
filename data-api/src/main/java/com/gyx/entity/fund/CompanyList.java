package com.gyx.entity.fund;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 基金公司名称
 * @author gyx
 */
@Data
public class CompanyList extends BaseEntity {

    @ApiModelProperty(value = "爬取时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date crawlDateTime;

    @ApiModelProperty(value = "公司ID")
    private String companyId;

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司详情")
    private String companyDetail;

    @ApiModelProperty(value = "公司吧")
    private String companyBa;

    @ApiModelProperty(value = "成立时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date companySetDate;

    @ApiModelProperty(value = "天相评级")
    private String companyTianXiang;

    @ApiModelProperty(value = "全部管理规模（亿元）")
    private BigDecimal scaleNumber;

    @ApiModelProperty(value = "规模记录日期")
    private String scaleDate;

    @ApiModelProperty(value = "全部基金数")
    private Integer fundNums;

    @ApiModelProperty(value = "全部经理数")
    private Integer managerNums;

    @ApiModelProperty(value = "经理Url")
    private String managerUrl;
}
