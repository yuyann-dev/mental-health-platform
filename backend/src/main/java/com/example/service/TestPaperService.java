package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.TestPaper;
import com.example.entity.Topic;
import com.example.exception.CustomException;
import com.example.mapper.TestPaperMapper;
import com.example.mapper.TopicMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 试卷信息业务层方法
 */
@Service
public class TestPaperService {

    @Resource
    private TestPaperMapper testPaperMapper;
    @Resource
    private TopicMapper topicMapper;

    public void add(TestPaper testPaper) {
        // 校验一下前端传过来的参数
        checkTestPaper(testPaper);

        Account currentUser = TokenUtils.getCurrentUser();
        testPaper.setDoctorId(currentUser.getId());

        if (1 == testPaper.getFlag()) { // 手动出题才需要处理这个idList
            // 处理下试卷中题目id的数据
            List<Integer> idList = testPaper.getIdList();
            testPaper.setIds(JSONUtil.toJsonStr(idList)); // "[1,2,3,4,5]"
        } else {
            // 自动出题前端不会传过来idList，我们需要根据num和typeId来随机组卷
            List<Topic> topics = topicMapper.selectAll(new Topic());
            List<Integer> list = topics.stream().filter(x -> testPaper.getTypeId().equals(x.getTypeId())).map(Topic::getId).collect(Collectors.toList());
            Collections.shuffle(list);
            if (list.size() < testPaper.getNum()) {
                throw new CustomException("500", "您选择的分类题库里题目数量不足，请减少试卷题目数量或者联系管理员增加题库中该分类下面的题目数量");
            }
            testPaper.setIds(JSONUtil.toJsonStr(list.subList(0, testPaper.getNum())));
        }

        // 处理一下三个分数区间的数据
        testPaper.setaRange(testPaper.getaLeft() + "~" + testPaper.getaRight());
        testPaper.setbRange(testPaper.getbLeft() + "~" + testPaper.getbRight());
        testPaper.setcRange(testPaper.getcLeft() + "~" + testPaper.getcRight());

        testPaper.setTime(DateUtil.now());
        testPaper.setStatus("待审核");
        testPaperMapper.insert(testPaper);
    }

    private void checkTestPaper(TestPaper testPaper) {
        if (ObjectUtil.isEmpty(testPaper.getTitle())
                || ObjectUtil.isEmpty(testPaper.getImg())
                || ObjectUtil.isEmpty(testPaper.getContent())
                || ObjectUtil.isEmpty(testPaper.getTypeId())
                || ObjectUtil.isEmpty(testPaper.getNum())
                || ObjectUtil.isEmpty(testPaper.getScore())
                || ObjectUtil.isEmpty(testPaper.getaLeft())
                || ObjectUtil.isEmpty(testPaper.getbLeft())
                || ObjectUtil.isEmpty(testPaper.getcLeft())
                || ObjectUtil.isEmpty(testPaper.getaRight())
                || ObjectUtil.isEmpty(testPaper.getbRight())
                || ObjectUtil.isEmpty(testPaper.getcRight())
                || ObjectUtil.isEmpty(testPaper.getaAnswer())
                || ObjectUtil.isEmpty(testPaper.getbAnswer())
                || ObjectUtil.isEmpty(testPaper.getcAnswer())
        ) {
            throw new CustomException("500", "数据不完整，请填写所有需要的数据");
        }
        if (1 == testPaper.getFlag()) {
            if (ObjectUtil.isEmpty(testPaper.getIdList())) {
                throw new CustomException("500", "数据不完整，请选择具体您要出的题目");
            }
        }
    }

    public void updateById(TestPaper testPaper) {
        testPaperMapper.update(testPaper);
    }

    public void deleteById(Integer id) {
        testPaperMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            testPaperMapper.deleteById(id);
        }
    }

    public TestPaper selectById(Integer id) {
        return testPaperMapper.findById(id);
    }

    public List<TestPaper> selectAll(TestPaper testPaper) {
        return testPaperMapper.selectAll(testPaper);
    }

    public PageInfo<TestPaper> selectPage(TestPaper testPaper, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.DOCTOR.name().equals(currentUser.getRole())) {
            testPaper.setDoctorId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<TestPaper> list = testPaperMapper.selectAll(testPaper);
        return PageInfo.of(list);
    }

    public List<Topic> getTopics(TestPaper testPaper) {
        List<Integer> idList = Arrays.stream(testPaper.getIds().split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        List<Topic> topics = new ArrayList<>();
        for (Integer id : idList) {
            Topic topic = topicMapper.findById(id);
            topics.add(topic);
        }
        return topics;
    }

    public List<TestPaper> selectAllDesc() {
        return testPaperMapper.selectAllDesc();
    }
}
