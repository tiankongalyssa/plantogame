package com.sky.blind.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tb_user")
public class User {
    @Id
    private Integer id;

    private String username;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String password;
    private String nickname;
    private Integer age;
    private String userFace;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String images;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String education; //学历
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String need; //需求
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String personality;//个性
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String income; //收入
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String height;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String summary; //心灵独白
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String job;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String address;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String tag;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String constellcation; //星座
    @JsonIgnore
    private String createdUser;
    @JsonIgnore
    private Date createdTime;
    @JsonIgnore
    private String modifiedUser;
    @JsonIgnore
    private Date modifiedTime;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", age=" + age +
                ", userFace='" + userFace + '\'' +
                ", images='" + images + '\'' +
                ", education='" + education + '\'' +
                ", need='" + need + '\'' +
                ", personality='" + personality + '\'' +
                ", income='" + income + '\'' +
                ", height='" + height + '\'' +
                ", summary='" + summary + '\'' +
                ", job='" + job + '\'' +
                ", address='" + address + '\'' +
                ", tag='" + tag + '\'' +
                ", constellcation='" + constellcation + '\'' +
                ", createdUser='" + createdUser + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedUser='" + modifiedUser + '\'' +
                ", modifiedTime=" + modifiedTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUserFace() {
        return userFace;
    }

    public void setUserFace(String userFace) {
        this.userFace = userFace;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getConstellcation() {
        return constellcation;
    }

    public void setConstellcation(String constellcation) {
        this.constellcation = constellcation;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
