package com.gyx.datafundcollect.service;

import com.gyx.entity.fund.eastmoney.EastmoneyCompanyTO;
import com.gyx.entity.fund.eastmoney.EastmoneyFundOverviewTO;

import java.io.IOException;
import java.util.List;

public interface IFundCompany {

    public EastmoneyFundOverviewTO crawlCompanyOverview() throws IOException, InterruptedException;

    public List<EastmoneyCompanyTO> crawlCompanyList() throws IOException, InterruptedException;
}
