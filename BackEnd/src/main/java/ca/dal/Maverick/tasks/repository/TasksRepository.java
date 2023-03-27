package ca.dal.Maverick.tasks.repository;

import ca.dal.Maverick.tasks.model.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<TaskModel, Long> {

  List<TaskModel> findAllByBoardID(String boardID);
  List<TaskModel> findAllByDueDate(OffsetDateTime dueDate);

  // Name like
  List<TaskModel> findAllByBoardIDAndTaskNameContaining(String boardID, String title);

  // Overdue
  List<TaskModel> findAllByBoardIDAndDueDateBefore(String boardID, OffsetDateTime dueDate);

  // Due Today and week
  List<TaskModel> findAllByBoardIDAndDueDateIsBetween(String boardID, OffsetDateTime startDate, OffsetDateTime endDate);
}
