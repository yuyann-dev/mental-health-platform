package com.example.entity;

import lombok.Data;

@Data
public class AnswerRecord {

    private Integer id;

    private Integer testRecordId;

    private Integer topicId;

    private String answer;

}
