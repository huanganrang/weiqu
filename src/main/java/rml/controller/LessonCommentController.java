package rml.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.model.Community;
import rml.model.Lesson;
import rml.model.LessonComment;
import rml.model.User;
import rml.service.LessonCommentServiceI;
import rml.service.LessonServiceI;
import rml.service.UserServiceI;
import rml.util.ReturnJson;

import java.util.List;

/**
 * Created by Administrator on 2015/10/5 0005.
 */

@Controller
@RequestMapping("/Comment/Lesson")
public class LessonCommentController {


}
