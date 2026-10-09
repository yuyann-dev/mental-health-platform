package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Constants;
import com.example.common.enums.ResultCodeEnum;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;

import com.example.entity.Doctor;
import com.example.entity.Reservation;

import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.DoctorMapper;
import com.example.mapper.ReservationMapper;
import com.example.utils.MD5Util;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 心理医生信息业务层方法
 */
@Service
public class DoctorService {
    @Resource
    MD5Util md5Util;
    @Resource
    private DoctorMapper doctorMapper;
    @Resource
    private ReservationMapper reservationMapper;

    public void add(Doctor doctor) {
        Doctor dbDoctor = doctorMapper.selectByUsername(doctor.getUsername());
        if (ObjectUtil.isNotNull(dbDoctor)) {
            throw new CustomException(ResultCodeEnum.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(doctor.getPassword())) {
            doctor.setPassword(Constants.USER_DEFAULT_PASSWORD);
        }
        if (ObjectUtil.isEmpty(doctor.getName())) {
            doctor.setName(doctor.getUsername());
        }
        if (ObjectUtil.isEmpty(doctor.getStatus())) {
            doctor.setStatus("待审批");
        }
        doctor.setPassword(md5Util.encrypt(doctor.getPassword()));
        doctor.setRole(RoleEnum.DOCTOR.name());
        doctorMapper.insert(doctor);
    }

    public void updateById(Doctor doctor) {
        doctorMapper.updateById(doctor);
    }

    public void deleteById(Integer id) {
        doctorMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            doctorMapper.deleteById(id);
        }
    }

    public Doctor selectById(Integer id) {
        return doctorMapper.selectById(id);
    }

    public List<Doctor> selectAll(Doctor doctor) {
        return doctorMapper.selectAll(doctor);
    }

    public PageInfo<Doctor> selectPage(Doctor doctor, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Doctor> list = doctorMapper.selectAll(doctor);
        return PageInfo.of(list);
    }

    /**
     * 登录
     */
    public Doctor login(Account account) {
        Doctor dbDoctor = doctorMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbDoctor)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!dbDoctor.getPassword().equals(md5Util.encrypt(account.getPassword()))) {
            throw new CustomException(ResultCodeEnum.USER_ACCOUNT_ERROR);
        }
        // 生成token
        String token = TokenUtils.createToken(dbDoctor.getId() + "-" + dbDoctor.getRole(), dbDoctor.getPassword());
        dbDoctor.setToken(token);
        return dbDoctor;
    }

    /**
     * 修改密码
     */
    public void updatePassword(Account account) {
        Doctor dbDoctor = doctorMapper.selectByUsername(account.getUsername());
        if (ObjectUtil.isNull(dbDoctor)) {
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST_ERROR);
        }
        if (!account.getPassword().equals(dbDoctor.getPassword())) {
            throw new CustomException(ResultCodeEnum.PARAM_PASSWORD_ERROR);
        }
        dbDoctor.setPassword(md5Util.encrypt(account.getNewPassword()));
        doctorMapper.updateById(dbDoctor);
    }

    public void register(Account account) {
        // 注册的本质就是新增
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(account, doctor);
        add(doctor);
    }

    public void submit(Doctor doctor) {
        doctor.setStatus("待审批");
        doctorMapper.updateById(doctor);
    }

    public List<Doctor> top4() {
        List<Doctor> doctors = doctorMapper.allPassDoctor();
        for (Doctor doctor : doctors) {
            List<Reservation> list = reservationMapper.selectByDoctorIdAndPass(doctor.getId());
            doctor.setNum(list.size());
        }
        return doctors.stream()
                .sorted(Comparator.comparingInt(Doctor::getNum).reversed())
                .collect(Collectors.toList());
    }

    /**
     * 获取指定医生的患者的列表
     */
    public List<User> getPatients(Integer doctorId) {
        return doctorMapper.getPatients(doctorId);
    }

    public Doctor selectByUserName(String username) {
        return doctorMapper.selectByUserName(username);
    }
}
