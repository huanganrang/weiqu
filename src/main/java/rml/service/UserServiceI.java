package rml.service;

import java.util.List;

import rml.model.Lesson;
import rml.model.User;

public interface UserServiceI {

    User isMobileExist(User user);

    List<User> getAll();
	
	User selectByPrimaryKey(int id);

    int insert(User muser);

    int update(User muser);
    
    int delete(String id);

    User checPassword(User user);

    User selectByToken(String id);

    int insertThird(User user);

    User selectByMobile(String mobile);

    User selectUid(String uid);

    User selecthxUid(String huanxinUid);

    public int insertTemp(User user);

    User checkPasswordHuanxin(User user);

    int updateProfile(User user);

    int updatePassword(User user);

    User getAccountName(String accountName);

    User checkAccountName(User user);

    User checkMobile(User user);
}
