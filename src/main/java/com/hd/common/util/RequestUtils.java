package com.hd.common.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

public class RequestUtils {

    /**
     * payload 形式 json参数 的 Post 请求
     *
     * @param url
     * @param paramJsonStr
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse doPost(String url, String paramJsonStr) throws IOException {

        CloseableHttpClient tmpClient = HttpClients.createDefault();

        HttpPost tmpPost = new HttpPost(url);
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
        tmpPost.setConfig(requestConfig);

        tmpPost.setHeader("Content-type","application/json;charset=UTF-8");

        StringEntity tmpEntity = new StringEntity(paramJsonStr,"UTF-8");

        tmpPost.setEntity(tmpEntity);

        CloseableHttpResponse response = tmpClient.execute(tmpPost);

        return response;
    }

}
