package rml.dao;

import rml.model.LessonUser;

import java.util.List;

/**
 * Created by Administrator on 2015/9/25.
 */
public interface LessonUserMapper {

    public int createLessonUser(LessonUser lessonUser);

    public List<LessonUser> getLessonUsers(int lessonId);

}
