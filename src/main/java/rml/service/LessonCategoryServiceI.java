package rml.service;

import rml.model.CommunityCategory;
import rml.model.LessonCategory;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public interface LessonCategoryServiceI {

    public List<LessonCategory> getCategory();

    public LessonCategory getCategoryById(int id);

}
