package com.example.controller;

import com.example.common.Result;
import com.example.entity.TestPaper;
import com.example.entity.TestRecord;
import com.example.service.AnswerRecordService;
import com.example.service.TestPaperService;
import com.example.service.TestRecordService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 测试记录前端请求接口
 */
@RestController
@RequestMapping("/testRecord")
public class TestRecordController {

    @Resource
    private TestRecordService testRecordService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody TestPaper testPaper) {
        testRecordService.add(testPaper);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody TestRecord testRecord) {
        testRecordService.updateById(testRecord);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        testRecordService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        testRecordService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        TestRecord testRecord = testRecordService.selectById(id);
        return Result.success(testRecord);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(TestRecord testRecord) {
        List<TestRecord> list = testRecordService.selectAll(testRecord);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(TestRecord testRecord,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<TestRecord> pageInfo = testRecordService.selectPage(testRecord, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
