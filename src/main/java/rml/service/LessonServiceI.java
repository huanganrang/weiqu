package rml.service;

import rml.model.Lesson;

import java.util.List;

/**
 * Created by Administrator on 2015/9/20.
 */
public interface LessonServiceI {
    public Lesson createLesson(Lesson Lesson);

    public Lesson getLesson(String token);

    public Lesson getLessonById(int id);

    public Lesson getLessonByToken(String token);

    public List<Lesson> getLessonByCommunity(Lesson lesson);

    int updateLessonUpNum(int id);

    int cancelLessonGood(int id);

    int updateLessonComment(int id);

    int deleteLessonComment(int id);

    List<Lesson> getLessonByCommunityTotal(Lesson lesson);

    List<Lesson> getUserLesson(Lesson lesson);

    int deleteLesson(String token);

    List<Lesson> getUserLessonTotal(Lesson lesson);

    List<Lesson> getLessons(Lesson lesson);
}
