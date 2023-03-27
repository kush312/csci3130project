package ca.dal.Maverick.board.repository;

import ca.dal.Maverick.board.model.BoardModel;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardModel, String> {

  List<BoardModel> findByWorkspaceID(String workspaceID);
}
