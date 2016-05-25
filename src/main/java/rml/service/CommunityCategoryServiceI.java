package rml.service;

import rml.model.ChannelCategory;
import rml.model.CommunityCategory;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public interface CommunityCategoryServiceI {

    public List<CommunityCategory> getCategory();

    public CommunityCategory getCategoryById(int id);

}
