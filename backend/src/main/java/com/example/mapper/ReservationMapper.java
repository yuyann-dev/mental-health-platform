package com.example.mapper;

import com.example.entity.Reservation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ReservationMapper {

    int insert(Reservation reservation);

    void updateById(Reservation reservation);

    void deleteById(Integer id);

    @Select("select * from `reservation` where id = #{id}")
    Reservation selectById(Integer id);

    List<Reservation> selectAll(Reservation reservation);

    @Select("select * from reservation where user_id = #{userId} and doctor_id = #{doctorId}")
    List<Reservation> selectByUserIdAndDoctorId(@Param("userId") Integer userId, @Param("doctorId") Integer doctorId);

    @Select("select * from reservation where doctor_id = #{doctorId} and (status = '审核通过' or status = '已结束')")
    List<Reservation> selectByDoctorIdAndPass(Integer doctorId);
}
