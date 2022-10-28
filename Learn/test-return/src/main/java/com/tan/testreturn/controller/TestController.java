package com.tan.testreturn.controller;

import com.tan.testreturn.annotation.ReturnMore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

//    @ReturnMore
    @GetMapping("/get2")
    @ResponseBody
    public Object retModelAndView(){
        Map map = new HashMap();
        map.put("d", "a");
        return map;
    }

//    @ReturnMore
    @GetMapping(value = "/get2", params = "a")
    public String retModelAndView2(Model model){
        return "view";
    }

    //
}
