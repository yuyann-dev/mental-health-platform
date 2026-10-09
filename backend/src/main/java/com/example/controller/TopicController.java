package com.example.controller;

import com.example.common.Result;
import com.example.entity.Topic;
import com.example.service.TopicService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 题库信息前端请求接口
 */
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Resource
    private TopicService topicService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Topic topic) {
        topicService.add(topic);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Topic topic) {
        topicService.updateById(topic);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        topicService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        topicService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Topic topic = topicService.selectById(id);
        return Result.success(topic);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Topic topic) {
        List<Topic> list = topicService.selectAll(topic);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Topic topic,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Topic> pageInfo = topicService.selectPage(topic, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
