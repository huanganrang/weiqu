package rml.service;

import rml.model.LessonGood;

/**
 * Created by edward-echo on 2016/1/18.
 */
public interface LessonGoodService {
    int createLessonGood(LessonGood lessonGood);

    LessonGood getLessonGood(LessonGood lessonGood);

    int cancelLessonGood(LessonGood lessonGood);

    LessonGood getGoodByToken(String token);
}
