import com.hundsun.UdataRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件描述
 *
 * @ProductName: Hundsun HEP
 * @ProjectName: light-data
 * @Package: PACKAGE_NAME
 * @Description: note
 * @Author: lizheng
 * @CreateDate: 2021/8/6 13:16
 * @UpdateUser: lizheng
 * @UpdateDate: 2021/8/6 13:16
 * @UpdateRemark: The modified content
 * @Version: 1.0
 * <p>
 * Copyright © 2021 Hundsun Technologies Inc. All Rights Reserved
 **/
public class UdataTest {

    public static void main(String[] args) throws IOException {
        String url = "https://udata.hs.net/udata/business/v1/app_services/basic_data/stock_list";
        String appToken = "***";
        Map<String, Object> mapParam = new HashMap<>(12);
        mapParam.put("fields","secu_abbr");
        String resultGet = UdataRequest.sendGet(appToken, url, mapParam);
        String resultPost = UdataRequest.sendPost(appToken, url, mapParam);
    }

}
