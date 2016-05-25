package rml.dao;

import java.util.List;

import rml.model.User;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(int id);

    User selectByToken(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User checkPassword(User user);

    User checkAccountName(User user);

    User checkMobile(User user);

    List<User> getAll();

    int insertThird(User user);

    User selectByMobile(String mobile);

   User  selectUid(String uid);

    User selecthxUid(String huanxinUid);

    User checkPasswordHuanxin(User user);

    int updateProfile(User user);

    int updatePassword(User user);

    User getAccountName(String accountName);

    User isMobileExist(User user);
}