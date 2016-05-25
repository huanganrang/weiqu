package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.CommunityCategoryMapper;
import rml.dao.LessonCategoryMapper;
import rml.model.CommunityCategory;
import rml.model.LessonCategory;
import rml.service.CommunityCategoryServiceI;
import rml.service.LessonCategoryServiceI;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */

@Service("lessonCategoryService")
public class LessonCategoryServiceImpl implements LessonCategoryServiceI {
    @Autowired
    private LessonCategoryMapper lessonCategoryMapper;

    public List<LessonCategory> getCategory(){
        return  lessonCategoryMapper.getCategory();
    }

    @Override
    public LessonCategory getCategoryById(int id) {
        return lessonCategoryMapper.getCategoryById(id);
    }

}
