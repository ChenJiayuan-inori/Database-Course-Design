package com.kevinchen.wordsmanagement.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Word {
    private Integer id;
    private String word;
    private Integer classes;
    private String meaning;
    private String description;
    private String example;
    private Integer type;
    private Integer queriesTimes;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
