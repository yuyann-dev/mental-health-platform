package com.example.mapper;

import com.example.entity.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TopicMapper {
    
    @Select("SELECT * FROM topic WHERE id = #{id}")
    Topic findById(Integer id);
    
    @Select("SELECT * FROM topic WHERE type_id = #{typeId}")
    List<Topic> findByTypeId(Integer typeId);
    
    // XML mapped methods - no annotations needed
    void insert(Topic topic);
    void update(Topic topic);
    void deleteById(Integer id);
    List<Topic> selectAll(Topic topic);
}
