package com.jsdc.gsgwxb.utils;

import lombok.Data;

/**
 * @ClassName ResultInfo 统一返回类
 * @Description TODO
 * @Author xujian
 * @Date 2021/12/30 17:21
 * @Version 1.0
 */
@Data
public class ResultInfo<T> {
    private Integer code;
    private String msg;
    private T data;
    private String logMsg;
    private Integer dispose;

    /**
     * 成功
     *
     * @return
     */
    public static ResultInfo success() {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(0);
        resultInfo.setMsg("成功");
        return resultInfo;
    }

    public static ResultInfo success(String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(0);
        resultInfo.setMsg(msg);
        return resultInfo;
    }


    /**
     * 成功返回
     *
     * @param o 返回对象
     * @return
     */
    public static ResultInfo success(Object o) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(0);
        resultInfo.setMsg("成功");
        resultInfo.setData(o);
        return resultInfo;
    }

    public static ResultInfo success(String msg, Object o) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(0);
        resultInfo.setMsg(msg);
        resultInfo.setData(o);
        return resultInfo;
    }


    /**
     * 成功返回
     *
     * @param o 返回对象
     * @return
     */
    public static ResultInfo success(Object o, String logMsg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(0);
        resultInfo.setMsg("成功");
        resultInfo.setData(o);
        resultInfo.setLogMsg(logMsg);
        return resultInfo;
    }


    /**
     * 失败返回
     *
     * @param msg 错误信息
     * @return
     */
    public static ResultInfo error(String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(-1);
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public static ResultInfo error(Object o, String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(-1);
        resultInfo.setMsg(msg);
        resultInfo.setData(o);
        return resultInfo;
    }

    public static ResultInfo error(Integer code, String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(code);
        resultInfo.setMsg(msg);
        return resultInfo;
    }


    /**
     * 失败返回
     *
     * @param msg 错误信息
     * @return
     */
    public static ResultInfo error(String msg, String logMsg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(-1);
        resultInfo.setMsg(msg);
        resultInfo.setLogMsg(logMsg);
        return resultInfo;
    }


    /**
     * 自定义返回结果
     *
     * @param code 状态码
     * @param msg  返回信息
     * @param o    返回对象
     * @return
     */
    public static ResultInfo customize_response(Integer code, String msg, Object o, String logMsg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(code);
        resultInfo.setMsg(msg);
        resultInfo.setData(o);
        resultInfo.setLogMsg(logMsg);
        return resultInfo;
    }

    public static ResultInfo success(Object o, Integer dispose) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setCode(0);
        resultInfo.setMsg("成功");
        resultInfo.setData(o);
        resultInfo.setDispose(dispose);
        return resultInfo;
    }


}
