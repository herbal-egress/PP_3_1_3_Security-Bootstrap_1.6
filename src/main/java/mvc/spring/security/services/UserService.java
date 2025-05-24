package mvc.spring.security.services;

import mvc.spring.security.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> findAllUser();

    User findUserByName(String username);

    User findUserById(int id);

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

    void register(User user);

    void update(int id, User userWithNewInfo);

    void delete(int id);
}