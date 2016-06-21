package rml.model;

import com.google.api.client.util.Lists;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public class House {
    private String title;

    private String channelToken;

    private String password;

    private String adminToken;

    private String userToken;

    private String token;

    private int channelId;

    private int onlineUserCount;

    private String houseIcon;

    private String adminNickName;

    private String openListen;

    private Integer openTime;

    private int userId;

    private String huanxinRoomId;

    private String desc;

    private int isDescNull;

    private int isPasswordNull;

    private String lessonDesc;

    private int isLessonDescNull;

    private int isScheduleNull;

    private List<Role> roleList= Lists.newArrayList();
    public int getIsScheduleNull() {
        return isScheduleNull;
    }

    public void setIsScheduleNull(int isScheduleNull) {
        this.isScheduleNull = isScheduleNull;
    }

    public int getIsLessonDescNull() {
        return isLessonDescNull;
    }

    public void setIsLessonDescNull(int isLessonDescNull) {
        this.isLessonDescNull = isLessonDescNull;
    }

    public String getLessonDesc() {
        return lessonDesc;
    }

    public void setLessonDesc(String lessonDesc) {
        this.lessonDesc = lessonDesc;
    }

    public int getIsDescNull() {
        return isDescNull;
    }

    public void setIsDescNull(int isDescNull) {
        this.isDescNull = isDescNull;
    }

    public int getIsPasswordNull() {
        return isPasswordNull;
    }

    public void setIsPasswordNull(int isPasswordNull) {
        this.isPasswordNull = isPasswordNull;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private List<HouseSchedule> schedules;


    public List<HouseSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<HouseSchedule> schedules) {
        this.schedules = schedules;
    }

    public String getHuanxinRoomId() {
        return huanxinRoomId;
    }

    public void setHuanxinRoomId(String huanxinRoomId) {
        this.huanxinRoomId = huanxinRoomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAdminNickName() {
        return adminNickName;
    }

    public void setAdminNickName(String adminNickName) {
        this.adminNickName = adminNickName;
    }

    public String getHouseIcon() {
        return "http://file.weiqu168.com/group1/M00/00/03/Cq6VllYuPgiAa3DBAAAVtGhG-rs96..png";
    }

    public void setHouseIcon(String houseIcon) {
        this.houseIcon = houseIcon;
    }

    public int getOnlineUserCount() {
        return onlineUserCount;
    }

    public void setOnlineUserCount(int onlineUserCount) {
        this.onlineUserCount = onlineUserCount;
    }

    public int getChannelId() {
        return channelId;
    }

    public House setChannelId(int channelId) {
        this.channelId = channelId;
        return this;
    }

    public String getChannelToken() {
        return channelToken;
    }

    public House setChannelToken(String channelToken) {
        this.channelToken = channelToken;
        return this;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getToken() {
        return token;
    }

    public House setToken(String token) {
        this.token = token;
        return this;
    }

    public String getUserToken() {
        return userToken;
    }

    public House setUserToken(String userToken) {
        this.userToken = userToken;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public House setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public House setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getAdminToken() {
        return adminToken;
    }

    public House setAdminToken(String adminToken) {
        this.adminToken = adminToken;
        return this;
    }

    private int id;

    public int getId() {
        return id;
    }

    public House setId(int id) {
        this.id = id;
        return this;
    }

    public String getOpenListen() {
        return openListen;
    }

    public void setOpenListen(String openListen) {
        this.openListen = openListen;
    }

    public Integer getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Integer openTime) {
        this.openTime = openTime;
    }
}
