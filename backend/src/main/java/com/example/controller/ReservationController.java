package com.example.controller;

import com.example.common.Result;
import com.example.entity.Reservation;
import com.example.service.ReservationService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 预约信息前端请求接口
 */
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Resource
    private ReservationService reservationService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody Reservation reservation) {
        reservationService.add(reservation);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody Reservation reservation) {
        reservationService.updateById(reservation);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        reservationService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        reservationService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Reservation reservation = reservationService.selectById(id);
        return Result.success(reservation);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(Reservation reservation) {
        List<Reservation> list = reservationService.selectAll(reservation);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(Reservation reservation,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Reservation> pageInfo = reservationService.selectPage(reservation, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
