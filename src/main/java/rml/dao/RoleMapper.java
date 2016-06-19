package rml.dao;

import rml.model.Role;

/**
 * Created by Jianghui on 2016/6/19.
 */
public interface RoleMapper {

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
