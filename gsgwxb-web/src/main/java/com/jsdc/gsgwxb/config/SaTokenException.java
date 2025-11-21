package com.jsdc.gsgwxb.config;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.NotSafeException;
import com.jsdc.gsgwxb.utils.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * ClassName: SaTokenException
 * Description:
 * date: 2024/5/14 15:18
 *
 * @author bn
 */
@Slf4j
@RestControllerAdvice
public class SaTokenException {

    @ExceptionHandler(NotLoginException.class)
    public ResultInfo handlerNotLoginException(NotLoginException nle) {
        // 不同异常返回不同状态码
        String message = "";
        if (nle.getType().equals(NotLoginException.NOT_TOKEN)) {
            message = "未提供Token";
        } else if (nle.getType().equals(NotLoginException.INVALID_TOKEN)) {
            message = "未提供有效的Token";
        } else if (nle.getType().equals(NotLoginException.TOKEN_TIMEOUT)) {
            message = "登录信息已过期，请重新登录";
        } else if (nle.getType().equals(NotLoginException.BE_REPLACED)) {
            message = "已被顶下线";
        } else if (nle.getType().equals(NotLoginException.KICK_OUT)) {
            message = "已被踢下线";
        } else {
            message = "当前会话未登录";
        }
        // 返回给前端
        return ResultInfo.error(10401, message);
    }

    @ExceptionHandler
    public ResultInfo handlerNotRoleException(NotRoleException e) {
        return ResultInfo.error(10402, "无此角色：" + e.getRole());
    }

    @ExceptionHandler
    public ResultInfo handlerNotPermissionException(NotPermissionException e) {
        return ResultInfo.error(10403, "无此权限：" + e.getCode());
    }

    @ExceptionHandler
    public ResultInfo handlerNotSafeException(NotSafeException e) {
        return ResultInfo.error(10404, "二级认证异常：" + e.getMessage());
    }
}

