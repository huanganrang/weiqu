package rml.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rml.dao.RoleMapper;
import rml.model.House;
import rml.model.Role;
import rml.model.User;
import rml.service.RoleServiceI;

import java.util.List;

@Service("roleService")
public class RoleServiceImpl implements RoleServiceI {
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRoleByHouse(House house) {
        return roleMapper.findRoleByHouse(house);
    }

    @Override
    public List<Role> findRoleByUser(House house, User user) {
        return roleMapper.findRoleByUser(house,user);
    }

}
