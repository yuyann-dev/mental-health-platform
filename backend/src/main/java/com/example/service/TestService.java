package com.example.service;

import com.example.entity.TestPaper;
import com.example.entity.TestRecord;
import com.example.entity.Topic;

import java.util.List;
import java.util.Map;

public interface TestService {
    
    List<TestPaper> getTestPapersByType(Integer typeId);
    
    TestPaper getTestPaperDetail(Integer id);
    
    Topic getTopicDetail(Integer id);
    
    TestRecord submitTest(Integer paperId, Integer userId, List<String> answers);
    
    List<TestRecord> getUserTestRecords(Integer userId);
    
    Map<String, Object> getTestResult(Integer recordId);
} 