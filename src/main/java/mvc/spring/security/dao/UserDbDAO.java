package mvc.spring.security.dao;


import mvc.spring.security.model.UserDb;

import java.util.List;

public interface UserDbDAO {
    public List<UserDb> allUsers();

    public UserDb userById(int id);

    public void save(UserDb user);

    public void update(int id, UserDb updatedUser);

    public void delete(int id);
}