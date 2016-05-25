package rml.dao;

import org.springframework.stereotype.Component;
import rml.model.BBSComment;

import java.util.List;

/**
 * Created by Administrator on 2015/10/11 0011.
 */
@Component
public interface BBSCommentMapper {

    int createComment(BBSComment bbsComment);

    List<BBSComment> getBbsComments(BBSComment BBSComment);

    List<BBSComment> getBbsCommentsTotal(BBSComment BBSComment);

    BBSComment getCommentByToken(String token);
}
