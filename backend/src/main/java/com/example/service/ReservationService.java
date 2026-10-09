package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.common.enums.RoleEnum;
import com.example.entity.Account;
import com.example.entity.Reservation;
import com.example.exception.CustomException;
import com.example.mapper.ReservationMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 预约信息业务层方法
 */
@Service
public class ReservationService {

    @Resource
    private ReservationMapper reservationMapper;

    public void add(Reservation reservation) {
        Account currentUser = TokenUtils.getCurrentUser();
        reservation.setUserId(currentUser.getId());
        reservation.setTime(DateUtil.now());
        reservation.setStatus("待审核");
        // 处理一下时间段的数据
        List<String> timeRange = reservation.getTimeRange();
        reservation.setStart(timeRange.get(0));
        reservation.setEnd(timeRange.get(1));
        // 做一下数据的校验：同一个用户预约同一个医生这种场景，在以下情况下不允许重复预约。
        // 该用户预约过该医生，但是预约记录里面的状态是“待审核”或者“审核通过”（反之：“已结束”或者“审核拒绝”可预约）
        List<Reservation> list = reservationMapper.selectByUserIdAndDoctorId(currentUser.getId(), reservation.getDoctorId());
        long count = list.stream().filter(v -> "待审核".equals(v.getStatus()) || "审核通过".equals(v.getStatus())).count();
        if (count > 0) {
            throw new CustomException("500", "您已经预约过该医生，请耐心等待该医生审核");
        }

        reservationMapper.insert(reservation);
    }

    public void updateById(Reservation reservation) {
        reservationMapper.updateById(reservation);
    }

    public void deleteById(Integer id) {
        reservationMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            reservationMapper.deleteById(id);
        }
    }

    public Reservation selectById(Integer id) {
        return reservationMapper.selectById(id);
    }

    public List<Reservation> selectAll(Reservation reservation) {
        return reservationMapper.selectAll(reservation);
    }

    public PageInfo<Reservation> selectPage(Reservation reservation, Integer pageNum, Integer pageSize) {
        Account currentUser = TokenUtils.getCurrentUser();
        if (RoleEnum.DOCTOR.name().equals(currentUser.getRole())) {
            reservation.setDoctorId(currentUser.getId());
        }
        if (RoleEnum.USER.name().equals(currentUser.getRole())) {
            reservation.setUserId(currentUser.getId());
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Reservation> list = reservationMapper.selectAll(reservation);
        return PageInfo.of(list);
    }

}
