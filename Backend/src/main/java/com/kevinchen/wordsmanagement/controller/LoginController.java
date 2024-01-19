package com.kevinchen.wordsmanagement.controller;


import com.kevinchen.wordsmanagement.pojo.Result;
import com.kevinchen.wordsmanagement.pojo.User;
import com.kevinchen.wordsmanagement.service.AdminService;
import com.kevinchen.wordsmanagement.service.UserService;
import com.kevinchen.wordsmanagement.utils.JwtUtils;
import io.jsonwebtoken.Jwt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/admin")
    public Result adminLogin(@RequestBody User user){
        log.info("管理员登录:{}", user);

        User resUser = adminService.adminLogin(user);

        if(resUser == null) {
            log.info("管理员用户名密码错误");
            return Result.error("管理员用户名或密码错误");
        }
        else {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", resUser.getId());
            claims.put("username", resUser.getUsername());
            claims.put("identify", "admin");
            String jwt = JwtUtils.generateJwt(claims);
            log.info("登陆成功");
            return Result.success(jwt);
        }
    }

    @PostMapping("/user")
    public Result userLogin(@RequestBody User user){
        log.info("用户登录:{}", user);

        User resUser = userService.userLogin(user);

        if(resUser == null) {
            log.info("用户名密码错误");
            return Result.error("用户名或密码错误");
        }
        else {
            Map<String, Object> claims = new HashMap<>();
            claims.put("id", resUser.getId());
            claims.put("username", resUser.getUsername());
            claims.put("identify", "user");
            String jwt = JwtUtils.generateJwt(claims);
            log.info("登陆成功");
            return Result.success(jwt);
        }
    }
}
