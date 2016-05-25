package rml.dao;

import rml.model.ChannelCategory;
import rml.model.LessonCategory;

import java.util.List;

/**
 * Created by Administrator on 2015/9/24.
 */
public interface LessonCategoryMapper {
    public List<LessonCategory> getCategory();

    public LessonCategory getCategoryById(int id);
}
