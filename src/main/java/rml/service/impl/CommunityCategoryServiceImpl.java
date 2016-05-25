package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.CategoryMapper;
import rml.dao.CommunityCategoryMapper;
import rml.model.ChannelCategory;
import rml.model.CommunityCategory;
import rml.service.ChannelCategoryServiceI;
import rml.service.CommunityCategoryServiceI;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */

@Service("communityCategoryService")
public class CommunityCategoryServiceImpl implements CommunityCategoryServiceI {

    @Autowired
    private CommunityCategoryMapper categoryMapper;

    public List<CommunityCategory> getCategory(){
        return  categoryMapper.getCategory();
    }

    @Override
    public CommunityCategory getCategoryById(int id) {
        return categoryMapper.getCategoryById(id);
    }

}
