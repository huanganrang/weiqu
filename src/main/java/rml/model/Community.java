package rml.model;

import java.util.List;

/**
 * Created by Administrator on 2015/9/25.
 */
public class Community {

    private int id;

    private String name;

    private String shortDesc;

    private String url;

    private int status;

    private int categoryId;

    private String icon;

    private String token;

    private int userId;

    private String userToken;

    private String defaultImg;

    private int totalPeople;

    private String categoryName;

    private String communityToken;

    private boolean isAdmin;

    private int pageSize;

    private int pageNo;

    private int startSize;

    private boolean isJoin;

    private List<Lesson> lessons;

    private List<CommunityBBS> bbses;

    public List<CommunityBBS> getBbses() {
        return bbses;
    }

    public void setBbses(List<CommunityBBS> bbses) {
        this.bbses = bbses;
    }

    public boolean isJoin() {
        return isJoin;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public boolean getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(boolean isJoin) {
        this.isJoin = isJoin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getCommunityToken() {
        return communityToken;
    }

    public void setCommunityToken(String communityToken) {
        this.communityToken = communityToken;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }


    public String getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(String defaultImg) {
        this.defaultImg = defaultImg;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public Community setToken(String token) {
        this.token = token;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Community setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Community setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Community setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getId() {
        return id;
    }

    public Community setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Community setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public Community setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
        return this;
    }

}
