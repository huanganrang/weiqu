package rml.service;


import rml.model.House;
import rml.model.Role;
import rml.model.User;

import java.util.List;

public interface RoleServiceI {

    List<Role> findRoleByHouse(House house);
    List<Role> findRoleByUser(House house,User user);
}
