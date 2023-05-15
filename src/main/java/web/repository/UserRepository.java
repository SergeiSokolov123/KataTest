package web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import web.model.Users;
@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {

}
