package com.example.mapper;

import com.example.entity.Advice;
import com.example.entity.AdviceDto;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
@Mapper
public interface AdviceMapper {

//    @Insert("insert into `advice` (doctor_id, user_id, advice_content, advice_time, status) values (#{doctorId}, #{userId}, #{adviceContent}, #{adviceTime}, #{status})")
//    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertAdvice(Advice advice);

//    @Update("update `advice` set advice_content = #{adviceContent}, advice_time = #{adviceTime}, status = #{status} where id = #{id}")
    void updateAdvice(Advice advice);

//    @Delete("delete from `advice` where id = #{id}")
    void deleteAdviceById(Integer id);

//    @Select("select * from `advice` where id = #{id}")
    Advice selectAdviceById(Integer id);

//    @Select("select * from `advice` where user_id = #{userId}")
    List<Advice> selectAllAdviceByUser(Integer userId);


    List<AdviceDto> selectAllAdviceByDoctor(Integer id);
}
