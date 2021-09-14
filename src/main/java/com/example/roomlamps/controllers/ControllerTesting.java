package com.example.roomlamps.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ControllerTesting {


    @GetMapping("/testing")
    @ResponseBody
    public String getTesting() {
        return "testing";
    }




}
