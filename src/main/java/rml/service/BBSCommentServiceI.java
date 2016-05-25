package rml.service;

import rml.model.BBSComment;

import java.util.List;

/**
 * Created by Administrator on 2015/10/11 0011.
 */
public interface BBSCommentServiceI {
    int createComment(BBSComment bbsComment);

    List<BBSComment> getBbsComments(BBSComment BBSComment);

    BBSComment getCommentByToken(String token);

    List<BBSComment> getBbsCommentsTotal(BBSComment BBSComment);

}
