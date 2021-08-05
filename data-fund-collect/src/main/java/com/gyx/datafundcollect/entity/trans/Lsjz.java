package com.gyx.datafundcollect.entity.trans;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Lsjz {


    private LsjzData Data;

    private Integer ErrCode;

    private Integer ErrMsg;

    private Integer TotalCount;

    private Integer Expansion;

    private Integer PageSize;

    private Integer PageIndex;

    public Lsjz(Integer totalCount) {
        TotalCount = totalCount;
    }
}
