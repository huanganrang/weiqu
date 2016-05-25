package rml.dao;

import rml.model.Lesson;

import java.util.List;

/**
 * Created by Administrator on 2015/9/17.
 */
public interface LessonMapper {
    public int createLesson(Lesson Lesson);

    public Lesson getLesson(String token);

    public int updateLesson(Lesson Lesson);

    public Lesson getLessonById(int id);

    public Lesson getLessonByToken(String token);

    public List<Lesson> getLessonByCommunity(Lesson lesson);

    public List<Lesson> getLessonByCommunityTotal(Lesson lesson);

    int updateLessonUpNum(int id);

    int deleteLessonUpNum(int id);

    int updateLessonComment(int id);

    int deleteLessonComment(int id);

    List<Lesson> getUserLesson(Lesson lesson);

    List<Lesson> getUserLessonTotal(Lesson lesson);

    List<Lesson> getLessons(Lesson lesson);

    int deleteLesson(String token);

}
