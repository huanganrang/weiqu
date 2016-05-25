package rml.model;


import java.util.Date;
/**
 * Created by edward-echo on 2016/1/18.
 */
public class LessonGood {

    private int id;

    private int userId;

    private int lessonId;

    private String userToken;

    private String lessonToken;

    private Date createTime;

    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
