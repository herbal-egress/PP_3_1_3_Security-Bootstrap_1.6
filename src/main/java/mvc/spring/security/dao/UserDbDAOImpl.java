package mvc.spring.security.dao;

import javax.persistence.*;

import mvc.spring.security.model.UserDb;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional(readOnly = true)
public class UserDbDAOImpl implements UserDbDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<UserDb> allUsers() {
        return entityManager.createQuery("SELECT u FROM UserDb u", UserDb.class).getResultList();
    }

    @Override
    public UserDb userById(int id) {
        TypedQuery<UserDb> tQuery = entityManager.createQuery("SELECT u FROM UserDb u where u.id = :id", UserDb.class);
        tQuery.setParameter("id", id);
        return tQuery.getResultList().stream().findAny().orElse(null);
    }

    @Override
    @Transactional
    public void save(UserDb userDb) {
        entityManager.persist(userDb);
    }

    @Override
    @Transactional
    public void update(int id, UserDb updatedUser) {
        UserDb userTeBeUpdated = entityManager.find(UserDb.class, id);
        userTeBeUpdated.setName(updatedUser.getName());
        userTeBeUpdated.setAge(updatedUser.getAge());
        userTeBeUpdated.setEmail(updatedUser.getEmail());
        entityManager.merge(userTeBeUpdated);
    }

    @Override
    @Transactional
    public void delete(int id) {
        UserDb userTeBeDeleted = entityManager.find(UserDb.class, id);
        entityManager.remove(userTeBeDeleted);
    }
}