package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.LessonGoodMapper;
import rml.model.LessonGood;
import rml.service.LessonGoodService;

/**
 * Created by edward-echo on 2016/1/18.
 */

@Service("lessonGoodService")
public class LessonGoodServiceImpl implements LessonGoodService {

    @Autowired
    LessonGoodMapper lessonGoodMapper;

    @Override
    public int createLessonGood(LessonGood lessonGood) {
        return lessonGoodMapper.createLessonGood(lessonGood);
    }

    @Override
    public LessonGood getLessonGood(LessonGood lessonGood) {
        return lessonGoodMapper.getLessonGood(lessonGood);
    }

    @Override
    public int cancelLessonGood(LessonGood lessonGood) {
        return lessonGoodMapper.cancelLessonGood(lessonGood);
    }

    @Override
    public LessonGood getGoodByToken(String token) {
        return lessonGoodMapper.getGoodByToken(token);
    }
}
