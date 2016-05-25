package rml.model;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015/9/12.
 */
public class User{

    private String uid;

    private String channel;

    private String token;

    private int id;

    private int identification;

    private int age;

    private String nickName;

    private String mobile;

    private int isFromThirdPath;

    private String thirdPartToken;

    private String password;

    private int isSuperUser;

    private String newPassword;

    private String validCode;

    private String icon;

    private int type;

    private Date createTime;

    private Date updateTime;

    private String huanxinUid;

    private String content;

    private String accountName;

    private String identifier;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHuanxinUid() {
        return huanxinUid;
    }

    public void setHuanxinUid(String huanxinUid) {
        this.huanxinUid = huanxinUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getIdentification() {
        return identification;
    }

    public User setIdentification(int identification) {
        this.identification = identification;
        return this;
    }


    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getIsFromThirdPath() {
        return isFromThirdPath;
    }

    public void setIsFromThirdPath(int isFromThirdPath) {
        this.isFromThirdPath = isFromThirdPath;
    }

    public String getThirdPartToken() {
        return thirdPartToken;
    }

    public void setThirdPartToken(String thirdPartToken) {
        this.thirdPartToken = thirdPartToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsSuperUser() {
        return isSuperUser;
    }

    public void setIsSuperUser(int isSuperUser) {
        this.isSuperUser = isSuperUser;
    }

    public User(){
        id=0;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + nickName + ", password=" + password
                + ", mobile =" + mobile + ", token =" + token + "]";
    }


}

