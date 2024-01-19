package com.kevinchen.wordsmanagement.mapper;

import com.kevinchen.wordsmanagement.pojo.User;
import com.kevinchen.wordsmanagement.pojo.Word;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users where username = #{username} and password = #{password}")
    User userLogin(User user);

    @Insert("insert into users (username, password, create_time, update_time) values (#{username}, #{password}, #{createTime}, #{updateTime})")
    void insertUser(User user);

    @Select("select * from users where username = #{username}")
    User selectByUsername(User user);

    @Select("select * from words")
    List<Word> wordsInfo();

    @Select("select * from words where word like concat('%', #{search}, '%') or meaning like concat('%', #{search}, '%')")
    List<Word> searchWord(String search);

    @Select("select * from words where id = #{id}")
    Word searchById(Integer id);

    @Update("update words set queries_times = queries_times + 1 where id = #{id}")
    void updateById(Integer id);

    @Insert("insert into collection (user_id, word_id) VALUES (#{userId}, #{wordId})")
    void collectWord(@Param("userId") Integer userId,@Param("wordId")  Integer wordId);

    @Select("select * from collection where word_id = #{wordId} and user_id = #{userId}")
    Integer isCollected(@Param("userId") Integer userId,@Param("wordId")  Integer wordId);

    @Delete("delete from collection where user_id = #{userId} and word_id = #{wordId}")
    void deleteColletion(@Param("userId") Integer userId,@Param("wordId")  Integer wordId);


    @Select("select * from words w, (select word_id from collection where user_id = #{userId}) uw where uw.word_id = w.id")
    List<Word> CollectedWords(@Param("userId") Integer userId);

//    @Select("select w.* from words w, (select word_id from collection where user_id = #{userId}) uw where (uw.word_id = w.id) and " +
//            "(w.word like concat('%', #{search}, '%') or w.meaning like concat('%', #{search}, '%'))")
//    @Select("select res.* from (select * from words w, (select word_id from collection where user_id = #{userId}) uw where uw.word_id = w.id) as res where " +
//            "res.word like concat('%', #{search}, '%') or res.meaning like concat('%', #{search}, '%')")
    @Select("with tab as " +
            "(select * from words w, (select word_id from collection where user_id = #{userId}) uw where uw.word_id = w.id)" +
            "select * from tab where tab.word like concat('%', #{search}, '%') or tab.meaning like concat('%', #{search}, '%')")
    List<Word> searchCollection(@Param("userId") Integer userId,@Param("search") String search);
}
