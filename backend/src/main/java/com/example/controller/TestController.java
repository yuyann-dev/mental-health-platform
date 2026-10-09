package com.example.controller;

import com.example.common.Result;
import com.example.entity.TestPaper;
import com.example.entity.TestRecord;
import com.example.entity.Topic;
import com.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    
    @Autowired
    private TestService testService;
    
    @GetMapping("/paper/list")
    public Result<List<TestPaper>> getTestPapers(@RequestParam Integer typeId) {
        return Result.success(testService.getTestPapersByType(typeId));
    }
    
    @GetMapping("/paper/detail")
    public Result<TestPaper> getTestPaperDetail(@RequestParam Integer id) {
        return Result.success(testService.getTestPaperDetail(id));
    }
    
    @GetMapping("/topic/detail")
    public Result<Topic> getTopicDetail(@RequestParam Integer id) {
        return Result.success(testService.getTopicDetail(id));
    }
    
    @PostMapping("/submit")
    public Result<Map<String, Object>> submitTest(@RequestBody Map<String, Object> request) {
        Integer paperId = (Integer) request.get("paperId");
        Integer userId = (Integer) request.get("userId");
        List<String> answers = (List<String>) request.get("answers");
        
        TestRecord record = testService.submitTest(paperId, userId, answers);
        return Result.success(testService.getTestResult(record.getId()));
    }
    
    @GetMapping("/record/list")
    public Result<List<TestRecord>> getUserTestRecords(@RequestParam Integer userId) {
        return Result.success(testService.getUserTestRecords(userId));
    }
    
    @GetMapping("/record/detail")
    public Result<Map<String, Object>> getTestResult(@RequestParam Integer recordId) {
        return Result.success(testService.getTestResult(recordId));
    }
} 