package rml.dao;

import rml.model.House;
import rml.model.Role;
import rml.model.User;

import java.util.List;

/**
 * Created by Jianghui on 2016/6/19.
 */
public interface RoleMapper {

    List<Role> findRoleByHouse(House house);
    List<Role> findRoleByUser(House house,User user);

    int insert(Role role);
    int update(Role role);

    /**
     * 维护角色与菜单权限关系
     * @param role
     * @return
     */
    public int deleteRoleResource(Role role);

    public int insertRoleResource(Role role);
}
