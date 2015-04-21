package io.hosuaby.restful.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    @ResponseBody
    public String getGreeting() {
        return "I am your father, Luke!";
    }

}
