package com.yuan.quartz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangy on 2018/11/5.
 */
@Controller
public class MyController {

    @RequestMapping("login")
    private
    @ResponseBody
    String hello(@RequestParam(value = "username", required = false) String username,
                 @RequestParam(value = "password", required = false) String password) {
        return "Hello!" + username + "，密码是" + password;
    }

}
