package com.gyx.datahandler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gyx.entity.fund.eastmoney.EastmoneyFundTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class TEastmoneyFundTO extends EastmoneyFundTO {

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO,value = "id")
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;
}
