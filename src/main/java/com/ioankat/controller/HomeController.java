package com.ioankat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showLanding(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("dog"));
//        System.out.println(session.getAttribute("user"));

        return "landing";
    }

    @GetMapping("/access-denied")
    public String show404() {

        return "access-denied";
    }

    @GetMapping("/employees")
    public String showHome() {
        return "home";
    }

    // add request mapping for /leaders

    @GetMapping("/leaders")
    public String showLeaders() {

        return "leaders";
    }

    // add request mapping for /systems

    @GetMapping("/systems")
    public String showSystems() {

        return "systems";
    }
}
