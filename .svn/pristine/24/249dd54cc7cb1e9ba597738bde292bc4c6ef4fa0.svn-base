package com.jsdc.gsgwxb.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.jsdc.gsgwxb.utils.ResultInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.security.auth.login.AccountExpiredException;
import java.nio.file.AccessDeniedException;
import java.sql.SQLException;
import java.util.Objects;

/**
 * 全局异常处理器
 *
 * @author gl
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String error_msg = "网络走丢了请稍后在试";

    private static final String access_error = "登录状态失效！";

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public ResultInfo<Void> businessException(CustomException e) {
        if (Objects.isNull(e.getCode())) {
            return ResultInfo.error(e.getMessage());
        }
        return ResultInfo.error(error_msg);
    }

    /**
     * 获取用户异常
     */
    @ExceptionHandler(UserException.class)
    public ResultInfo<Void> userException(UserException e) {
        if (Objects.isNull(e.getCode())) {
            return ResultInfo.error(e.getMessage());
        }
        return ResultInfo.error(10401,access_error);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResultInfo<Void> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return ResultInfo.error(HttpStatus.NOT_FOUND, error_msg);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResultInfo<Void> handleAuthorizationException(AccessDeniedException e) {
        log.error(e.getMessage());
        return ResultInfo.error(HttpStatus.FORBIDDEN, access_error);
    }

    @ExceptionHandler(NotLoginException.class)
    public ResultInfo<Void> handleNotLoginException(NotLoginException e) {
        log.error(e.getMessage());
        return ResultInfo.error(HttpStatus.UNAUTHORIZED, access_error);
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResultInfo<Void> handleAccountExpiredException(AccountExpiredException e) {
        log.error(e.getMessage(), e);
        return ResultInfo.error(error_msg);
    }


    @ExceptionHandler(DataAccessException.class)
    public ResultInfo<Void> handleDataAccessException(DataAccessException e) {
        log.error(e.getMessage(), e);
        return ResultInfo.error("数据异常！");
    }

    @ExceptionHandler(SQLException.class)
    public ResultInfo<Void> handleSQLException(SQLException e) {
        log.error(e.getMessage(), e);
        return ResultInfo.error(error_msg);
    }

    @ExceptionHandler(Exception.class)
    public ResultInfo<Void> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResultInfo.error(error_msg);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public ResultInfo<Void> validatedBindException(BindException e) {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return ResultInfo.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultInfo<Void> validExceptionHandler(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        return ResultInfo.error(error_msg);
    }

}
