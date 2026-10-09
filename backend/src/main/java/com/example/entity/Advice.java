package com.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 诊疗建议实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Advice {
    private Integer id; // 主键ID
    private Integer doctorId; // 医生ID
    private Integer userId; // 用户ID
    private String adviceContent; // 诊疗建议内容
    private Date adviceTime; // 诊疗建议时间
}
