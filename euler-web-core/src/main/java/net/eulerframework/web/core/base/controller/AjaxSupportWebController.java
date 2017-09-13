package net.eulerframework.web.core.base.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;

import net.eulerframework.web.core.base.response.ErrorResponse;
import net.eulerframework.web.core.exception.web.DefaultWebRuntimeException;
import net.eulerframework.web.core.exception.web.WebRuntimeException;
import net.eulerframework.web.core.exception.web.WebError;

@ResponseBody
public abstract class AjaxSupportWebController extends AbstractWebController {

    /**
     * 用于在程序发生{@link AccessDeniedException}异常时统一返回错误信息
     * 
     * @return 包含错误信息的Ajax响应体
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public Object accessDeniedException(AccessDeniedException e) {
        return new ErrorResponse(new DefaultWebRuntimeException(e.getMessage(), WebError.ACCESS_DENIED, e));
    }

    /**
     * 用于在程序发生{@link MissingServletRequestParameterException}异常时统一返回错误信息
     * 
     * @return 包含错误信息的Ajax响应体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Object missingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new ErrorResponse(new DefaultWebRuntimeException(e.getMessage(), WebError.ILLEGAL_PARAMETER, e));
    }

    /**
     * 用于在程序发生{@link IllegalArgumentException}异常时统一返回错误信息
     * 
     * @return 包含错误信息的Ajax响应体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public Object illegalArgumentException(IllegalArgumentException e) {
        return new ErrorResponse(new DefaultWebRuntimeException(e.getMessage(), WebError.ILLEGAL_ARGUMENT, e));
    }

    /**
     * 用于在程序发生{@link WebRuntimeException}异常时统一返回错误信息
     * 
     * @return 包含错误信息的Ajax响应体
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebRuntimeException.class)
    public Object webRuntimeException(WebRuntimeException e) {
        return new ErrorResponse(e);
    }

    /**
     * 用于在程序发生{@link Exception}异常时统一返回错误信息
     * 
     * @return 包含错误信息的Ajax响应体
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Object exception(Exception e) {
        this.logger.error(e.getMessage(), e);
        return new ErrorResponse();
    }
}
