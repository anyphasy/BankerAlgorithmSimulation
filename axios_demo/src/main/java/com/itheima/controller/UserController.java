package com.itheima.controller;

import com.itheima.pojo.Result;
import com.itheima.pojo.User;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/article")
@RequestMapping("/user")
@CrossOrigin//支持跨域
public class UserController {
    //新增文章
    @PostMapping("/register1")
    public Result register1(String username,String password,String rePassword) {
//        user.add(article);
//        user.forEach(System.out::println);
//        System.out.println("//新增文章");
//        System.out.println("return \"新增成功\";");
        System.out.println(username);
        System.out.println(password);
        System.out.println(rePassword);


        return Result.success();
    }
    @PostMapping("/register")
    public Result register(@RequestBody User user) {
//        user.add(article);
//        user.forEach(System.out::println);
//        System.out.println("//新增文章");
//        System.out.println("return \"新增成功\";");
//        System.out.println(username);
//        System.out.println(password);
//        System.out.println(rePassword);
        System.out.println(user);


        return Result.success();
    }
}
