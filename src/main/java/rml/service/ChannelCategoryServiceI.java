package rml.service;

import rml.dao.CategoryMapper;
import rml.model.ChannelCategory;
import rml.model.CommunityCategory;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public interface ChannelCategoryServiceI {

    public List<ChannelCategory> getCategory();

}
