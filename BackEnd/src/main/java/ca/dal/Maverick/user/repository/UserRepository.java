package ca.dal.Maverick.user.repository;

import ca.dal.Maverick.user.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmailID(String emailID);
}
