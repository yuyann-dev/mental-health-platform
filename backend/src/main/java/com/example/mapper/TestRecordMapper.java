package com.example.mapper;

import com.example.entity.TestRecord;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TestRecordMapper {
    
    @Select("SELECT * FROM test_record WHERE user_id = #{userId} ORDER BY time DESC")
    List<TestRecord> findByUserId(Integer userId);
    
    @Select("SELECT * FROM test_record WHERE id = #{id}")
    TestRecord findById(Integer id);
    
    // XML mapped methods - no annotations needed
    void insert(TestRecord testRecord);
    void update(TestRecord testRecord);
    void deleteById(Integer id);
    List<TestRecord> selectAll(TestRecord testRecord);
}
