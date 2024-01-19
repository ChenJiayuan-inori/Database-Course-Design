package com.kevinchen.wordsmanagement.service.impl;

import com.kevinchen.wordsmanagement.mapper.AdminMapper;
import com.kevinchen.wordsmanagement.pojo.Result;
import com.kevinchen.wordsmanagement.pojo.User;
import com.kevinchen.wordsmanagement.pojo.Word;
import com.kevinchen.wordsmanagement.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public List<Word> wordsInfo() {
        return adminMapper.wordsInfo();
    }

    @Override
    public void addWord(Word word) {

        word.setCreateTime(LocalDateTime.now());
        word.setUpdateTime(LocalDateTime.now());
        adminMapper.addWord(word);
    }

    @Override
    public List<Word> searchWord(String search) {
        List<Word> words = adminMapper.searchWord(search);
        return words;
    }

    @Override
    public Boolean serachByWord(String word) {
        if(adminMapper.searchByWord(word) != null) return true;
        else return false;
    }

    @Override
    public User adminLogin(User user) {
        return adminMapper.adminLogin(user);
    }

    @Override
    public Word selectById(Integer id) {
        return adminMapper.selectById(id);
    }

    @Override
    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    @Override
    public void update(Word word) {
        Word newWord = selectById(word.getId());
        if(word.getWord() == null){
            word.setWord(newWord.getWord());
        }
        if(word.getClasses() == null){
            word.setClasses(newWord.getClasses());
        }
        if(word.getMeaning() == null){
            word.setMeaning(newWord.getMeaning());
        }
        if(word.getDescription() == null){
            word.setDescription(newWord.getDescription());
        }
        if(word.getExample() == null){
            word.setExample(newWord.getExample());
        }
        if(word.getType() == null){
            word.setType(newWord.getType());
        }
        word.setUpdateTime(LocalDateTime.now());
        adminMapper.update(word);
    }


}
