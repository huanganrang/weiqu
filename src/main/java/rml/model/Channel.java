package rml.model;

/**
 * Created by Administrator on 2015/9/25.
 */
public class Channel {

    private int count;

    private int id;

    private String displayIconUrl;

    private int userOnlineCount;

    private String categoryName;

    private String name;

    private String shortDesc;

    private String url;

    private int status;

    private int categoryId;

    private String channelIcon;

    private String token;

    private int pageSize;

    private int pageNo;

    private int startSize;

    private String nickName;

    private int userId;

    private String userToken;


    public String getDisplayIconUrl() {
        return displayIconUrl;
    }

    public void setDisplayIconUrl(String displayIconUrl) {
        this.displayIconUrl = displayIconUrl;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getUserOnlineCount() {
        return userOnlineCount;
    }

    public void setUserOnlineCount(int userOnlineCount) {
        this.userOnlineCount = userOnlineCount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getToken() {
        return token;
    }

    public Channel setToken(String token) {
        this.token = token;
        return this;
    }

    public String getChannelIcon() {
        return channelIcon;
    }

    public Channel setChannelIcon(String channelIcon) {
        this.channelIcon = channelIcon;
        return this;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public Channel setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Channel setUrl(String url) {
        this.url = url;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Channel setStatus(int status) {
        this.status = status;
        return this;
    }

    public int getId() {
        return id;
    }

    public Channel setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Channel setName(String name) {
        this.name = name;
        return this;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public Channel setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
        return this;
    }
    @Override
    public String toString() {
        return "Channel [id=" + id + ", userToken=" + userToken + ", channelIcon=" + channelIcon
                + ", shortDesc =" + shortDesc + ", categoryId =" + categoryId + ",name =" + name +"]";
    }

}
