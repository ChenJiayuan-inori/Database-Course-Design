package com.kevinchen.wordsmanagement.service;


import com.kevinchen.wordsmanagement.pojo.User;
import com.kevinchen.wordsmanagement.pojo.Word;

import java.util.List;

public interface UserService {
    User userLogin(User user);

    void insertUser(User user);

    User selectByUsername(User user);

    List<Word> wordsInfo();

    List<Word> searchWord(String search);

    Word searchById(Integer id);

    void updateById(Integer id);

    void collectWord(Integer userId, Integer wordId);

    Integer isCollected(Integer userId, Integer wordId);

    void deleteColletion(Integer userId, Integer wordId);

    List<Word> getCollection(Integer userId);

    List<Word> searchCollection(Integer userId, String search);
}
