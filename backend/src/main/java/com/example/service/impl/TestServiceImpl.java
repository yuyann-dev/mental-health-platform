package com.example.service.impl;

import com.example.entity.TestPaper;
import com.example.entity.TestRecord;
import com.example.entity.Topic;
import com.example.mapper.TestPaperMapper;
import com.example.mapper.TestRecordMapper;
import com.example.mapper.TopicMapper;
import com.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class TestServiceImpl implements TestService {
    
    @Autowired
    private TestPaperMapper testPaperMapper;
    
    @Autowired
    private TopicMapper topicMapper;
    
    @Autowired
    private TestRecordMapper testRecordMapper;
    
    @Override
    public List<TestPaper> getTestPapersByType(Integer typeId) {
        return testPaperMapper.findByTypeId(typeId);
    }
    
    @Override
    public TestPaper getTestPaperDetail(Integer id) {
        return testPaperMapper.findById(id);
    }
    
    @Override
    public Topic getTopicDetail(Integer id) {
        return topicMapper.findById(id);
    }
    
    @Override
    @Transactional
    public TestRecord submitTest(Integer paperId, Integer userId, List<String> answers) {
        TestPaper paper = testPaperMapper.findById(paperId);
        if (paper == null) {
            throw new RuntimeException("量表不存在");
        }
        
        // 计算得分
        int totalScore = 0;
        // 处理JSON数组格式的ids
        String ids = paper.getIds().trim();
        ids = ids.substring(1, ids.length() - 1); // 移除方括号
        String[] topicIds = ids.split(",");
        
        for (int i = 0; i < answers.size(); i++) {
            Topic topic = topicMapper.findById(Integer.parseInt(topicIds[i].trim()));
            String answer = answers.get(i);
            switch (answer.toLowerCase()) {
                case "a":
                    totalScore += topic.getaScore();
                    break;
                case "b":
                    totalScore += topic.getbScore();
                    break;
                case "c":
                    totalScore += topic.getcScore();
                    break;
                case "d":
                    totalScore += topic.getdScore();
                    break;
            }
        }
        
        // 判断等级
        String result;
        String analysis;
        if (totalScore <= Integer.parseInt(paper.getaRange().split("~")[1])) {
            result = paper.getaAnswer();
            analysis = generateAnalysis(paper, answers, "轻度");
        } else if (totalScore <= Integer.parseInt(paper.getbRange().split("~")[1])) {
            result = paper.getbAnswer();
            analysis = generateAnalysis(paper, answers, "中度");
        } else {
            result = paper.getcAnswer();
            analysis = generateAnalysis(paper, answers, "重度");
        }
        
        // 保存测试记录
        TestRecord record = new TestRecord();
        record.setTestPaperId(paperId);
        record.setUserId(userId);
        record.setScore(totalScore);
        record.setResult(result);
        record.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        record.setAiResult(analysis);
        
        testRecordMapper.insert(record);
        testPaperMapper.incrementTestNum(paperId);
        
        return record;
    }
    
    @Override
    public List<TestRecord> getUserTestRecords(Integer userId) {
        return testRecordMapper.findByUserId(userId);
    }
    
    @Override
    public Map<String, Object> getTestResult(Integer recordId) {
        TestRecord record = testRecordMapper.findById(recordId);
        if (record == null) {
            throw new RuntimeException("测试记录不存在");
        }
        
        TestPaper paper = testPaperMapper.findById(record.getTestPaperId());
        
        Map<String, Object> result = new HashMap<>();
        result.put("score", record.getScore());
        result.put("level", record.getResult());
        result.put("analysis", record.getAiResult());
        result.put("paperTitle", paper.getTitle());
        result.put("paperContent", paper.getContent());
        
        return result;
    }
    
    private String generateAnalysis(TestPaper paper, List<String> answers, String level) {
        StringBuilder analysis = new StringBuilder();
        analysis.append("<p>根据本次测评结果分析，您的心理状态处于").append(level).append("水平：<br><br>");
        
        // 根据不同量表类型生成不同的分析内容
        switch (paper.getTypeId()) {
            case 1: // 抑郁自评量表SDS
                generateSdsAnalysis(analysis, answers);
                break;
            case 2: // CES-D抑郁自评量表
                generateCesdAnalysis(analysis, answers);
                break;
            case 3: // PHQ-9抑郁症筛查量表
                generatePhq9Analysis(analysis, answers);
                break;
            case 4: // 焦虑自评量表(SAS)
                generateSasAnalysis(analysis, answers);
                break;
            case 5: // 社交焦虑量表(LSAS)
                generateLsasAnalysis(analysis, answers);
                break;
            case 6: // 强迫症状自评量表(MOCI)
                generateMociAnalysis(analysis, answers);
                break;
            case 7: // 创伤后应激障碍量表(PCL-5)
                generatePclAnalysis(analysis, answers);
                break;
        }
        
        analysis.append("</p>");
        return analysis.toString();
    }
    
    private void generateSdsAnalysis(StringBuilder analysis, List<String> answers) {
        // 分析情绪症状
        analysis.append("1.<strong>情绪症状</strong>：");
        if (answers.get(0).equalsIgnoreCase("c") || answers.get(0).equalsIgnoreCase("d")) {
            analysis.append("您的情绪状态相对稳定，较少出现情绪低落的情况。");
        } else {
            analysis.append("您可能经常感到情绪低落，建议寻求专业帮助。");
        }
        analysis.append("<br><br>");
        
        // 分析躯体症状
        analysis.append("2.<strong>躯体症状</strong>：");
        if (answers.get(3).equalsIgnoreCase("c") || answers.get(3).equalsIgnoreCase("d")) {
            analysis.append("您的睡眠质量较好，身体状况良好。");
        } else {
            analysis.append("您可能存在睡眠问题和其他身体不适，建议进行身体检查。");
        }
        analysis.append("<br><br>");
        
        // 建议
        analysis.append("建议：<br>");
        analysis.append("1. 保持规律的作息时间<br>");
        analysis.append("2. 适当进行运动<br>");
        analysis.append("3. 与家人朋友多交流<br>");
        analysis.append("4. 必要时寻求专业心理咨询");
    }
    
    private void generateCesdAnalysis(StringBuilder analysis, List<String> answers) {
        // TODO: 实现CES-D量表的分析逻辑
    }
    
    private void generatePhq9Analysis(StringBuilder analysis, List<String> answers) {
        // TODO: 实现PHQ-9量表的分析逻辑
    }
    
    private void generateSasAnalysis(StringBuilder analysis, List<String> answers) {
        // TODO: 实现SAS量表的分析逻辑
    }
    
    private void generateLsasAnalysis(StringBuilder analysis, List<String> answers) {
        // TODO: 实现LSAS量表的分析逻辑
    }
    
    private void generateMociAnalysis(StringBuilder analysis, List<String> answers) {
        // TODO: 实现MOCI量表的分析逻辑
    }
    
    private void generatePclAnalysis(StringBuilder analysis, List<String> answers) {
        // TODO: 实现PCL-5量表的分析逻辑
    }
} 