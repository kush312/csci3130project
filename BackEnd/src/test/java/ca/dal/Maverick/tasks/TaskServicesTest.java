package ca.dal.Maverick.tasks;

import ca.dal.Maverick.board.model.BoardModel;
import ca.dal.Maverick.board.service.BoardService;
import ca.dal.Maverick.tasks.model.TaskModel;
import ca.dal.Maverick.tasks.repository.TasksRepository;
import ca.dal.Maverick.tasks.service.TasksService;
import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.workspace.model.WorkSpaceModel;
import ca.dal.Maverick.workspace.service.WorkSpaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.OffsetDateTime;
import java.util.*;

@SpringBootTest
@ContextConfiguration(classes = {TasksService.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskServicesTest {

    @Autowired
    public TasksService tasksService;

    @MockBean
    private TasksRepository tasksRepository;

    @MockBean
    private BoardService boardService;

    @MockBean
    private WorkSpaceService workSpaceService;

    @Test
    public void createTaskTestNull() {

        TaskModel model = new TaskModel();
        model.setTaskName("");

        TaskModel result = tasksService.createTask(model);

        assertNull(result, "create task is not returning null when passing a empty task model");
    }

    @Test
    public void createTaskTestReturn() {

        TaskModel model = new TaskModel();
        model.setTaskName("name");

        Mockito.when(tasksRepository.save(any())).thenReturn(model);

        TaskModel result = tasksService.createTask(model);

        assertNotNull(result, "create task is returning null TaskModel");
    }

    @Test
    public void getTaskTest() {

        Mockito.when(tasksRepository.findById(any())).thenReturn(Optional.of(new TaskModel()));

        TaskModel result = tasksService.getTask(1);

        assertNotNull(result, "getTask is returning null when valid id is passed");
    }

    @Test
    public void setDueDateTest() {

        Mockito.when(tasksRepository.findById(any())).thenReturn(Optional.of(new TaskModel()));
        Mockito.when(tasksRepository.save(any())).thenReturn(new TaskModel());

        OffsetDateTime time = OffsetDateTime.now();

        TaskModel result = tasksService.setDueDate(Long.valueOf("0"), time);

        assertEquals(time, result.getDueDate(), "Date is not being modified correctly");
    }
    @Test
    public void assignUsersTestNull() {

        UserModel[] usersToAdd = new UserModel[1];
        usersToAdd[0] = null;

        TaskModel result = tasksService.assignUsers(usersToAdd, Long.valueOf("0"));

        assertNull(result, "assignUser is not returning null if a user id is null");
    }

    @Test
    public void assignUsersTest() {

        UserModel user = new UserModel("name", "email", "password", "question", "answer");
        UserModel user2 = new UserModel("name2", "email2", "password2", "question2", "answer2");

        Set<UserModel> set = new HashSet<>();
        set.add(user);

        UserModel[] usersToAdd = new UserModel[1];
        usersToAdd[0] = user2;

        TaskModel model = new TaskModel();
        model.setTask_users(set);

        Mockito.when(tasksRepository.findById(any())).thenReturn(Optional.of(model));
        Mockito.when(tasksRepository.save(any())).thenReturn(model);

        TaskModel result = tasksService.assignUsers(usersToAdd, Long.valueOf("0"));

        assertEquals(set, result.getTask_users(), "Task user are not being added correctly");
    }

    @Test
    public void changeTaskStatusTest() {

        Mockito.when(tasksRepository.findById(any())).thenReturn(Optional.of(new TaskModel()));
        Mockito.when(tasksRepository.save(any())).thenReturn(new TaskModel());

        TaskModel result = tasksService.changeTaskStatus("Test", Long.valueOf("0"));

        assertEquals("Test", result.getStatus(), "Status is not being updated correctly");
    }

    @Test
    public void getTasksForBoardTest() {

        List<TaskModel> list = new ArrayList<>();
        list.add(new TaskModel());

        Mockito.when(tasksRepository.findAllByBoardID(any())).thenReturn(list);

        List<TaskModel> result = tasksService.getTasksForBoard("0");

        assertEquals(list, result, "getTasksForBoard is not returning correct list");
    }

    @Test
    public void searchByNameTest() {

        List<TaskModel> list = new ArrayList<>();
        list.add(new TaskModel());

        Mockito.when(tasksRepository.findAllByBoardIDAndTaskNameContaining(any(), any())).thenReturn(list);

        List<TaskModel> result = tasksService.searchByName("0", "name");

        assertEquals(list, result, "searchByName is not returning correct list");
    }

    @Test
    public void searchDueTodayTest() {

        List<TaskModel> list = new ArrayList<>();
        list.add(new TaskModel());

        Mockito.when(tasksRepository.findAllByBoardIDAndDueDateIsBetween(any(), any(), any())).thenReturn(list);

        List<TaskModel> result = tasksService.searchDueToday("0");

        assertEquals(list, result, "searchDueToday is not returning correct list");
    }

    @Test
    public void searchDueOverdueTest() {

        List<TaskModel> list = new ArrayList<>();
        list.add(new TaskModel());

        Mockito.when(tasksRepository.findAllByBoardIDAndDueDateBefore(any(), any())).thenReturn(list);

        List<TaskModel> result = tasksService.searchDueOverdue("0");

        assertEquals(list, result, "searchDueOverdue is not returning correct list");
    }

    @Test
    public void searchDueWeekTest() {

        List<TaskModel> list = new ArrayList<>();
        list.add(new TaskModel());

        Mockito.when(tasksRepository.findAllByBoardIDAndDueDateIsBetween(any(), any(), any())).thenReturn(list);

        List<TaskModel> result = tasksService.searchDueWeek("0");

        assertEquals(list, result, "searchDueWeek is not returning correct list");
    }

    @Test
    public void getTasksDueByTest() {

        List<TaskModel> list = new ArrayList<>();
        list.add(new TaskModel());

        Mockito.when(tasksRepository.findAllByDueDate(any())).thenReturn(list);

        List<TaskModel> result = tasksService.getTasksDueBy(OffsetDateTime.now());

        assertEquals(list, result, "getTasksDueBy is not returning correct list");
    }

    @Test
    public void numUsersWorkingOpenIssuesTest() {

        UserModel user = new UserModel("name", "email", "password", "question", "answer");
        UserModel user2 = new UserModel("name2", "email2", "password2", "question2", "answer2");

        Set<UserModel> set = new HashSet<>();
        set.add(user);
        set.add(user2);

        TaskModel model = new TaskModel();
        model.setStatus("Not Done!");
        model.setTask_users(set);

        List<TaskModel> list = new ArrayList<>();
        list.add(model);

        Mockito.when(tasksRepository.findAllByBoardID(any())).thenReturn(list);

        int result = tasksService.numUsersWorkingOpenTasksInBoard("Any");

        assertEquals(2, result, "numUsersWorkingOpenIssues is not returning correct number of users");
    }

    @Test
    public void numUsersWorkingOpenIssuesTestDone() {

        UserModel user = new UserModel("name", "email", "password", "question", "answer");
        UserModel user2 = new UserModel("name2", "email2", "password2", "question2", "answer2");

        Set<UserModel> set = new HashSet<>();
        set.add(user);
        set.add(user2);

        TaskModel model = new TaskModel();
        model.setStatus("Done");
        model.setTask_users(set);

        List<TaskModel> list = new ArrayList<>();
        list.add(model);

        Mockito.when(tasksRepository.findAllByBoardID(any())).thenReturn(list);

        int result = tasksService.numUsersWorkingOpenTasksInBoard("Any");

        assertEquals(0, result, "numUsersWorkingOpenIssues is not returning correct number of users");
    }


    @Test
    public void getAssignedUsersTestNull() {

        Optional<TaskModel> optionalTaskModel = Optional.empty();

        Mockito.when(tasksRepository.findById(any())).thenReturn(optionalTaskModel);

        Set<UserModel> result = tasksService.getAssignedUsers(Long.parseLong("0"));

        assertNull(result, "getAssignedUsers is not returning null for invalid task id");
    }

    @Test
    public void getAssignedUsersTest() {

        UserModel user = new UserModel("name", "email", "password", "question", "answer");
        UserModel user2 = new UserModel("name2", "email2", "password2", "question2", "answer2");

        Set<UserModel> set = new HashSet<>();
        set.add(user);
        set.add(user2);

        TaskModel model = new TaskModel();
        model.setTask_users(set);

        Mockito.when(tasksRepository.findById(any())).thenReturn(Optional.of(model));

        Set<UserModel> result = tasksService.getAssignedUsers(Long.parseLong("0"));

        assertEquals(set, result, "getAssignedUsers is not returning correct list");
    }

    @Test
    public void numUsersWorkingOpenTasksInWorkspaceTest() {

        UserModel user = new UserModel("name", "email", "password", "question", "answer");
        UserModel user2 = new UserModel("name2", "email2", "password2", "question2", "answer2");

        Set<UserModel> set = new HashSet<>();
        set.add(user);
        set.add(user2);

        TaskModel model = new TaskModel();
        model.setStatus("Not Done!");
        model.setTask_users(set);

        List<TaskModel> list = new ArrayList<>();
        list.add(model);

        Mockito.when(tasksRepository.findAllByBoardID(any())).thenReturn(list);

        List<BoardModel> boardList = new ArrayList<>();
        boardList.add(new BoardModel());

        Mockito.when(boardService.getBoardsForWorkspace(any())).thenReturn(boardList);

        int result = tasksService.numUsersWorkingOpenTasksInWorkspace("id");

        assertEquals(2, result, "numUsersWorkingOpenTasksInWorkspace is not returning correct user value");
    }

    @Test
    public void numUsersWorkingOpenTasksInWorkspacesTest() {

        UserModel user = new UserModel("name", "email", "password", "question", "answer");
        UserModel user2 = new UserModel("name2", "email2", "password2", "question2", "answer2");

        Set<UserModel> set = new HashSet<>();
        set.add(user);
        set.add(user2);

        TaskModel model = new TaskModel();
        model.setStatus("Not Done!");
        model.setTask_users(set);

        List<TaskModel> list = new ArrayList<>();
        list.add(model);

        Mockito.when(tasksRepository.findAllByBoardID(any())).thenReturn(list);

        List<BoardModel> boardList = new ArrayList<>();
        boardList.add(new BoardModel());

        Mockito.when(boardService.getBoardsForWorkspace(any())).thenReturn(boardList);

        List<WorkSpaceModel> workspaceList = new ArrayList<>();
        workspaceList.add(new WorkSpaceModel());

        Mockito.when(workSpaceService.getWorkspaceForUser(any())).thenReturn(workspaceList);

        int result = tasksService.numUsersWorkingOpenTasksInWorkspaces(anyLong());

        assertEquals(2, result, "numUsersWorkingOpenTasksInWorkspace is not returning correct value");
    }
}
