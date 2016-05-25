package rml.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2015/10/22 0022.
 */
public class File implements Serializable {



    private String name;

    private String ext;

    private String token;

    private String remoteUrl;

    private int type;

    private long lenght;

    private Date createDate;

    private byte[] content;

    private String groupName;

    private String remoteFileName;

    private String userToken;

    private String houseToken;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getHouseToken() {
        return houseToken;
    }

    public void setHouseToken(String houseToken) {
        this.houseToken = houseToken;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public void setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRemoteUrl() {
        return remoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        this.remoteUrl = remoteUrl;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getLenght() {
        return lenght;
    }

    public void setLenght(long lenght) {
        this.lenght = lenght;
    }

    @Override
    public String toString() {
        return "User [ name=" + name + ", ext=" + ext
                + ", content =" + content + "]";
    }

}
