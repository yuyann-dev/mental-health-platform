package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.common.zj.RateLimit;
import com.example.entity.Account;
import com.example.entity.Doctor;
import com.example.entity.UserVo;
import com.example.exception.CustomException;
import com.example.service.AdminService;
import com.example.service.DoctorService;
import com.example.service.UserService;
import com.example.utils.MD5Util;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
public class WebController {

    @Resource
    private AdminService adminService;
    @Resource
    private DoctorService doctorService;
    @Resource
    private UserService userService;
    @Resource
    MD5Util md5Util;
    /**
     * 默认请求接口
     */
    @GetMapping("/")
    public Result hello () {
        return Result.success();
    }

    /**
     * 登录
     */
    @RateLimit(value=3,duration = 10)
    @PostMapping("/login")
    public Result login(@RequestBody Account account) {
        Account loginAccount = null;
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            loginAccount = adminService.login(account);
        }
        if (RoleEnum.DOCTOR.name().equals(account.getRole())) {
            loginAccount = doctorService.login(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            loginAccount = userService.login(account);
        }
        return Result.success(loginAccount);
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public Result register(@RequestBody Account account) {
        // 通过前端传过来的数据里面的角色来判断往哪个数据库里新增一条数据
        if (RoleEnum.DOCTOR.name().equals(account.getRole())) {
            doctorService.register(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.register(account);
        }
        return Result.success();
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account) {
        if (RoleEnum.ADMIN.name().equals(account.getRole())) {
            adminService.updatePassword(account);
        }
        if (RoleEnum.DOCTOR.name().equals(account.getRole())) {
            doctorService.updatePassword(account);
        }
        if (RoleEnum.USER.name().equals(account.getRole())) {
            userService.updatePassword(account);
        }
        return Result.success();
    }
    /**
     * 找回密码
     */
    @PostMapping("/findPassword")
    public Result findPassword(@RequestBody UserVo account) {
        Doctor doctor = doctorService.selectByUserName(account.getUsername());
        if(ObjectUtil.isNull(doctor)){
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if(!account.getMbwt().equals(doctor.getMbwt())){
            throw new CustomException(ResultCodeEnum.MBWTNOTRIGHT);
        }
        doctor.setPassword(md5Util.encrypt(account.getPassword()));
        doctorService.updateById(doctor);
        return Result.success();
    }
}
