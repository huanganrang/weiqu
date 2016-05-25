package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.LessonUserMapper;
import rml.model.LessonUser;
import rml.service.LessonUserServiceI;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
@Service("lessonUserService")
public class LessonUserServiceImpl implements LessonUserServiceI{

    @Autowired
    LessonUserMapper lessonUserMapper;

    @Override
    public LessonUser createLessonUser(LessonUser lessonUser) {
           lessonUser.setCreateDate(new Date());
           lessonUserMapper.createLessonUser(lessonUser);
           return lessonUser;

    }

    @Override
    public List<LessonUser> getLessonUsers(int lessonId) {
        return lessonUserMapper.getLessonUsers(lessonId);
    }
}
