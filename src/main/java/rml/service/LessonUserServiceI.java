package rml.service;

import rml.model.LessonUser;

import java.util.List;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public interface LessonUserServiceI {

    public LessonUser createLessonUser(LessonUser lessonUser);

    public List<LessonUser> getLessonUsers(int lessonId);

}
