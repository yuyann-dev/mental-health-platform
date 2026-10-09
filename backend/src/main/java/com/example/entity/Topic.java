package com.example.entity;

import lombok.Data;

@Data
public class Topic {
    private Integer id;
    private String title;
    private Integer typeId;
    private String aName;
    private Integer aScore;
    private String bName;
    private Integer bScore;
    private String cName;
    private Integer cScore;
    private String dName;
    private Integer dScore;
    private Integer score;

    private String typeName;

    private String userAnswer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public Integer getaScore() {
        return aScore;
    }

    public void setaScore(Integer aScore) {
        this.aScore = aScore;
    }

    public Integer getbScore() {
        return bScore;
    }

    public void setbScore(Integer bScore) {
        this.bScore = bScore;
    }

    public Integer getcScore() {
        return cScore;
    }

    public void setcScore(Integer cScore) {
        this.cScore = cScore;
    }

    public Integer getdScore() {
        return dScore;
    }

    public void setdScore(Integer dScore) {
        this.dScore = dScore;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
