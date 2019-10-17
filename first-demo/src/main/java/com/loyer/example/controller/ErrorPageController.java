package com.loyer.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author kuangq
 * @projectName example
 * @title ErrorPageController
 * @description 异常响应跳转Controller
 * @date 2019-10-15 15:23
 */
@Controller
@RequestMapping("error")
public class ErrorPageController {

    @RequestMapping("e404")
    public ModelAndView e404(Model model) {
        return new ModelAndView("error/404");
    }

    @RequestMapping("e500")
    public ModelAndView e500(Model model) {
        return new ModelAndView("error/404");
    }
}
