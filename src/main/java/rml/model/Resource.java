package rml.model;

/**
 * Created by Jianghui on 2016/6/19.
 */
public class Resource {
    private  String id;
    private String icon;
    private String name;
    private String remark;
    private Integer seq;
    private String url;
    private Resource parent;
    private String pids;
    private String tResourceTypeId;

    public Resource getParent() {
        return parent;
    }

    public void setParent(Resource parent) {
        this.parent = parent;
    }

    public String getPids() {
        return pids;
    }

    public void setPids(String pids) {
        this.pids = pids;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }



    public String gettResourceTypeId() {
        return tResourceTypeId;
    }

    public void settResourceTypeId(String tResourceTypeId) {
        this.tResourceTypeId = tResourceTypeId;
    }
}
