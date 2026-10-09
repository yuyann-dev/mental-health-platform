package com.example.mapper;


import com.example.entity.Doctor;
import com.example.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DoctorMapper {

    int insert(Doctor doctor);

    void updateById(Doctor doctor);

    void deleteById(Integer id);

    @Select("select * from `doctor` where id = #{id}")
    Doctor selectById(Integer id);

    @Select("select * from `doctor` where username = #{username}")
    Doctor selectByUsername(String username);

    List<Doctor> selectAll(Doctor doctor);

    @Select("select * from doctor where status = '审批通过'")
    List<Doctor> allPassDoctor();

    //根据医生id查询患者
    @Select("SELECT * FROM user WHERE doctor_id = #{doctorId}")
    List<User> getPatients(@Param("doctorId") Integer doctorId);

    Doctor selectByUserName(String username);
}
