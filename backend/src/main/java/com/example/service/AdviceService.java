package com.example.service;

import com.example.entity.Account;
import com.example.entity.Advice;
import com.example.entity.AdviceDto;
import com.example.mapper.AdviceMapper;
import com.example.utils.TokenUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 诊疗建议业务层方法
 */
@Service
public class AdviceService {

    @Autowired
    private AdviceMapper adviceMapper;

    public void add(Advice advice) {
        adviceMapper.insertAdvice(advice);
    }

    public void updateById(Advice advice) {
        adviceMapper.updateAdvice(advice);
    }

    public void deleteById(Integer id) {
        adviceMapper.deleteAdviceById(id);
    }

    public Advice selectById(Integer id) {
        return adviceMapper.selectAdviceById(id);
    }

    public List<Advice> selectAllByUserId(Integer userId) {
        return adviceMapper.selectAllAdviceByUser(userId);
    }

    public List<AdviceDto> selectAllAdviceByDoctor(Integer id) {
        return adviceMapper.selectAllAdviceByDoctor(id);

    }
}
