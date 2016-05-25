package rml.dao;

import rml.model.ChannelCategory;
import rml.model.CommunityCategory;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public interface CommunityCategoryMapper {
    public List<CommunityCategory> getCategory();

    public CommunityCategory getCategoryById(int id);
}
