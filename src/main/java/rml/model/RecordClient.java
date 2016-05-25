package rml.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/10/7 0007.
 */
public class RecordClient implements Serializable{


    private  String userToken;

    private String houseToken;

    private String content;

    private int type;

    private String action;

    private String userIcon;

    private String nickName;

    private String fileName;

    private String fileUrl;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void DoAction(){

    }

}
