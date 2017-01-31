package net.eulerframework.web.core.exception.web;

@SuppressWarnings("serial")
public abstract class ViewException extends WebException {
    public ViewException(String message, int code) {
        super(message, code);
    }

    public ViewException(String message, int code, Throwable cause) {
        super(message, code, cause);
    }

    protected ViewException(String message, int code, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, code, cause, enableSuppression, writableStackTrace);
    }
}
