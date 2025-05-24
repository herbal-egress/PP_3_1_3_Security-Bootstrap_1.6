package mvc.spring.security.services;

import mvc.spring.security.entities.Role;

import java.util.List;

public interface RoleService {
    List<Role> findAllRole();
    Role findRoleByName(String name);
    void saveRole(String name);

}