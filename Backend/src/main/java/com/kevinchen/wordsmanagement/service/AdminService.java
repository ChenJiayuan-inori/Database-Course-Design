package com.kevinchen.wordsmanagement.service;

import com.kevinchen.wordsmanagement.pojo.Result;
import com.kevinchen.wordsmanagement.pojo.User;
import com.kevinchen.wordsmanagement.pojo.Word;

import java.util.List;

public interface AdminService {
    List<Word> wordsInfo();

    void addWord(Word word);

    List<Word> searchWord(String search);

    Boolean serachByWord(String word);

    User adminLogin(User user);

    Word selectById(Integer id);

    void deleteById(Integer id);

    void update(Word word);
}
