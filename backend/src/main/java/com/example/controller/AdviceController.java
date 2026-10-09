package com.example.controller;

import com.example.common.Result;
import com.example.entity.*;
import com.example.mapper.DoctorMapper;
import com.example.mapper.UserMapper;
import com.example.service.AdviceService;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 诊疗建议控制器
 */
@RestController
@RequestMapping("/advice")
public class AdviceController {

    @Autowired
    private AdviceService adviceService;
    @Autowired
    private UserMapper userMapper;
    /**
     * 新增诊疗建议
     */
    @PostMapping("/add")
    public Result add(@RequestBody Advice advice) {
        advice.setAdviceTime(new Date());
        adviceService.add(advice);
        return Result.success();
    }

    /**
     * 修改诊疗建议
     */
    @PutMapping("/update")
    public Result update(@RequestBody Advice advice) {
        adviceService.updateById(advice);
        return Result.success();
    }

    /**
     * 删除诊疗建议
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        adviceService.deleteById(id);
        return Result.success();
    }

    /**
     * 根据ID查询诊疗建议
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Advice advice = adviceService.selectById(id);
        return Result.success(advice);
    }

    /**
     * 根据用户ID查询所有诊疗建议
     */
    @GetMapping("/selectAllByUserId/{userId}")
    public Result selectAllByUserId(@PathVariable Integer userId) {
        List<Advice> adviceList = adviceService.selectAllByUserId(userId);
        return Result.success(adviceList);
    }

    /**
     * 我的所有诊疗建议
     */
    @GetMapping("/selectAllByMy")
    public Result selectAllMy() {
        Account currentUser = TokenUtils.getCurrentUser();
        List<Advice> adviceList = adviceService.selectAllByUserId(currentUser.getId());
        return Result.success(adviceList);
    }
    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(TestRecord testRecord,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Account currentUser = TokenUtils.getCurrentUser();
       List<AdviceDto>  adviceDtoList=adviceService.selectAllAdviceByDoctor(currentUser.getId());

        for (AdviceDto adviceDto : adviceDtoList) {
            User user = userMapper.selectById(adviceDto.getUserId());
            adviceDto.setUserName(user.getUsername());
        }
        PageInfo<AdviceDto>pageInfo=new PageInfo<>();
        pageInfo.setList(adviceDtoList);
        return Result.success(pageInfo);
    }
}
