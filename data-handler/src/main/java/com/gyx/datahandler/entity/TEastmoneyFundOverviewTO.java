package com.gyx.datahandler.entity;

import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gyx.entity.fund.CompanyOverview;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zkna
 * @since 2021-07-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TCompanyOverview extends CompanyOverview {

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;
}
