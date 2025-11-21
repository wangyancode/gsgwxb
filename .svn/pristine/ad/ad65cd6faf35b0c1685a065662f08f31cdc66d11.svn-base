package com.jsdc.gsgwxb.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 枚举
 */
public enum CommonEnum {

    //默认密码
    DEFAULT_PASS_WORD("Jsdc@0516", "默认密码"),
    BRACELET_BUNK_BEDS_ONE("0", "普通"),
    BRACELET_BUNK_BEDS_TWO("1", "上"),
    BRACELET_BUNK_BEDS_THREE("2", "下");
    private final String code;

    private final String msg;


    CommonEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    /**
     * 根据值value 得到 描述desc
     * @param code
     * @return
     */
    public static String getMsgByCode(String code) {
        if (StringUtils.isBlank(code)) {
            return "";
        }
        for (CommonEnum myEnum : CommonEnum.values()) {
            if (Objects.equals(myEnum.getCode(), code)) {
                return myEnum.getMsg();
            }
        }
        return "";
    }

    /**
     * 根据描述desc 得到 值value
     * @param msg
     * @return
     */
    public static String getCodeByMsg(String msg) {
        if (StringUtils.isBlank(msg)) {
            return "";
        }
        for (CommonEnum myEnum : CommonEnum.values()) {
            if (myEnum.getMsg().equals(msg)) {
                return myEnum.getCode();
            }
        }
        return "";
    }
}
