package com.example.entity;

import lombok.Data;

import java.util.List;
@Data
public class Doctor extends Account {

    /** 主键ID */
    private Integer id;
    /** 账号 */
    private String username;
    private String password;
    private String name;
    private String avatar;
    private String role;
    private String phone;
    private String email;
    private Integer seniority;
    private String content;
    private String code;
    private String certificate;
    private String status;
    private List<Advice> adviceList;
    private String mbwt;
    private Integer num;



}
