package com.example.service;

import com.example.entity.AnswerRecord;
import com.example.mapper.AnswerRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerRecordService {

    @Autowired
    private AnswerRecordMapper answerRecordMapper;

    public void add(AnswerRecord answerRecord) {
        answerRecordMapper.insert(answerRecord);
    }

    public AnswerRecord selectById(Integer testRecordId, Integer topicId) {
        return answerRecordMapper.selectById(testRecordId, topicId);
    }

}
