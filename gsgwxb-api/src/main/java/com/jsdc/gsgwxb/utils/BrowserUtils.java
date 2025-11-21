package com.jsdc.gsgwxb.utils;

import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BrowserUtils {
    //获取浏览器名称
    public static String getBrowser(HttpServletRequest request) {
        String userAgentStr = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
        return userAgent.getBrowser().getName();
    }

    public static String getClientInfo(HttpServletRequest request) {
        String userAgentStr = request.getHeader("User-Agent");
        if (userAgentStr == null) {
            return "Unknown Client";
        }

        // 检查常见API工具
        if (userAgentStr.contains("PostmanRuntime")) return "Postman";
        if (userAgentStr.contains("apipost")) return "ApiPost";
        if (userAgentStr.contains("curl")) return "cURL";
        if (userAgentStr.contains("Insomnia")) return "Insomnia";

        // 解析标准浏览器
        try {
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentStr);
            if (userAgent.getBrowser() != null && userAgent.getBrowser().getName() != null) {
                return userAgent.getBrowser().getName();
            }
        } catch (Exception e) {
            // 记录日志但不影响主流程
            System.err.println("User-Agent解析失败: " + userAgentStr);
        }

        // 提取客户端类型（通用逻辑）
        String[] tokens = userAgentStr.split("[ /;()]");
        if (tokens.length > 0) {
            return tokens[0];
        }

        return "Unknown";
    }

    // 获取完整信息示例
    public static Map<String, String> getFullInfo(HttpServletRequest request) {
        String userAgentStr = request.getHeader("User-Agent");
        if (userAgentStr == null) {
            return null;
        }

        // 检查常见API工具
        if (userAgentStr.contains("PostmanRuntime")) return null;
        if (userAgentStr.contains("apipost")) return null;
        if (userAgentStr.contains("curl")) return null;
        if (userAgentStr.contains("Insomnia")) return null;

        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Map<String, String> map = new HashMap<>();
        map.put("browser", userAgent.getBrowser().getName());
        map.put("browserVersion", Optional.ofNullable(userAgent.getBrowserVersion()).map(Version::getVersion).orElse("Unknown"));
        map.put("os", userAgent.getOperatingSystem().getName());
        map.put("deviceType", userAgent.getOperatingSystem().getDeviceType().getName());
        return map;
    }
}
