package rml.model;

import java.util.Date;

/**
 * Created by Administrator on 2015/9/26.
 */
public class HouseUser {


    private int id;

    private Date createTime;

    private int houseId;

    private int identification;

    private int userId;

    private String houseToken;

    private String userToken;

    private String userIcon;

    private String nickName;

    private String houseUserToken;

    private String huanxin_uid;

    private String icon;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHuanxin_uid() {
        return huanxin_uid;
    }

    public void setHuanxin_uid(String huanxin_uid) {
        this.huanxin_uid = huanxin_uid;
    }

    public String getHouseUserToken() {
        return houseUserToken;
    }

    public void setHouseUserToken(String houseUserToken) {
        this.houseUserToken = houseUserToken;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }


    public String getHouseToken() {
        return houseToken;
    }

    public void setHouseToken(String houseToken) {
        this.houseToken = houseToken;
    }

    public String getUserToken() {
        return userToken;
    }

    public HouseUser setUserToken(String userToken) {
        this.userToken = userToken;
        return this;
    }

    public int getId() {
        return id;
    }

    public HouseUser setId(int id) {
        this.id = id;
        return this;
    }

    public int getHouseId() {
        return houseId;
    }

    public HouseUser setHouseId(int houseId) {
        this.houseId = houseId;
        return this;
    }

    public int getIdentification() {
        return identification;
    }

    public HouseUser setIdentification(int identification) {
        this.identification = identification;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    public HouseUser setUserId(int userId) {
        this.userId = userId;
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
        HouseUser houseUser = (HouseUser) obj;
        if (id != houseUser.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", houseToken=" + houseToken + ", userToken=" + userToken
                + ", identification =" + identification + "]";
    }

}
