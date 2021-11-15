package com.gyx.datahandler.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gyx.entity.fund.eastmoney.EastmoneyFundOverviewTO;
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
public class TEastmoneyFundOverviewTO extends EastmoneyFundOverviewTO {

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;
}
