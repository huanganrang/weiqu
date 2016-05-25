package rml.model;

/**
 * Created by Administrator on 2015/9/24.
 */
public class ChannelCategory {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public ChannelCategory setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ChannelCategory setName(String name) {
        this.name = name;
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
        CommunityCategory other = (CommunityCategory) obj;
        if (id != other.getId())
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name+"]";
    }

}
