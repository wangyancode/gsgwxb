package com.jsdc.gsgwxb.config;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static cn.dev33.satoken.SaManager.log;

public class IpLocationResolver {

    /**
     * 在线查询IP归属地
     */
    public static String getIpAddressByOnline(String ip) {
        try {
            //1、创建 URLConnction
            URL url = new URL("http://ip-api.com/json/" + ip + "?lang=zh-CN");

            //2、设置connection的属性
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            connection.setReadTimeout(20000);
            connection.setRequestProperty("content-type", "application/json; charset=utf-8");

            //3.连接
            connection.connect();

            //4.获取内容
            InputStream inputStream = connection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            String str = sb.toString();
            if (StringUtils.isNotEmpty(str)) {
                // string转map
                Map map = new HashMap<>();
                map = JSONObject.parseObject(str, map.getClass());
                String country = (String) map.getOrDefault("country", "-");
                String city = (String) map.getOrDefault("city", "-");
                String regionName = (String) map.getOrDefault("regionName", "-");
//                System.out.println("国家：" + country);
//                System.out.println("城市：" + city);
//                System.out.println("地区：" + regionName);
                return country + "|" + city + "|" + regionName;
            }
        } catch (Exception e) {
            log.error("在线查询IP地址异常，{}", e.getMessage());
            e.printStackTrace();
        }
        return "-|-|-";
    }

    public static String getCurrentIp() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://api.ipify.org")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                return response.body().string().trim();
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        System.err.println(getIpAddressByOnline("58.218.188.178"));
    }
}
