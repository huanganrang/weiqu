package rml.model;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public class LessonUser {

    private int id;

    private String userToken;

    private String lessonToken;

    private int userId;

    private int lessonId;

    private Date createDate;

    private double paidMoney;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getLessonToken() {
        return lessonToken;
    }

    public void setLessonToken(String lessonToken) {
        this.lessonToken = lessonToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public double getPaidMoney() {
        return paidMoney;
    }

    public void setPaidMoney(double paidMoney) {
        this.paidMoney = paidMoney;
    }
}
