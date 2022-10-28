package com.example.demo.controller;

import com.alibaba.fastjson2.JSON;
import com.example.demo.annotation.ReturnMore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ViewController {

    // 返回视图
    @GetMapping("/toindex")
    public String toIndex(Model map){
        map.addAttribute("data", "data");
        return "index";
    }

    // 返回json数据
    @GetMapping(value = "/toindex", params = "methodUrl")
    @ResponseBody
    public Object returnData(@RequestParam String methodUrl){
        Map<String, Object> map = new HashMap<>();
        map.put("data", methodUrl);
        return map;
    }

    @RequestMapping("/getData")
    public String getData(HttpServletResponse response, @RequestParam Map<String, Object> params, Model map){
        // .... 做一系列的业务操作
        map.addAttribute("data", "data");
        // 如果为外部调用
        if(params.get("methodUrl") != null){
            // 设置响应的格式以及响应的编码
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            // 获取流，已流的形式返回json数据
            try(OutputStream os = response.getOutputStream()) {
                os.write(JSON.toJSONBytes(map.asMap()));
                os.flush();
            }catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        // 如果没有methodUrl，则返回视图名
        return "index";
    }

    @ReturnMore
    @RequestMapping("/testRet")
    public String testReturn(HttpServletResponse response, HttpServletRequest request, Model model){
        model.addAttribute("data", "something");
        return "index";
    }

}
