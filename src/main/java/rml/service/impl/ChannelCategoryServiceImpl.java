package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.CategoryMapper;
import rml.model.ChannelCategory;
import rml.service.ChannelCategoryServiceI;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */

@Service("channelCategoryService")
public class ChannelCategoryServiceImpl implements ChannelCategoryServiceI{

    @Autowired
    private CategoryMapper categoryMapper;

    public List<ChannelCategory> getCategory(){
        return  categoryMapper.getChannelCategory();
    }

}
