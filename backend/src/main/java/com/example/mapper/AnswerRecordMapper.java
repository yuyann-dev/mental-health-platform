package com.example.mapper;

import com.example.entity.AnswerRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AnswerRecordMapper {

    @Insert("INSERT INTO `mental_health`.`answer_reocrd` (`test_record_id`, `topic_id`, `answer`) " +
            "VALUES (#{testRecordId}, #{topicId}, #{answer}); ")
    int insert(AnswerRecord answerRecord);

    @Select("select * from `answer_record` where test_record_id = #{testRecordId} and topic_id = #{topicId}")
    AnswerRecord selectById(@Param("testRecordId") Integer testRecordId, @Param("topicId") Integer topicId);

}
