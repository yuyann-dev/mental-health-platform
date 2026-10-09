package com.example.entity;

import java.util.List;
import lombok.Data;

@Data
public class TestPaper {
    private Integer id;
    private String title;
    private String img;
    private String content;
    private Integer typeId;
    private Integer doctorId;
    private Integer num;
    private Integer score;
    private String ids;
    private String status;
    private Integer testNum;
    private String time;
    private String aRange;
    private String bRange;
    private String cRange;
    private String aAnswer;
    private String bAnswer;
    private String cAnswer;

    private String typeName;
    private String doctorName;
    private String doctorAvatar;
    private List<Integer> idList;
    private List<Topic> topicList;
    private Integer aLeft;
    private Integer aRight;
    private Integer bLeft;
    private Integer bRight;
    private Integer cLeft;
    private Integer cRight;
    private Integer flag;

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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTestNum() {
        return testNum;
    }

    public void setTestNum(Integer testNum) {
        this.testNum = testNum;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getaRange() {
        return aRange;
    }

    public void setaRange(String aRange) {
        this.aRange = aRange;
    }

    public String getbRange() {
        return bRange;
    }

    public void setbRange(String bRange) {
        this.bRange = bRange;
    }

    public String getcRange() {
        return cRange;
    }

    public void setcRange(String cRange) {
        this.cRange = cRange;
    }

    public String getaAnswer() {
        return aAnswer;
    }

    public void setaAnswer(String aAnswer) {
        this.aAnswer = aAnswer;
    }

    public String getbAnswer() {
        return bAnswer;
    }

    public void setbAnswer(String bAnswer) {
        this.bAnswer = bAnswer;
    }

    public String getcAnswer() {
        return cAnswer;
    }

    public void setcAnswer(String cAnswer) {
        this.cAnswer = cAnswer;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorAvatar() {
        return doctorAvatar;
    }

    public void setDoctorAvatar(String doctorAvatar) {
        this.doctorAvatar = doctorAvatar;
    }

    public List<Integer> getIdList() {
        return idList;
    }

    public void setIdList(List<Integer> idList) {
        this.idList = idList;
    }

    public List<Topic> getTopicList() {
        return topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public Integer getaLeft() {
        return aLeft;
    }

    public void setaLeft(Integer aLeft) {
        this.aLeft = aLeft;
    }

    public Integer getaRight() {
        return aRight;
    }

    public void setaRight(Integer aRight) {
        this.aRight = aRight;
    }

    public Integer getbLeft() {
        return bLeft;
    }

    public void setbLeft(Integer bLeft) {
        this.bLeft = bLeft;
    }

    public Integer getbRight() {
        return bRight;
    }

    public void setbRight(Integer bRight) {
        this.bRight = bRight;
    }

    public Integer getcLeft() {
        return cLeft;
    }

    public void setcLeft(Integer cLeft) {
        this.cLeft = cLeft;
    }

    public Integer getcRight() {
        return cRight;
    }

    public void setcRight(Integer cRight) {
        this.cRight = cRight;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
