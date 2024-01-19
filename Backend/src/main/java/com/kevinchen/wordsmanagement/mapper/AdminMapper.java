package com.kevinchen.wordsmanagement.mapper;

import com.kevinchen.wordsmanagement.pojo.Result;
import com.kevinchen.wordsmanagement.pojo.User;
import com.kevinchen.wordsmanagement.pojo.Word;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {

    @Select("select * from words")
    List<Word> wordsInfo();

    @Insert("insert into words (word, classes, meaning, description, example, type, create_time, update_time) " +
            "values " +
            "(#{word}, #{classes}, #{meaning}, #{description}, #{example}, #{type}, #{createTime}, #{updateTime})")
    void addWord(Word word);

    @Select("select * from words where word like concat('%', #{search}, '%') or meaning like concat('%', #{search}, '%')")
    List<Word> searchWord(String search);

    @Select("select * from words where word = #{word}")
    Word searchByWord(String word);

    @Select("select * from admin where username = #{username} and password = #{password}")
    User adminLogin(User user);

    @Select("select * from words where id = #{id}")
    Word selectById(Integer id);

    @Delete("delete from words where id = #{id}")
    void deleteById(Integer id);

    @Update("update words set word = #{word}, classes = #{classes}, meaning = #{meaning}, description = #{description}, example = #{example}, type = #{type}, update_time = #{updateTime} where id = #{id}")
    void update(Word word);
}
