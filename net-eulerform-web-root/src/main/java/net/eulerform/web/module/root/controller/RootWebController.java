package net.eulerform.web.module.root.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.eulerform.web.core.annotation.WebController;
import net.eulerform.web.core.base.controller.BaseController;
import net.eulerform.web.module.basedata.service.IBaseDataService;

@WebController
@Scope("prototype")
@RequestMapping("/")
public class RootWebController extends BaseController {
    
    @Resource
    private IBaseDataService baseDataService;
    
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        return "/about/index";
    }

    @RequestMapping(value = { "/login" }, method = RequestMethod.GET)
    public String login()
    {
        return "/root/login";
    }

    //@Override
    @RequestMapping(value={"/manage"},method=RequestMethod.GET)
    public String manage(HttpServletRequest request, Model model) {
        return "/root/manage";
    }
}
