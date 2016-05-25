package rml.model;

/**
 * Created by Administrator on 2015/9/20.
 */
public class Video{
    private int id;

    private int houseId;

    private String houseToken;

    private String PullUrl;

    private String Name;

    private String token;

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public Video setToken(String token) {
        this.token = token;
        return this;
    }

    public int getId() {
        return id;
    }

    public Video setId(int id) {
        this.id = id;
        return this;
    }

    public int getHouseId() {
        return houseId;
    }

    public Video setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    public String getHouseToken() {
        return houseToken;
    }

    public Video setHouseToken(String houseToken) {
        this.houseToken = houseToken;
        return this;
    }

    public String getPullUrl() {
        return PullUrl;
    }

    public Video setPullUrl(String pullUrl) {
        PullUrl = pullUrl;
        return this;
    }

    public String getName() {
        return Name;
    }

    public Video setName(String name) {
        Name = name;
        return this;
    }

}