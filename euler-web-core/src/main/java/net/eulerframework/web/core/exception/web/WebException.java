package net.eulerframework.web.core.exception.web;

import net.eulerframework.common.util.StringUtils;
import net.eulerframework.web.core.i18n.Tag;

public abstract class WebException extends Exception {
    
    private String error;
    private int code;

    public WebException() {
        super();
        this.error = this.getClass().getSimpleName();
        if(this.error.endsWith("Exception")) {
            this.error.substring(0, this.error.length() - "Exception".length());
        }
        this.error = StringUtils.camelCaseToUnderLineCase(this.error);
        this.code = this.error.hashCode();
    }
    
    public WebException(String error, int code) {
        super();
        this.error = error;
        this.code = code;
    }

    public WebException(String message, String error, int code) {
        super(message);
        this.error = error;
        this.code = code;
    }

    public WebException(String error, int code, Throwable cause) {
        super(cause);
        this.error = error;
        this.code = code;
    }

    public WebException(String message, String error, int code, Throwable cause) {
        super(message, cause);
        this.error = error;
        this.code = code;
    }

    protected WebException(String message, String error, int code, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.error = error;
        this.code = code;
    }

    @Override
    public String getLocalizedMessage() {
        return Tag.i18n(this.getMessage());
    }

    public int getCode() {
        return this.code;
    }
    
    public String getError() {
        return this.error;
    }
}
