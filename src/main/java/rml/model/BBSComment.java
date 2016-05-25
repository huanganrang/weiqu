package rml.model;

import java.util.Date;

/**
 * Created by Administrator on 2015/10/11 0011.
 */
public class BBSComment {



    private String userToken;

    private int id;

    private String content;

    private Date createTime;

    private int userId;

    private String token;

    private int bbsId;

    private String bbsToken;

    private String imgs;
    private String userName;

    private int pageSize;

    private int pageNo;

    private int startSize;

    public int getStartSize() {
        return (pageNo-1)*pageSize;
    }

    public void setStartSize(int startSize) {
        this.startSize = startSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getBbsId() {
        return bbsId;
    }

    public void setBbsId(int bbsId) {
        this.bbsId = bbsId;
    }

    public String getBbsToken() {
        return bbsToken;
    }

    public void setBbsToken(String bbsToken) {
        this.bbsToken = bbsToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getImgs() {
        return imgs;
    }

    public void setImgs(String imgs) {
        this.imgs = imgs;
    }
}
