package rml.controller;

import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import rml.comm.Constants;
import rml.httpclient.apidemo.EasemobIMUsers;
import rml.httpclient.utils.HTTPClientUtils;
import rml.httpclient.vo.EndPoints;
import rml.model.User;
import rml.model.ValidCode;
import rml.service.UserServiceI;
import rml.service.ValidCodeService;
import rml.service.UserServiceI;
import rml.util.HTTPMethod;
import rml.util.HttpXmlClient;
import rml.util.ReturnJson;

@Controller
@RequestMapping("/User")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	private static final JsonNodeFactory factory = new JsonNodeFactory(false);

	@Autowired
	ValidCodeService validCodeService;

	private UserServiceI userService;

	public UserServiceI getUserService() {
		return userService;
	}

	@Autowired
	public void setUserService(UserServiceI UserService) {
		this.userService = UserService;
	}
	
	@RequestMapping(value="/listUser")
	public String listUser(HttpServletRequest request) {
		
		List <User> list = userService.getAll();
		request.setAttribute("userlist", list);
		return "listUser";
	}
	
	@RequestMapping(value="/User",method = RequestMethod.POST)
	@ResponseBody
	public Object addUser(@RequestBody User user) {

	ReturnJson returnJson = new ReturnJson();
	returnJson.setErrorCode(1000);
	returnJson.setReturnMessage("调用成功"+user.toString());
	returnJson.setServerStatus(0);


	if(StringUtils.isEmpty(user.getNickName())||StringUtils.isEmpty(user.getMobile())||StringUtils.isEmpty(user.getPassword())){

		returnJson.setErrorCode(1002);
		returnJson.setReturnMessage("传入参数为空" + user.toString());
		returnJson.setServerStatus(1);
		return returnJson;
		}
		ValidCode code = new ValidCode();
		code.setValidCode(user.getValidCode());
		code.setMobile(user.getMobile());

		User value = userService.selectByMobile(user.getMobile());
		if(value!=null){
			returnJson.setErrorCode(1001);
			returnJson.setReturnMessage("该用户已注册，请重新输入手机号" + code.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		int flag =   validCodeService.checkValidCode(code);
		if(flag==1){
			returnJson.setErrorCode(1003);
			returnJson.setReturnMessage("验证码错误，请重新输入" + code.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		User user1 = userService.getAccountName(user.getNickName());
		if(user1!=null){
			returnJson.setErrorCode(1005);
			returnJson.setReturnMessage("nickName重复");
			returnJson.setServerStatus(1);
			return returnJson;
		}
	try {
		user.setIcon("file.weiqu168.com/group1/M00/00/1F/Cq6VllZgUR-AJdr8AAAXRGswmOk45..png");
		user.setAccountName(user.getNickName());
		userService.insert(user);
		ObjectNode datanode = JsonNodeFactory.instance.objectNode();
		datanode.put("username",user.getHuanxinUid());
		datanode.put("password", user.getPassword());
		ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
		if (null != createNewIMUserSingleNode) {
			LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
		}
	}catch(Exception ex){
		ex.printStackTrace();
		returnJson.setErrorCode(1001);
		returnJson.setReturnMessage("服务器异常");
		returnJson.setServerStatus(2);
		return returnJson;
	}
		returnJson.setReturnObject(user);
		returnJson.setReturnValue(user.getToken());
		return returnJson;
	}



	@RequestMapping(value="/User/Channel",method = RequestMethod.POST)
	@ResponseBody
	public Object addThirdUser(@RequestBody User user) {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(46000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);


		if(StringUtils.isEmpty(user.getNickName())||StringUtils.isEmpty(user.getIcon())||StringUtils.isEmpty(user.getChannel())||StringUtils.isEmpty(user.getUid())){

			returnJson.setErrorCode(46002);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
			User tmp = null;
		try{
			tmp = userService.selectUid(user.getUid());
		}catch (Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(46004);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
		}
		if(tmp!=null){
			returnJson.setReturnValue(tmp.getToken());
			returnJson.setReturnObject(tmp);
			return returnJson;
		}
		try {
			userService.insertThird(user);
			ObjectNode datanode = JsonNodeFactory.instance.objectNode();
			datanode.put("username",user.getHuanxinUid());
			datanode.put("password", user.getPassword());
			ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
			if (null != createNewIMUserSingleNode) {
				LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(46004);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
		}
		returnJson.setReturnValue(user.getToken());
		returnJson.setReturnObject(user);
		return returnJson;
	}


	@RequestMapping(value="/User/Temp",method = RequestMethod.POST)
	@ResponseBody
	public Object tempUser() {

		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(46000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		User user = new User();
		User tmp = null;
		try{
		}catch (Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(46004);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
		}
		if(tmp!=null){
			returnJson.setReturnValue(tmp.getToken());
			returnJson.setReturnObject(tmp);
			return returnJson;
		}
		try {
			user.setIcon("file.weiqu168.com/group1/M00/00/1F/Cq6VllZgUdeAFU_HAAATyb3T4PM78..png");
			userService.insertTemp(user);
			ObjectNode datanode = JsonNodeFactory.instance.objectNode();
			datanode.put("username",user.getHuanxinUid());
			datanode.put("password", user.getPassword());
			ObjectNode createNewIMUserSingleNode = EasemobIMUsers.createNewIMUserSingle(datanode);
			if (null != createNewIMUserSingleNode) {
				LOGGER.info("注册IM用户[单个]: " + createNewIMUserSingleNode.toString());
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(46004);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
		}
		returnJson.setReturnValue(user.getToken());
		returnJson.setReturnObject(user);
		return returnJson;
	}


	@RequestMapping(value="/deleteUser")
	public String deleteUser(String id) {

		userService.delete(id);
		return "redirect:/UserController/listUser.do";
	}

	@RequestMapping(value="/updateUserUI")
	public String updateUserUI(String id, HttpServletRequest request) {
		int value = new Integer(id);
		User User = userService.selectByPrimaryKey(value);
		request.setAttribute("user", User);
		return "updateUser";
	}


	@RequestMapping(value="/Security/Pwd",method = RequestMethod.PUT)
	@ResponseBody
	public Object findPwd(User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(2000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);

		User pwdUser = userService.checPassword(user);

		if(pwdUser==null){
			returnJson.setErrorCode(2001);
			returnJson.setReturnMessage("密码不匹配，请重新输入密码" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		pwdUser.setNewPassword(user.getNewPassword());
		try {
			pwdUser.setPassword(pwdUser.getNewPassword());
			userService.update(pwdUser);
			String username = user.getMobile();
			ObjectNode json2 = JsonNodeFactory.instance.objectNode();
			json2.put("newpassword",user.getNewPassword());
			ObjectNode modifyIMUserPasswordWithAdminTokenNode =	EasemobIMUsers.modifyIMUserPasswordWithAdminToken(username,json2);
			if (null != modifyIMUserPasswordWithAdminTokenNode) {
				LOGGER.info("重置IM用户密码 提供管理员token: " + modifyIMUserPasswordWithAdminTokenNode.toString());
			}
		}catch (Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(1001);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
		}
		returnJson.setReturnValue(pwdUser.getToken());
		return returnJson;
	}


	@RequestMapping(value="/User/Temp",method = RequestMethod.GET)
	@ResponseBody
	public Object loginUser(User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(5000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);
		User pwdUser = null;
		ValidCode code = new ValidCode();
		code.setValidCode(user.getValidCode());
		code.setMobile(user.getMobile());
//		int flag =   validCodeService.checkValidCode(code);
//		if(flag==1){
//			returnJson.setErrorCode(5003);
//			returnJson.setReturnMessage("验证码错误，请重新输入" + code.toString());
//			returnJson.setServerStatus(1);
//		}
		try {
			pwdUser = userService.checkPasswordHuanxin(user);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(5002);
			returnJson.setReturnMessage("服务器异常" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		if(pwdUser==null){
			returnJson.setErrorCode(5001);
			returnJson.setReturnMessage("密码不匹配，请重新输入密码" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		returnJson.setReturnObject(pwdUser);
		returnJson.setReturnValue(pwdUser.getToken());

		return returnJson;
	}


	@RequestMapping(value="/User",method = RequestMethod.GET)
	@ResponseBody
	public Object loginUserName(User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(5000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);
		User pwdUser = null;
		ValidCode code = new ValidCode();
		code.setValidCode(user.getValidCode());
		code.setMobile(user.getMobile());
		if(StringUtils.isEmpty(user.getPassword())){

			returnJson.setErrorCode(46002);
			returnJson.setReturnMessage("传入参数为空" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
            String password = user.getPassword();
			user.setAccountName(user.getIdentifier());
			pwdUser = userService.checkAccountName(user);
			if(pwdUser==null){
				user.setMobile(user.getIdentifier());
                user.setPassword(password);
				pwdUser = userService.checkMobile(user);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(5002);
			returnJson.setReturnMessage("服务器异常" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;
		}
		if(pwdUser==null){
			returnJson.setErrorCode(5001);
			returnJson.setReturnMessage("密码不匹配，请重新输入密码" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		returnJson.setReturnObject(pwdUser);
		returnJson.setReturnValue(pwdUser.getToken());

		return returnJson;
	}







	@RequestMapping(value="/Security/Pwd",method = RequestMethod.POST)
	@ResponseBody
	public Object updateUserPwd(@RequestBody User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(6000);
		returnJson.setReturnMessage("调用成功" + user.toString());
		returnJson.setServerStatus(0);
		User result = null;
		try {
			result =  userService.selectByToken(user.getToken());
		}catch(Exception ex){
			returnJson.setErrorCode(6001);
			returnJson.setReturnMessage("服务器错误,请重试" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;

		}
		if(result==null){
			returnJson.setErrorCode(6002);
			returnJson.setReturnMessage("token不存在" + user.toString());
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			result.setPassword(user.getPassword());
			userService.update(result);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(6001);
			returnJson.setReturnMessage("服务器错误,请重试" + user.toString());
			returnJson.setServerStatus(2);
			return returnJson;

		}
		returnJson.setReturnValue(result.getToken());
		return returnJson;
	}



	@RequestMapping(value="/Mobile/Exist",method = RequestMethod.GET)
	@ResponseBody
	public Object checkMobile(User user) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(54000);
		returnJson.setReturnMessage("调用成功");
		returnJson.setServerStatus(0);
		User pwdUser = null;
		if(StringUtils.isEmpty(user.getMobile())){
			returnJson.setErrorCode(53001);
			returnJson.setReturnMessage("传入参数为空");
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			pwdUser = userService.isMobileExist(user);
			if(pwdUser!=null){
				returnJson.setErrorCode(53003);
				returnJson.setReturnMessage("手机号已存在");
				returnJson.setServerStatus(2);
				return returnJson;
			}else{
				returnJson.setErrorCode(53004);
				returnJson.setReturnMessage("手机号不存在");
				returnJson.setServerStatus(2);
				return returnJson;
			}
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(53002);
			returnJson.setReturnMessage("服务器异常");
			returnJson.setServerStatus(2);
			return returnJson;
		}
	}



	@RequestMapping(value="/User/NickName",method = RequestMethod.GET)
	@ResponseBody
	public Object getNickName(String huanxinUid) {
		ReturnJson returnJson = new ReturnJson();
		returnJson.setErrorCode(53000);
		returnJson.setReturnMessage("调用成功" +huanxinUid);
		returnJson.setServerStatus(0);
		User pwdUser = null;
		if(StringUtils.isEmpty(huanxinUid)){
			returnJson.setErrorCode(53001);
			returnJson.setReturnMessage("传入参数为空" +huanxinUid);
			returnJson.setServerStatus(1);
			return returnJson;
		}
		try {
			pwdUser = userService.selecthxUid(huanxinUid);
		}catch(Exception ex){
			ex.printStackTrace();
			returnJson.setErrorCode(53002);
			returnJson.setReturnMessage("服务器异常" + huanxinUid);
			returnJson.setServerStatus(2);
			return returnJson;
		}
		if(pwdUser==null){
			returnJson.setErrorCode(53001);
			returnJson.setReturnMessage("该环信ID用户不存在" + huanxinUid);
			returnJson.setServerStatus(1);
			return returnJson;
		}
		returnJson.setReturnObject(pwdUser);
		returnJson.setReturnValue(pwdUser.getNickName());

		return returnJson;
	}
}
