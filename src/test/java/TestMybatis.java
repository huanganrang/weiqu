import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.springframework.util.SocketUtils;
import rml.model.*;
import rml.service.*;

import com.alibaba.fastjson.JSON;
import rml.model.User;
import rml.model.ValidCode;
import rml.service.UserServiceI;

import com.alibaba.fastjson.JSON;
import rml.service.ValidCodeService;

@RunWith(SpringJUnit4ClassRunner.class) // = extends SpringJUnit4ClassRunner
@ContextConfiguration(locations = { "classpath:spring.xml", "classpath:spring-mybatis.xml" })
public class TestMybatis {

	private static final Logger logger = Logger.getLogger(TestMybatis.class);

	@Autowired
	private LessonUserServiceI lessonUserService;

	@Autowired
	private LessonCategoryServiceI lessonCategoryService;

	@Autowired
	private LessonServiceI lessonService;

	@Autowired
	private CommunityServiceI communityService;

	@Autowired
	private UserServiceI userService;

	@Autowired
	private CommunityCategoryServiceI communityCategoryService;


	@Autowired
	private HouseChatServiceI chatService;
	@Autowired
	private ChannelCategoryServiceI channelCategoryService;

	private UserServiceI UserService;

	public UserServiceI getUserService() {
		return UserService;
	}

	@Autowired
	public void setUserService(UserServiceI UserService) {
		this.UserService = UserService;
	}

   @Autowired
	private ValidCodeService validCodeService;

	@Autowired
	private HouseServiceI houseService;

	@Autowired
	private ChannelServiceI channelService;

	@Autowired
	private HouseUserServiceI houseUserService;

	@Autowired
	private LessonCommentServiceI lessonCommentService;

	@Test
	public void testCheckValidCode() {
		ValidCode value = new ValidCode();
		value.setMobile("123");
		value.setValidCode("13311");
		int codce = validCodeService.checkValidCode(value);
		Assert.assertEquals(codce,1);
	}


	@Test
	public void testInsertValidCode() {
		ValidCode value = new ValidCode();
		value.setMobile("123");
		value.setValidCode("13311");
		value.setChannel("register");
		value.setCreateTime(new Date());
		int codce = validCodeService.insert(value);
	}


	@Test
	public void testInsertHouse() {
		House value = new House();
		value.setPassword("12345");
	    value.setTitle("test_house");
		House house = houseService.createHouse(value);
		System.err.print(house);
	}


	@Test
	public void testGetCategory() {
		channelCategoryService.getCategory();
	}

	@Test
	public void testCreateChannel() {
		Channel channel = new Channel();
		channel.setShortDesc("test_desc");
		channel.setName("test_name");
		channel.setStatus(1);
		channel.setCategoryId(1);
		channelService.createChannel(channel);

	}


	@Test
	public void testDeleteUser() {
		HouseUser user = new HouseUser();
		user.setUserId(25);
		user.setHouseId(16);
		houseUserService.deleteUser(user);
	}

	@Test
	public void testChat() {
		HouseChat chat = new HouseChat();
	    chat.setContent("test_content");
		chat.setUserId(125);
		chat.setHouseId(16);
		chatService.pushChat(chat);
		System.err.println(chatService.pullChat(chat));

	}

	@Test
	public void testBatchInsertUser(){
		HouseChat chat = new HouseChat();
		chat.setUserId(125);
		chat.setHouseId(16);
		System.err.println(chatService.pullChat(chat));
	}

	@Test
	public void testCommnityCategory(){
		System.err.print(communityCategoryService.getCategory());
	}



	@Test
	public void testCreateCommunity() {
		Community channel = new Community();
		channel.setShortDesc("test_desc");
		channel.setName("test_name");
		channel.setStatus(1);
		channel.setCategoryId(1);
		communityService.createCommunity(channel);
	}

	@Test
	public void testCreateLesson() {
		Lesson lesson = new Lesson();
		lesson.setCommunityId(1);
		lesson.setIcon("123");
		lessonService.createLesson(lesson);
	}

	@Test
	public void testLessonCategory(){
		System.err.print(lessonCategoryService.getCategory());
	}



	@Test
	public void testLessonCommentSave(){
		LessonComment comment = new LessonComment();
		comment.setUserId(1);
		comment.setContent("aaa");
		comment.setLessonId(1);
		lessonCommentService.createLessonComment(comment);
	}



	@Test
	public void testLessonUserSave(){
		LessonUser lessonUser = new LessonUser();
		lessonUser.setLessonId(1);
		lessonUser.setUserId(1);
		lessonUser.setCreateDate(new Date());
		lessonUserService.createLessonUser(lessonUser);
	}



	@Autowired
	CommunityBbsServiceI communityBbsService;

	@Test
	public void testCommunityBbs(){
		CommunityBBS bbs = new CommunityBBS();
		bbs.setContent("123");
		bbs.setCommunityId(123);
		bbs.setUserId(111);
		bbs.setTitle("test");
		bbs.setToken("sfdasdf");
		communityBbsService.createBlog(bbs);
	}


	@Autowired
	BBSCommentServiceI bbsCommentService;
	@Test
	public void testBbsComment(){
		BBSComment bbsComment = new BBSComment();
		bbsComment.setContent("sdfsdf");
		bbsComment.setUserId(1);
		bbsComment.setBbsId(1);
		bbsCommentService.createComment(bbsComment);
	}

	@Test
	public void testTcpPort(){
		System.err.print(channelService.count());
	}

	@Test
	public void testGetHouseUser()
	{
		List<HouseUser> list = houseUserService.getHouseUsers(30);
		System.err.print(list.size());
	}

}
