package com.gyx.datafundcollect.service;

import com.gyx.entity.fund.CompanyList;
import com.gyx.entity.fund.CompanyOverview;

import java.io.IOException;
import java.util.List;

public interface IFundCompany {

    public CompanyOverview crawlCompanyOverview() throws IOException, InterruptedException;

    public List<CompanyList> crawlCompanyList() throws IOException, InterruptedException;
}
