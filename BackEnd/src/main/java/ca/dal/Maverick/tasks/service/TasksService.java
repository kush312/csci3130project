package ca.dal.Maverick.tasks.service;

import ca.dal.Maverick.board.model.BoardModel;
import ca.dal.Maverick.board.service.BoardService;
import ca.dal.Maverick.tasks.model.TaskModel;
import ca.dal.Maverick.tasks.repository.TasksRepository;
import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.workspace.model.WorkSpaceModel;
import ca.dal.Maverick.workspace.service.WorkSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.*;

@Service
public class TasksService {

  @Autowired
  TasksRepository tasksRepository;

  @Autowired
  BoardService boardService;

  @Autowired
  WorkSpaceService workSpaceService;

  static final long NUM_OF_DAYS = 7;
  static final long Due_Day = 1;

  public TaskModel createTask(TaskModel task) {
    if (task.getTaskName().isEmpty()) {
      return null;
    }
    
    return tasksRepository.save(task);
  }

  public TaskModel getTask(long taskID) {
    Optional<TaskModel> optionalTask = tasksRepository.findById(taskID);

    return optionalTask.orElse(null);
  }

  public TaskModel setDueDate(Long taskID, OffsetDateTime date) {
    TaskModel task = getTask(taskID);

    task.setDueDate(date);

    tasksRepository.save(task);

    return task;
  }


  public TaskModel assignUsers(UserModel[] usersToAdd, Long taskID) {
    TaskModel task = getTask(taskID);

    ArrayList<UserModel> addedUsers = new ArrayList<>();

    for (UserModel user : usersToAdd) {
      if (user == null) {
        return null;
      }
      addedUsers.add(user);
    }

    Set<UserModel> assignedUsers = task.getTask_users();
    assignedUsers.addAll(addedUsers);

    tasksRepository.save(task);

    return task;
  }

  public TaskModel changeTaskStatus(String status, Long taskID) {
    TaskModel task = getTask(taskID);

    task.setStatus(status);

    tasksRepository.save(task);
    return task;
  }

  public List<TaskModel> getTasksForBoard(String boardID) {
    return tasksRepository.findAllByBoardID(boardID);
  }

  public List<TaskModel> searchByName(String boardID, String name) {
    return tasksRepository.findAllByBoardIDAndTaskNameContaining(boardID, name);
  }

  public List<TaskModel> searchDueToday(String boardID) {

    OffsetDateTime date = OffsetDateTime.of(LocalDate.now(), LocalTime.MIN, OffsetDateTime.now().getOffset());

    return tasksRepository.findAllByBoardIDAndDueDateIsBetween(boardID, date, date.plusDays(Due_Day));
  }

  public List<TaskModel> searchDueOverdue(String boardID) {
    OffsetDateTime date = OffsetDateTime.of(LocalDate.now(), LocalTime.MIN, OffsetDateTime.now().getOffset());

    return tasksRepository.findAllByBoardIDAndDueDateBefore(boardID, date);
  }

  public List<TaskModel> searchDueWeek(String boardID) {

    OffsetDateTime date = OffsetDateTime.of(LocalDate.now(), LocalTime.MIN, OffsetDateTime.now().getOffset());

    return tasksRepository.findAllByBoardIDAndDueDateIsBetween(boardID, date, date.plusDays(NUM_OF_DAYS));
  }

  public List<TaskModel> getTasksDueBy(OffsetDateTime date) {
    return tasksRepository.findAllByDueDate(date);
  }

  public int numUsersWorkingOpenTasksInBoard(String boardID) {
    List<TaskModel> openTasks = tasksRepository.findAllByBoardID(boardID);
    int userCount = 0;

    for (TaskModel task : openTasks) {
      if (task.getStatus().equals("Done")) {
        continue;
      }

      userCount += task.getTask_users().size();
    }

    return userCount;
  }


  public int numUsersWorkingOpenTasksInWorkspace(String workspaceID) {
    int userCount = 0;

    List<BoardModel> boardInWorkspace = boardService.getBoardsForWorkspace(workspaceID);

    for (BoardModel board : boardInWorkspace) {
      userCount += numUsersWorkingOpenTasksInBoard(board.getId());
    }

    return userCount;
  }

  public int numUsersWorkingOpenTasksInWorkspaces(long userID) {
    int userCount = 0;

    List<WorkSpaceModel> workspaceForUser = workSpaceService.getWorkspaceForUser(userID);

    for (WorkSpaceModel workspace : workspaceForUser) {
      userCount += numUsersWorkingOpenTasksInWorkspace(workspace.getId());
    }

    return userCount;
  }

  public Set<UserModel> getAssignedUsers(long taskID) {
    TaskModel task = getTask(taskID);

    if (task != null) {
      return task.getTask_users();
    } else {
      return null;
    }
  }
}
