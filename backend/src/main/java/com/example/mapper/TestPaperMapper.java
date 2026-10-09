package com.example.mapper;

import com.example.entity.TestPaper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TestPaperMapper {
    
    @Select("SELECT * FROM test_paper WHERE type_id = #{typeId} AND status = '审核通过'")
    List<TestPaper> findByTypeId(Integer typeId);
    
    @Select("SELECT * FROM test_paper WHERE id = #{id}")
    TestPaper findById(Integer id);
    
    @Update("UPDATE test_paper SET test_num = test_num + 1 WHERE id = #{id}")
    void incrementTestNum(Integer id);
    
    // XML mapped methods - no annotations needed
    void insert(TestPaper testPaper);
    void update(TestPaper testPaper);
    void deleteById(Integer id);
    List<TestPaper> selectAll(TestPaper testPaper);

    @Select("SELECT * FROM test_paper ORDER BY test_num DESC")
    List<TestPaper> selectAllDesc();
}
