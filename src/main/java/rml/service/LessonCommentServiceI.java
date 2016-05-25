package rml.service;

import rml.model.Lesson;
import rml.model.LessonComment;

import java.util.List;

/**
 * Created by Administrator on 2015/10/5 0005.
 */
public interface LessonCommentServiceI {
    public LessonComment createLessonComment(rml.model.LessonComment lessonComment);

    public LessonComment getCommentById(int id);

    public LessonComment getCommentByToken(String token);

    public List<LessonComment> getLessonComments(LessonComment lessonComment);

    int deleteComment(LessonComment lessonComment);

    List<LessonComment> getLessonCommentsTotal(LessonComment lessonComment);

}
