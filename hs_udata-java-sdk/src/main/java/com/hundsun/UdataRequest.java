package com.hundsun;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: light-data
 * @Package: PACKAGE_NAME
 * @Description: note
 * @Author: lizheng
 * @CreateDate: 2021/8/5 15:36
 * @UpdateUser: lizheng
 * @UpdateDate: 2021/8/5 15:36
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2021 Hundsun Technologies Inc. All Rights Reserved
 **/
public class UdataRequest {

    /**
     * 应用Token
     */
    public static final String APPLICATION_TOKEN = "Application-Token";

    /**
     * 创建httpClient实例对象
     */
    public static final HttpClient HTTP_CLIENT = new HttpClient();

    public static String sendPost(String appToken, String uri, Map<String, Object> mapParams) throws IOException {
        // 设置httpClient连接主机服务器超时时间：15000毫秒
        HTTP_CLIENT.getHttpConnectionManager().getParams().setConnectionTimeout(15000);

        // 创建post请求方法实例对象
        PostMethod postMethod = new PostMethod(uri);
        // 设置post请求超时时间
        postMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 60000);
        postMethod.addRequestHeader("Content-Type", "application/json");
        postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");
        postMethod.addRequestHeader(APPLICATION_TOKEN, appToken);

        //设置body参数
        RequestEntity requestEntity = new StringRequestEntity(JSONObject.toJSON(mapParams).toString(), "application/json", "UTF-8");
        postMethod.setRequestEntity(requestEntity);
        HTTP_CLIENT.executeMethod(postMethod);

        InputStream inputStream = postMethod.getResponseBodyAsStream();
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        postMethod.releaseConnection();
        return result;
    }


    public static String sendGet(String appToken, String uri, Map<String, Object> mapParams) throws IOException {
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
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        getMethod.releaseConnection();
        return result;
    }


    public static void main(String[] args) throws IOException {
        String url = "https://udata.hs.net/udata/business/v1/app_services/basic_data/stock_list";
        String appToken = "***";
        Map<String, Object> mapParam = new HashMap<>(12);
        mapParam.put("fields","secu_abbr");
        String resultGet = UdataRequest.sendGet(appToken, url, mapParam);
        String resultPost = UdataRequest.sendPost(appToken, url, mapParam);
    }

}
