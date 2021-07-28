package com.gyx.datafundcollect.service;

import com.gyx.entity.fund.FundCompany;
import com.gyx.entity.fund.FundCompanyOverview;

import java.io.IOException;
import java.util.List;

public interface IFundCompany {

    public FundCompanyOverview crawlCompanyOverview() throws IOException, InterruptedException;

    public List<FundCompany> crawlFundCompany() throws IOException, InterruptedException;
}
