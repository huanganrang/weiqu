package rml.model;

/**
 * Created by Administrator on 2015/9/20.
 */
public class WhiteBoard {
    private int id;

    private int houseId;

    private String houseToken;

    private String userToken;

    private int userId;

    private String LastTime;

    private String PushUrl;

    private String PullUrl;

    private String Name;

    private double WorthMoney;

    private String token;

    private int type;

    private String startTime;

    public int getType() {
        return type;
    }

    public WhiteBoard setType(int type) {
        this.type = type;
        return this;
    }

    public String getToken() {
        return token;
    }

    public WhiteBoard setToken(String token) {
        this.token = token;
        return this;
    }

    public int getId() {
        return id;
    }

    public WhiteBoard setId(int id) {
        this.id = id;
        return this;
    }

    public int getHouseId() {
        return houseId;
    }

    public WhiteBoard setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    public String getHouseToken() {
        return houseToken;
    }

    public WhiteBoard setHouseToken(String houseToken) {
        this.houseToken = houseToken;
        return this;
    }

    public String getUserToken() {
        return userToken;
    }

    public WhiteBoard setUserToken(String userToken) {
        this.userToken = userToken;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public WhiteBoard setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public String getLastTime() {
        return LastTime;
    }

    public WhiteBoard setLastTime(String lastTime) {
        LastTime = lastTime;
        return this;
    }

    public String getPushUrl() {
        return PushUrl;
    }

    public WhiteBoard setPushUrl(String pushUrl) {
        PushUrl = pushUrl;
        return this;
    }

    public String getPullUrl() {
        return PullUrl;
    }

    public WhiteBoard setPullUrl(String pullUrl) {
        PullUrl = pullUrl;
        return this;
    }

    public String getName() {
        return Name;
    }

    public WhiteBoard setName(String name) {
        Name = name;
        return this;
    }

    public double getWorthMoney() {
        return WorthMoney;
    }

    public WhiteBoard setWorthMoney(double worthMoney) {
        WorthMoney = worthMoney;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public WhiteBoard setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }
}