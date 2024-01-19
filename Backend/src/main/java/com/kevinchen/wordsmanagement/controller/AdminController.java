package com.kevinchen.wordsmanagement.controller;


import com.kevinchen.wordsmanagement.pojo.Result;
import com.kevinchen.wordsmanagement.pojo.Word;
import com.kevinchen.wordsmanagement.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/backpage")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/info")
    public Result wordsInfo() {
        List<Word> words = adminService.wordsInfo();
        log.info("拿到了数据： {}", words);
        return Result.success(words);
    }

    @PostMapping("/addword")
    public Result addWord(@RequestBody Word word){
        Boolean isWord = adminService.serachByWord(word.getWord());
        if(isWord) {
            log.info("单词已经存在");
            return Result.error("单词已经存在！");
        }
        else {
            adminService.addWord(word);
            log.info("向数据库中插入了数据为： {}", word);
            return Result.success();
        }
    }

    @GetMapping("/search")
    public Result searchWord(String search){
        List<Word> words = adminService.searchWord(search);
        log.info("根据 {} 查询到如下数据: {}", search, words);
        return Result.success(words);
    }

    @GetMapping("/selectById")
    public Result selectById(Integer id){
        Word word = adminService.selectById(id);
        log.info("根据 {} 查询到如下数据: {}", id, word);
        return Result.success(word);
    }

    @DeleteMapping("/deleteById")
    public Result deleteById(Integer id){
        adminService.deleteById(id);
        return Result.success();
    }

    @PostMapping("/update")
    public Result update(@RequestBody Word word){
        adminService.update(word);
        log.info("更新成功");
        return Result.success("更新成功");
    }
}
