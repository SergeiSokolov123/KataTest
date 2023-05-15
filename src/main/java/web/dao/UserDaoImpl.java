package web.dao;

import org.springframework.stereotype.Repository;
import web.model.Users;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Users> getAllUsers() {
        return entityManager.createQuery("from Users", Users.class).getResultList();
    }

    @Override
    public Users getById(int id) {
        return entityManager.find(Users.class, id);
    }

    @Override
    public void addUser(Users user) {
        entityManager.persist(user);
    }

    @Override
    public void update(Users user) {
        entityManager.merge(user);
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(Users.class, id));
    }

}
