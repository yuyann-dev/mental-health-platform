package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.example.mapper.TestPaperMapper;
import com.example.mapper.TestRecordMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 测试记录业务层方法
 */
@Service
public class TestRecordService {

    @Resource
    private TestRecordMapper testRecordMapper;
    @Resource
    private TestPaperMapper testPaperMapper;
    @Resource
    private TestPaperService paperService;
    @Resource
    private AnswerRecordService answerRecordService;

    public void add(TestPaper testPaper) {
        // 校验用户是否将所有题目都答完了
        List<Topic> topicList = testPaper.getTopicList();
        List<Topic> collect = topicList.stream().filter(x -> ObjectUtil.isNotEmpty(x.getUserAnswer())).toList();
        if (topicList.size() != collect.size()) {
            throw new CustomException("500", "请您务必答完所有题目才能提交");
        }

        Account currentUser = TokenUtils.getCurrentUser();
        TestRecord testRecord = new TestRecord();
        testRecord.setTestPaperId(testPaper.getId());
        testRecord.setDoctorId(testPaper.getDoctorId());
        testRecord.setUserId(currentUser.getId());
        testRecord.setTime(DateUtil.now());

        // 计算用户获得的总分数：遍历topicList里面的每一个题目，把用户的答案和ABCD四个答案做比较，选中的是哪个就算哪个选项的分数
        Integer score = 0;
        for (Topic topic : topicList) {
            String userAnswer = topic.getUserAnswer();
            if (userAnswer.equals(topic.getaName())) {
                score += topic.getaScore();
            }
            if (userAnswer.equals(topic.getbName())) {
                score += topic.getbScore();
            }
            if (userAnswer.equals(topic.getcName())) {
                score += topic.getcScore();
            }
            if (userAnswer.equals(topic.getdName())) {
                score += topic.getdScore();
            }
        }
        testRecord.setScore(score);

        // 根据试卷的分数区间，获取到对应分数的结果
        String aRange = testPaper.getaRange();
        String bRange = testPaper.getbRange();
        String cRange = testPaper.getcRange();
        if (score >= Integer.parseInt(aRange.split("~")[0]) && score <= Integer.parseInt(aRange.split("~")[1])) {
            testRecord.setResult(testPaper.getaAnswer());
        }
        if (score >= Integer.parseInt(bRange.split("~")[0]) && score <= Integer.parseInt(bRange.split("~")[1])) {
            testRecord.setResult(testPaper.getbAnswer());
        }
        if (score >= Integer.parseInt(cRange.split("~")[0]) && score <= Integer.parseInt(cRange.split("~")[1])) {
            testRecord.setResult(testPaper.getcAnswer());
        }

        testRecordMapper.insert(testRecord);

        // 测试题的测试人数+1
        testPaper.setTestNum(testPaper.getTestNum() + 1);
        testPaperMapper.update(testPaper);
    }

    public void updateById(TestRecord testRecord) {
        testRecordMapper.update(testRecord);
    }

    public void deleteById(Integer id) {
        testRecordMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            testRecordMapper.deleteById(id);
        }
    }

    public TestRecord selectById(Integer id) {
        return testRecordMapper.findById(id);
    }

    public List<TestRecord> selectAll(TestRecord testRecord) {
        List<TestRecord> testRecords = testRecordMapper.selectAll(testRecord);
        for (TestRecord record : testRecords) {
            TestPaper testPaper = testPaperMapper.findById(record.getTestPaperId());
            List<Topic> topics = paperService.getTopics(testPaper);

            topics.forEach(it -> {
                AnswerRecord answerRecord = answerRecordService.selectById(record.getId(), it.getId());
                if (answerRecord != null) {
                    it.setUserAnswer(answerRecord.getAnswer());
                }
            });

            record.setTopicList(topics);
        }
        return testRecords;
    }

    public PageInfo<TestRecord> selectPage(TestRecord testRecord, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.DOCTOR.name().equals(currentUser.getRole())) {
            testRecord.setDoctorId(currentUser.getId());
        }
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            testRecord.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TestRecord> list = testRecordMapper.selectAll(testRecord);
        for (TestRecord record : list) {
            TestPaper testPaper = testPaperMapper.findById(record.getTestPaperId());
            List<Topic> topics = paperService.getTopics(testPaper);
            topics.forEach(it -> {
                AnswerRecord answerRecord = answerRecordService.selectById(record.getId(), it.getId());
                if (answerRecord != null) {
                    it.setUserAnswer(answerRecord.getAnswer());
                }
            });
            record.setTopicList(topics);
        }
        return PageInfo.of(list);
    }

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

}


