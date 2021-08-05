package com.gyx.datahandler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gyx.entity.fund.CompanyList;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author gyx
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TCompanyList extends CompanyList {

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;
}
