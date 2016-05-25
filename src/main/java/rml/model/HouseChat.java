package rml.model;

/**
 * Created by Administrator on 2015/9/29.
 */
public class HouseChat {

    private String userToken;

    private String houseToken;

    private int userId;

    private int houseId;

    private String content;

    private int type;

    private String moji;

    private String action;

    private int port;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMoji() {
        return moji;
    }

    public void setMoji(String moji) {
        this.moji = moji;
    }

    public int getType() {
        return type;
    }

    public HouseChat setType(int type) {
        this.type = type;
        return this;
    }

    public String getUserToken() {
        return userToken;
    }

    public HouseChat setUserToken(String userToken) {
        this.userToken = userToken;
        return this;
    }

    public String getHouseToken() {
        return houseToken;
    }

    public HouseChat setHouseToken(String houseToken) {
        this.houseToken = houseToken;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public HouseChat setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public int getHouseId() {
        return houseId;
    }

    public HouseChat setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    public String getContent() {
        return content;
    }

    public HouseChat setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HouseChat other = (HouseChat) obj;
        if (userId != other.userId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", houseId=" + houseId + ", content=" + content;
    }
}
