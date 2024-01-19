package com.kevinchen.wordsmanagement.service.impl;


import com.kevinchen.wordsmanagement.mapper.UserMapper;
import com.kevinchen.wordsmanagement.pojo.User;
import com.kevinchen.wordsmanagement.pojo.Word;
import com.kevinchen.wordsmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User userLogin(User user) {
        User newUser = this.selectByUsername(user);
        if(newUser == null){
            user.setCreateTime(LocalDateTime.now());
            user.setUpdateTime(LocalDateTime.now());
            this.insertUser(user);
        }
        return userMapper.userLogin(user);
    }

    public User selectByUsername(User user){
        return userMapper.selectByUsername(user);
    }

    @Override
    public List<Word> wordsInfo() {
        return userMapper.wordsInfo();
    }

    @Override
    public List<Word> searchWord(String search) {
        List<Word> words = userMapper.searchWord(search);
        return words;
    }

    @Override
    public Word searchById(Integer id) {
        userMapper.updateById(id);
        return userMapper.searchById(id);
    }

    @Override
    public void updateById(Integer id) {
        userMapper.updateById(id);
    }

    @Override
    public void collectWord(Integer userId, Integer wordId) {
        userMapper.collectWord(userId, wordId);
    }

    @Override
    public Integer isCollected(Integer userId, Integer wordId) {
        return userMapper.isCollected(userId, wordId);
    }

    @Override
    public void deleteColletion(Integer userId, Integer wordId) {
        userMapper.deleteColletion(userId, wordId);
    }


    @Override
    public List<Word> getCollection(Integer userId) {
        return userMapper.CollectedWords(userId);
    }

    @Override
    public List<Word> searchCollection(Integer userId, String search) {
        List<Word> collection = userMapper.searchCollection(userId, search);
        return collection;
    }

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }
}
