package rml.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.util.DigestUtils;
import rml.dao.UserMapper;
import rml.model.Lesson;
import rml.model.User;
import rml.service.UserServiceI;
import rml.util.MD5;
import rml.util.RandomGUID;

@Service("userService")
public class UserServiceImpl implements UserServiceI {


	private UserMapper userMapper;
		
	public UserMapper getMuserMapper() {
		return userMapper;
	}

	@Autowired
	public void setMuserMapper(UserMapper muserMapper) {
		this.userMapper = muserMapper;
	}

	@Override
	public User isMobileExist(User user) {
		return userMapper.isMobileExist(user);
	}

	@Override
	public List<User> getAll() {
		
		return userMapper.getAll();
	}

	@Override
	public int insert(User muser) {

		muser.setToken(UUID.randomUUID().toString());
		muser.setPassword(MD5.GetMD5Code(muser.getPassword()));
		muser.setHuanxinUid(UUID.randomUUID().toString().replaceAll("-",""));
		return userMapper.insert(muser);
	}

	@Override
	public int update(User muser) {
		RandomGUID guid = new RandomGUID(true);
		muser.setToken(guid.valueAfterMD5);
		return userMapper.updateByPrimaryKeySelective(muser);
	}

	@Override
	public int delete(String id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public User checPassword(User muser) {
		muser.setPassword(MD5.GetMD5Code(muser.getPassword()));
		return userMapper.checkPassword(muser);
	}

	@Override
	public User selectByToken(String token) {
		return userMapper.selectByToken(token);
	}

	@Override
	public int insertThird(User user)
	{
		user.setToken(UUID.randomUUID().toString());
		user.setType(1);
		user.setHuanxinUid(UUID.randomUUID().toString().replaceAll("-",""));
		user.setPassword(MD5.GetMD5Code(user.getUid()));
		return userMapper.insertThird(user);
	}

	@Override
	public int insertTemp(User user)
	{
		user.setNickName("游客"+UUID.randomUUID().toString().substring(0,8));
		user.setToken(UUID.randomUUID().toString());
		user.setType(2);
		user.setHuanxinUid(UUID.randomUUID().toString().replaceAll("-",""));
		user.setPassword(UUID.randomUUID().toString().substring(0,15).replaceAll("-",""));
		return userMapper.insertThird(user);
	}

	@Override
	public User checkPasswordHuanxin(User user) {
		return userMapper.checkPasswordHuanxin(user);
	}

	@Override
	public int updateProfile(User user) {
		return userMapper.updateProfile(user);
	}

	@Override
	public int updatePassword(User user) {
		user.setPassword(MD5.GetMD5Code(user.getPassword()));
		return userMapper.updatePassword(user);
	}

	@Override
	public User getAccountName(String accountName) {
		return userMapper.getAccountName(accountName);
	}

	@Override
	public User checkAccountName(User user) {
		user.setPassword(MD5.GetMD5Code(user.getPassword()));
		return userMapper.checkAccountName(user);
	}

	@Override
	public User checkMobile(User user) {
		user.setPassword(MD5.GetMD5Code(user.getPassword()));
		return userMapper.checkMobile(user);
	}


	@Override
	public User selectByMobile(String mobile) {
		return userMapper.selectByMobile(mobile);
	}

	@Override
	public User selectUid(String uid) {
		return userMapper.selectUid(uid);
	}

	@Override
	public User selecthxUid(String huanxinUid) {
		return userMapper.selecthxUid(huanxinUid);
	}

	@Override
	public User selectByPrimaryKey(int id) {
		return userMapper.selectByPrimaryKey(id);
	}

	public static void main(String[]args){

		System.err.print(MD5.GetMD5Code("123456"));
	}

}
