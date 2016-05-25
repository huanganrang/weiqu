package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.LessonMapper;
import rml.model.Lesson;
import rml.model.RecordClient;
import rml.service.LessonServiceI;
import rml.util.MD5;
import rml.util.RandomGUID;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2015/9/20.
 */
@Service("lessonService")
public class LessonServiceImpl implements LessonServiceI {

    @Autowired
    LessonMapper lessonMapper;


    @Override
    public Lesson createLesson(Lesson lesson) {
        lesson.setToken(UUID.randomUUID().toString());
        lessonMapper.createLesson(lesson);
        return lesson;
    }
    private Date convertDate(String date){
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date returnValue = null;
        try {
            returnValue = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return returnValue;
    }


    @Override
    public Lesson getLesson(String token) {
       Lesson lesson  = lessonMapper.getLesson(token);
       return lesson;
    }

    @Override
    public Lesson getLessonById(int id) {
        return lessonMapper.getLessonById(id);
    }

    @Override
    public Lesson getLessonByToken(String token) {
        return lessonMapper.getLessonByToken(token);
    }

    @Override
    public List<Lesson> getLessonByCommunity(Lesson lesson) {
        return lessonMapper.getLessonByCommunity(lesson);
    }

    @Override
    public int updateLessonUpNum(int id) {
        return lessonMapper.updateLessonUpNum(id);
    }

    @Override
    public int cancelLessonGood(int id) {
        return lessonMapper.deleteLessonUpNum(id);
    }

    @Override
    public int updateLessonComment(int id) {
        return lessonMapper.updateLessonComment(id);
    }

    @Override
    public int deleteLessonComment(int id) {
        return lessonMapper.deleteLessonComment(id);
    }

    @Override
    public List<Lesson> getLessonByCommunityTotal(Lesson lesson) {
        return lessonMapper.getLessonByCommunityTotal(lesson);
    }

    @Override
    public List<Lesson> getUserLesson(Lesson lesson) {
        return lessonMapper.getUserLesson(lesson);
    }

    @Override
    public int deleteLesson(String token) {
        return lessonMapper.deleteLesson(token);
    }

    @Override
    public List<Lesson> getUserLessonTotal(Lesson lesson) {
        return lessonMapper.getUserLessonTotal(lesson);
    }

    @Override
    public List<Lesson> getLessons(Lesson lesson) {
        return lessonMapper.getLessons(lesson);
    }
}
