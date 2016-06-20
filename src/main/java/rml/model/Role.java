package rml.model;

import com.google.api.client.util.Lists;

import java.util.List;

/**
 * Created by Jianghui on 2016/6/19.
 */
public class Role {

    private String id;
    private String name;
    private String remark;
    private Integer seq;
    private List<Resource> resourceList= Lists.newArrayList();

    public Role(){}

    public Role(String rid) {
        this.id=rid;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public List<Resource> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<Resource> resourceList) {
        this.resourceList = resourceList;
    }
}
