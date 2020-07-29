package com.online.edu.controller;

import com.onlin.common.ResultApi;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eService/user")
@CrossOrigin
public class LoginController {

    @PostMapping("/login")
    public ResultApi login(){
        return ResultApi.ok().data("token","admin");
    }

    @GetMapping("/info")
    public ResultApi info(){
        return ResultApi.ok().data("roles","[admin]").data("name","admin").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

}
