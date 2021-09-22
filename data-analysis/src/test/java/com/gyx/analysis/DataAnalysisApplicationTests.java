package com.gyx.analysis;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gyx.analysis.entity.FundCalcResult;
import com.gyx.analysis.entity.TFundArchives;
import com.gyx.analysis.entity.TFundList;
import com.gyx.analysis.service.ITFundArchivesService;
import com.gyx.analysis.service.ITFundListService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
class DataAnalysisApplicationTests {

    @Autowired
    ITFundListService itFundListService;

    @Autowired
    ITFundArchivesService itFundArchivesService;

    @Test
    void contextLoads() {
        QueryWrapper<TFundList> qw = new QueryWrapper<>();
        qw.select("fund_code,fund_name").eq("fund_concept","开放式基金").groupBy("fund_code,fund_name");
        List<TFundList> tls = itFundListService.list(qw);
        List<FundCalcResult> lm = new ArrayList<>();
        for (TFundList t : tls) {
            QueryWrapper<TFundArchives> qwa = new QueryWrapper<>();
            qwa.eq("fund_code", t.getFundCode()).orderByDesc("net_date_time").last("limit 5");
            List<TFundArchives> c = itFundArchivesService.list(qwa);
            try {
                if (!Objects.isNull(c) && !c.isEmpty()) {
                    TFundArchives tLast = c.get(0);
                    TFundArchives tEarLy = c.get(c.size() - 1);
                    lm.add(new FundCalcResult(t.getFundCode(), t.getFundName(), tLast.getAccumulatedNet(), tEarLy.getAccumulatedNet(), new BigDecimal(tLast.getAccumulatedNet()).subtract(new BigDecimal(tEarLy.getAccumulatedNet()))));
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        List<FundCalcResult> slm = lm.stream().sorted(Comparator.comparing(FundCalcResult::getResult).reversed()).limit(10).collect(Collectors.toList());
        slm.stream().map(v -> JSON.toJSONString(v)).forEach(System.out::println);


    }

}
