package com.example.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.*;
import com.example.service.*;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private TopicService topicService;
    @Resource
    private TestPaperService testPaperService;
    @Resource
    private DoctorService doctorService;
    @Resource
    private UserService userService;
    @Resource
    private TestRecordService testRecordService;
    @Resource
    private TypeService typeService;

    @GetMapping("/base")
    public Result base() {
        Map<String, Integer> resultMap = new HashMap<>();

        // 获取题库中所有题目的数量
        resultMap.put("topicNum", topicService.selectAll(new Topic()).size());
        // 获取所有的心理测试卷的数量
        resultMap.put("testPaperNum", testPaperService.selectAll(new TestPaper()).size());
        // 获取所有入驻心理医生的数量
        resultMap.put("doctorNum", doctorService.selectAll(new Doctor()).size());
        // 获取所有用户的数量
        resultMap.put("userNum", userService.selectAll(new User()).size());

        return Result.success(resultMap);
    }

    @GetMapping("/line")
    public Result line() {
        Map<String, Object> resultMap = new HashMap<>();
        List<Long> yList = new ArrayList<>();

        // 初始化xList和yList
        Date today = new Date();
        DateTime start = DateUtil.offsetDay(today, -7);
        List<String> xList = DateUtil.rangeToList(start, today, DateField.DAY_OF_YEAR).stream().map(DateUtil::formatDate).toList();

        List<TestRecord> testRecords = testRecordService.selectAll(new TestRecord());
        for (String day : xList) {
            long count = testRecords.stream().filter(x -> x.getTime().contains(day)).count();
            yList.add(count);
        }

        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);
        return Result.success(resultMap);
    }

    @GetMapping("/bar")
    public Result bar() {
        Map<String, Object> resultMap = new HashMap<>();
        List<String> xList = new ArrayList<>();
        List<Long> yList = new ArrayList<>();

        // 初始化xList和yList
        List<Type> types = typeService.selectAll(new Type());
        List<TestRecord> testRecords = testRecordService.selectAll(new TestRecord());

        for (Type type : types) {
            xList.add(type.getTitle());
            yList.add(testRecords.stream().filter(x -> x.getTypeName().equals(type.getTitle())).count());
        }

        resultMap.put("xAxis", xList);
        resultMap.put("yAxis", yList);
        return Result.success(resultMap);
    }

    @GetMapping("/pie")
    public Result pie() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Type> types = typeService.selectAll(new Type());
        List<Topic> topics = topicService.selectAll(new Topic());
        for (Type type : types) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", type.getTitle());
            map.put("value", topics.stream().filter(x -> x.getTypeId().equals(type.getId())).count());
            list.add(map);
        }
        return Result.success(list);
    }
}
