package ca.dal.Maverick.tasks.controller;

import ca.dal.Maverick.tasks.model.TaskModel;
import ca.dal.Maverick.tasks.service.TasksService;
import ca.dal.Maverick.user.entity.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/task")
public class TasksController {

  @Autowired
  TasksService tasksService;

  @PostMapping(path="/create", consumes = "application/json", produces = "application/json")
  public TaskModel createTask(@RequestBody TaskModel task) {
    return tasksService.createTask(task);
  }

  @PutMapping(path="/setDueDate/{taskID}", produces = "application/json")
  public TaskModel setDueDate(@PathVariable Long taskID, @RequestBody OffsetDateTime date) {
   return tasksService.setDueDate(taskID, date);
  }

  @PutMapping(path = "/assignUsers/{taskID}", consumes = "application/json", produces = "application/json")
  public TaskModel assignTask(@RequestBody UserModel[] usersToAdd, @PathVariable Long taskID) {
    return tasksService.assignUsers(usersToAdd, taskID);
  }

  @PutMapping(path="/changeTaskStatus/{taskID}", produces="application/json")
  public TaskModel changeTaskStatus(@RequestBody String taskStatus, @PathVariable Long taskID) {
    return tasksService.changeTaskStatus(taskStatus, taskID);
  }

  @GetMapping(path="/searchByName/{boardID}/{name}")
  public List<TaskModel> searchByName(@PathVariable String boardID, @PathVariable String name) {
    return tasksService.searchByName(boardID, name);
  }

  @GetMapping(path="/sortDueToday/{boardID}")
  public List<TaskModel> sortDueToday(@PathVariable String boardID) {
    return tasksService.searchDueToday(boardID);
  }

  @GetMapping(path="/sortDueOverdue/{boardID}")
  public List<TaskModel> sortDueOverdue(@PathVariable String boardID) {
    return tasksService.searchDueOverdue(boardID);
  }

  @GetMapping(path="/sortDueWeek/{boardID}")
  public List<TaskModel> sortDueWeek(@PathVariable String boardID) {
    return tasksService.searchDueWeek(boardID);
  }

  @GetMapping("/get/{boardID}")
  public List<TaskModel> getTasksForBoard(@PathVariable String boardID) {
    return tasksService.getTasksForBoard(boardID);
  }

  @GetMapping("/getAssignedUsers/{taskID}")
  public Set<UserModel> getAssignedUsers(@PathVariable long taskID) {
    return tasksService.getAssignedUsers(taskID);
  }

  @GetMapping("/numUsersWorkingOpenTasksInBoard/{boardID}")
  public int numUsersWorkingOpenTasksInBoard(@PathVariable String boardID) {
    return tasksService.numUsersWorkingOpenTasksInBoard(boardID);
  }

  @GetMapping("/numUsersWorkingOpenTasksInWorkspaces/{userID}")
  public int numUsersWorkingOpenTasksInWorkspaces(@PathVariable Long userID) {
    return tasksService.numUsersWorkingOpenTasksInWorkspaces(userID);
  }

  @GetMapping("/numUsersWorkingOpenTasksInWorkspace/{workspaceID}")
  public int numUsersWorkingOpenTasksInWorkspace(@PathVariable String workspaceID) {
    return tasksService.numUsersWorkingOpenTasksInWorkspace(workspaceID);
  }
}
