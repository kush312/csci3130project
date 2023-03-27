package ca.dal.Maverick.workspace.repository;

import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.workspace.model.WorkSpaceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkSpaceRepository extends JpaRepository<WorkSpaceModel, String> {

  boolean existsByName(String name);
  List<WorkSpaceModel> findAllByUsers(UserModel userModel);
}
