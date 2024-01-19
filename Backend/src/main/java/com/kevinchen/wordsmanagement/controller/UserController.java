package com.kevinchen.wordsmanagement.controller;

import com.kevinchen.wordsmanagement.pojo.Result;
import com.kevinchen.wordsmanagement.pojo.Word;
import com.kevinchen.wordsmanagement.service.UserService;
import com.kevinchen.wordsmanagement.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/front")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Result wordsInfo() {
        List<Word> words = userService.wordsInfo();
        log.info("拿到了数据： {}", words);
        return Result.success(words);
    }

    @GetMapping("/search")
    public Result searchWord(String search){
        List<Word> words = userService.searchWord(search);
        log.info("根据 {} 查询到如下数据: {}", search, words);
        return Result.success(words);
    }

    @GetMapping("/detail")
    public Result wordDetail(Integer id){
        Word word = userService.searchById(id);
        return Result.success(word);
    }

    @GetMapping ("/addColletion")
    public Result addColletion(Integer id, String user){
        Integer wordId = id;
        Claims claims = JwtUtils.parseJWT(user);
        Integer userId = claims.get("id", Integer.class);
        log.info("用户的id为"+ userId);
        userService.collectWord(userId, wordId);
        log.info("添加成功");
        return Result.success();
    }

    @GetMapping("/isCollected")
    public Result isCollected(Integer wordId, String user){
        Claims claims = JwtUtils.parseJWT(user);
        Integer userId = claims.get("id", Integer.class);
        Integer res = userService.isCollected(userId, wordId);
        log.info("检查到是否收藏" + res);
        return Result.success(res);
    }

    @GetMapping("/deleteColletion")
    public Result deleteColletion(Integer id, String user){
        Integer wordId = id;
        Claims claims = JwtUtils.parseJWT(user);
        Integer userId = claims.get("id", Integer.class);
        log.info("用户的id为"+ userId);
        userService.deleteColletion(userId, wordId);
        log.info("删除成功");
        return Result.success();
    }

    @GetMapping("/getCollection")
    public Result getCollection(String user){
        Claims claims = JwtUtils.parseJWT(user);
        Integer userId = claims.get("id", Integer.class);
        List<Word> words = userService.getCollection(userId);
        log.info("收藏夹的结果为：" + words);
        return Result.success(words);
    }

    @GetMapping("/searchCollection")
    public Result searchCollection(String user, String search){
        Claims claims = JwtUtils.parseJWT(user);
        Integer userId = claims.get("id", Integer.class);
        List<Word> words = userService.searchCollection(userId, search);
        log.info("根据 {} 查询到如下数据: {}", search, words);
        return Result.success(words);
    }
}
