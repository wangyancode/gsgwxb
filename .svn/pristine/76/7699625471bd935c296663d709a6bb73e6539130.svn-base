package com.jsdc.gsgwxb.utils;


/**
 * 常用工具类
 */
public class PassUtils {

    //密码验证
    public static boolean betterPwd(String pwd) {
//        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[~!@#$%^&*()_+`\\-={}:\";'<>,.\\/]).{8,16}$";
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{6,12}$";
        boolean stringMatches = pwd.matches(regex);
        return stringMatches;
    }

    // 正则表达式用于验证密码强度
    private static final String PASSWORD_REGEX =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$";

    /**
     * 验证密码是否符合强密码规则
     *
     * @param password 待验证的密码
     * @return 如果密码符合规则返回true，否则返回false
     */
    public static boolean validateStrongPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
}
