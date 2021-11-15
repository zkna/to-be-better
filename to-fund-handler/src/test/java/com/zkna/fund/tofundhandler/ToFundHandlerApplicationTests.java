package com.zkna.fund.tofundhandler;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zkna.fund.tofundhandler.entity.TFundListEntity;
import com.zkna.fund.tofundhandler.mapper.TFundListMapper;
import com.zkna.fund.tofundhandler.wrapper.TFundListQuery;
import lombok.SneakyThrows;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@SpringBootTest(classes = ToFundHandlerApplication.class)
class ToFundHandlerApplicationTests {

    /**
     * 应用Token
     */
    public static final String APPLICATION_TOKEN = "Application-Token";

    /**
     * 创建httpClient实例对象
     */
    public static final HttpClient HTTP_CLIENT = new HttpClient();

//    @Autowired
//    TCompanyListDao tCompanyListDao;
//
//    @Autowired
//    TCompanyListMapper tCompanyListMapper;

    @Qualifier("fmTFundListMapper")
    @Autowired
    TFundListMapper tFundListMapper;

    CountDownLatch cdl = null;
    private ExecutorService fixedThreadPool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

    class FundListRunnable implements Runnable{
        private String fundCode;

        public FundListRunnable(String fundCode) {
            this.fundCode = fundCode;
        }
        String url = "https://udata.hs.net/udata/business/v1/app_services/fund_basic_data/fund_list";
        String appToken = "38C63VPxEfKpKzGFyg8ssu_92thOn8jz97joD9lYmMuguc5h3uzQpWeMS3cHYMN4";
        @SneakyThrows
        @Override
        public void run() {
            String resultGet = HttpRequest.get(url).header("Application-Token",appToken).header("Content-Type", "application/json")
                    .charset("utf-8").form("symbols", fundCode+".OF").execute().body();
            System.out.println(resultGet);
            cdl.countDown();
        }



    }

    @SneakyThrows
    @Test
    void contextLoads() {
        TFundListQuery query = new TFundListQuery().select.end();

        List<TFundListEntity> summary = tFundListMapper.listEntity(query);
         cdl = new CountDownLatch(summary.size());
        for (TFundListEntity t : summary) {
            fixedThreadPool.execute(new FundListRunnable(t.getFundCode()));
        }

        cdl.await();

    }




    public String sendGet(String appToken, String uri, Map<String, Object> mapParams) throws IOException {
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        HTTP_CLIENT.getHttpConnectionManager().getParams().setConnectionTimeout(15000);
        // 创建GET请求方法实例对象
        GetMethod getMethod = new GetMethod(uri);
        // 设置get请求超时时间
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        getMethod.addRequestHeader("Content-Type", "application/json");
        getMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        getMethod.addRequestHeader(APPLICATION_TOKEN, appToken);

        List<NameValuePair> list = new ArrayList<>();
        for (Map.Entry<String, Object> entry : mapParams.entrySet()) {
            NameValuePair nameValuePair = new NameValuePair();
            nameValuePair.setName(entry.getKey());
            nameValuePair.setValue(entry.getValue().toString());
            list.add(nameValuePair);
        }

        NameValuePair[] nameValuePairs = new NameValuePair[list.size()];
        nameValuePairs = list.toArray(nameValuePairs);
        getMethod.setQueryString(nameValuePairs);

        HTTP_CLIENT.executeMethod(getMethod);

        InputStream inputStream = getMethod.getResponseBodyAsStream();
//        JSONObject.parseObject(inputStream, Feature.OrderedField);
//        JSON
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        getMethod.releaseConnection();
        return result;
    }


    public static void main(String[] args) throws IOException {

    }

}
