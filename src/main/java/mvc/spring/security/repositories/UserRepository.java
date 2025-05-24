package mvc.spring.security.repositories;

import mvc.spring.security.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByName(String name);

    User findUserById(int id);

    void deleteUserById(int id);
}