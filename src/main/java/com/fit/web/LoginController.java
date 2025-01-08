package com.fit.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "123456";

    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public String index() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView login(String username, String password, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("forward:/");
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            request.getSession().setAttribute("user", username);
            mv.setViewName("redirect:/manager/students");
        } else {
            mv.addObject("username", username);
            mv.addObject("message", "username or password error ");
        }
        return mv;
    }

    @RequestMapping("/logout")
    public ModelAndView logOut(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return new ModelAndView("forward:/");
    }
}
