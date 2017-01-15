package net.eulerframework.web.core.base.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import net.eulerframework.common.util.StringTool;
import net.eulerframework.web.config.ProjectMode;
import net.eulerframework.web.config.WebConfig;
import net.eulerframework.web.core.base.response.AjaxResponse;
import net.eulerframework.web.core.base.response.HttpStatusResponse;
import net.eulerframework.web.core.exception.AjaxException;
import net.eulerframework.web.core.exception.ViewException;

public abstract class AbstractWebController extends BaseController {

    /**
     * 获取WebController的名字<br>
     * 要使用此方法, WebController必须以WebController结尾<br>
     * 获取到的名字为WebController的类名去掉WebController后缀,首字母变为小写
     * @return ExampleControllerWebController的名字为exampleController
     */
    private String getWebControllerName() {
        String className = this.getClass().getSimpleName();

        int indexOfWebController = className.lastIndexOf("WebController");

        if(indexOfWebController <= 0)
            throw new RuntimeException("If you want to use this.display(), WebController's class name must end with 'WebController'");

        return StringTool.toLowerCaseFirstChar(className.substring(0, className.lastIndexOf("WebController")));
    }

    /**
     * 获取主题名称,会优获取请求参数_theme,然后从cookie中获取,从请求参数中获取的_theme会被放到cookie中
     * @return 主题名称,默认为default
     */
    protected String theme() {
        String themeParamName = "_theme";

        String theme = this.getRequest().getParameter(themeParamName);
        if(StringTool.isNull(theme)) {
            Cookie[] cookies = this.getRequest().getCookies();

            if(cookies != null) {
                for(Cookie cookie : cookies) {
                    if(cookie.getName().equals(themeParamName)) {
                        theme = cookie.getValue();
                    }
                }
            }

        } else {
            Cookie cookie = new Cookie(themeParamName, theme);
            this.getResponse().addCookie(cookie);
        }

        if(StringTool.isNull(theme)) {
            theme = "default";
        }

        return theme;
    }

    /**
     * 显示view<br>
     * 如果只指定view名称,则默认为themeName/webControllerName/view.jsp<br>
     * 如果view以'/'开头,则显示themeName/view.jsp
     * @param view view name
     * @return view在JspPath下的路径
     */
    protected String display(String view) {
        Assert.isTrue(!StringTool.isNull(view), "view path is empty");

        if(!view.startsWith("/"))
            return this.theme() + "/" + this.getWebControllerName() + "/" + view;
        else
            return this.theme() + view;
    }
    
    /**
     * 发送重定向<br>
     * 不以'/'开头表示相对路径,
     * 以'/'表示绝对路径,不需要加contextPath
     * @param action 重定向目标
     * @return 重定向字符串
     */
    protected String redirect(String action) {
        Assert.isTrue(!StringTool.isNull(action), "action path is empty");

        if(!action.startsWith("/"))
            return "redirect:" + "/" + this.getWebControllerName() + "/" + action;
        else
            return "redirect:" + action;        
    }
    
    /**
     * 显示跳转页面
     * @param message 显示信息
     * @param target 跳转目标,不需要加contextPath
     * @param waitSeconds 等待时间(秒)
     * @return 跳转view
     */
    protected String jump(String message, String target, int waitSeconds) {
        message = message == null ? "PAGE_WILL_REDIRECT" : message;
        target = target == null ? "" : target;
        
        String contextPath = this.getServletContext().getContextPath();
        if(contextPath.equals("/"))
            contextPath = "";
        
        if(target.startsWith("/")) {
            if(target.length() == 1) {
                target = "";                
            }
            else {
                target = target.substring(1);
            }
        }

        this.addMessageToRequest(message);
        HttpServletRequest request = this.getRequest();
        request.setAttribute("target", contextPath + "/" + target);
        request.setAttribute("waitSeconds", waitSeconds);
        return this.display("/common/jump");
    }
    
    /**
     * 显示错误页面,错误信息为UNKNOWN_ERROR(未国际化前)
     * @return 错误页面
     */
    protected String error() {
        return this.error(null);
    }
    
    /**
     * 显示错误页面,并指定错误信息
     * @param message 未国际化前的错误信息,为<code>null</code>时为UNKNOWN_ERROR
     * @return 错误页面
     */
    protected String error(String message) {
        message = message == null ? "UNKNOWN_ERROR": message;

        this.addMessageToRequest(message);
        return this.display("/common/error");
        
    }
    
    /**
     * 显示成功页面,信息为SUCCESS(未国际化前)
     * @return 成功页面
     */
    protected String success() {
        return this.success(null);
    }
    
    /**
     * 显示成功页面,并指定信息
     * @param message 未国际化前的信息,为<code>null</code>时为SUCCESS
     * @return 成功页面
     */
    protected String success(String message) {
        message = message == null ? "SUCCESS" : message;

        this.addMessageToRequest(message);
        return this.display("/common/success");
        
    }
    
    private void addMessageToRequest(String message) {
        HttpServletRequest request = this.getRequest();
        request.setAttribute("message", message);
    }
    
    /**
     * 显示404页面
     * @return
     */
    protected String notfound() {
        return this.redirect("/error-404");
    }
    
    /**  
     * 用于在程序发生{@link ViewException}异常时统一返回错误信息 
     * @return  
     */  
    @ExceptionHandler({ViewException.class})   
    public String viewException(ViewException e) {
        if(WebConfig.getProjectMode().equals(ProjectMode.DEVELOP) ||
                WebConfig.getProjectMode().equals(ProjectMode.DEBUG)) {
            this.logger.error("Error Code: " + e.getCode() + "message: " + e.getMessage(), e);
        }
        return this.error(e.getMsg());
    }
    
    /**  
     * 用于在程序发生{@link AjaxException}异常时统一返回错误信息 
     * @return  
     */  
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({AjaxException.class})   
    public AjaxResponse<?> ajaxException(AjaxException e) {
        if(WebConfig.getProjectMode().equals(ProjectMode.DEVELOP) ||
                WebConfig.getProjectMode().equals(ProjectMode.DEBUG)) {
            this.logger.error("Error Code: " + e.getCode() + "message: " + e.getMessage(), e);
        }
        return new AjaxResponse<>(e);
    }
    
    /**  
     * 用于在程序发生{@link Exception}异常时统一返回错误信息 
     * @return  
     */  
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})   
    public Object exception(Exception e) {
        this.logger.error(e.getMessage(), e);
        if(WebConfig.getProjectMode().equals(ProjectMode.DEVELOP) ||
                WebConfig.getProjectMode().equals(ProjectMode.DEBUG)) {
            return new HttpStatusResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } else {
            return new HttpStatusResponse(HttpStatus.INTERNAL_SERVER_ERROR);            
        }
    }

}
