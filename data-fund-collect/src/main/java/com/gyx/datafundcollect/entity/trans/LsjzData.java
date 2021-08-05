package com.gyx.datafundcollect.entity.trans;

import lombok.Data;

import java.util.List;

@Data
public class LsjzData {
    private List<LsjzDataList> LSJZList;
    private String FundType;
    private String SYType;
    //TODO
    private Boolean isNewType;
    private String Feature;
}
