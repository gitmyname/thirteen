package org.thirteen.datamation.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.thirteen.datamation.auth.exception.ForbiddenException;
import org.thirteen.datamation.auth.exception.LockedAccountException;
import org.thirteen.datamation.auth.exception.NotFoundException;
import org.thirteen.datamation.auth.exception.UnauthorizedException;
import org.thirteen.datamation.exception.BusinessException;
import org.thirteen.datamation.web.ResponseResult;

import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;
import java.sql.SQLException;

/**
 * @author Aaron.Sun
 * @description controller层统一异常处理
 * @date Created in 21:35 2018/5/30
 * @modified by
 */
@RestControllerAdvice
public class ControllerExceptionHandleAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandleAdvice.class);

    /**
     * 可以直接写@ExceptionHandler,不指明异常类，会自动映射
     */
    @ExceptionHandler({SignatureException.class, UnauthorizedException.class})
    public ResponseResult<String> handlerUnauthorized() {
        return ResponseResult.unauthorized();
    }

    @ExceptionHandler(LockedAccountException.class)
    public ResponseResult<String> handlerLockedAccount() {
        return ResponseResult.locked();
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseResult<String> handlerForbidden() {
        return ResponseResult.forbidden();
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseResult<String> handlerNotFound(NotFoundException e) {
        return ResponseResult.notFind(e.getMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseResult<String> handlerBusiness(BusinessException e) {
        return ResponseResult.error(e.getMessage());
    }

    @ExceptionHandler
    public ResponseResult<String> handler(HttpServletResponse res, Exception e) {
        logger.info("Restful Http请求发生异常");
        if (res.getStatus() == HttpStatus.BAD_REQUEST.value()) {
            res.setStatus(HttpStatus.OK.value());
        }
        logger.error("请求异常：", e);
        // 针对不同的异常类型，返回对应的信息
        if (e instanceof HttpRequestMethodNotSupportedException) {
            return ResponseResult.error(e.getMessage());
        }
        if (e instanceof NullPointerException) {
            return ResponseResult.error("发生空指针异常");
        }
        if (e instanceof IllegalArgumentException) {
            return ResponseResult.bad("请求参数类型不匹配");
        }
        if (e instanceof SQLException) {
            return ResponseResult.error("数据库访问异常");
        }
        return ResponseResult.error("请求失败,请联系管理员");
    }

}
