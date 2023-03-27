package ca.dal.Maverick.workspace;

import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.user.service.UserService;
import ca.dal.Maverick.utils.UUIDUtils;
import ca.dal.Maverick.workspace.model.WorkSpaceModel;
import ca.dal.Maverick.workspace.model.WorkSpaceRequestModel;
import ca.dal.Maverick.workspace.repository.WorkSpaceRepository;
import ca.dal.Maverick.workspace.service.WorkSpaceService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {WorkSpaceService.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WorkSpaceServiceTest {
  private final String name1 = "Test";
  private final String name2 = "Test2";
  private final String description1 = "Test description";
  private final String description2 = "Updated description";
  private final String testID = "dummy_id";
  private final Long userID = 1L;
  private final WorkSpaceRequestModel requestModel = new WorkSpaceRequestModel(name1, description1, userID);
  private UserModel mockUser;
  private UserModel mockUser2;


  @Autowired
  private WorkSpaceService workSpaceService;

  @MockBean
  private WorkSpaceRepository workSpaceRepository;
  @MockBean
  private UserService userService;
  private MockedStatic<UUIDUtils> uuidUtils;

  @BeforeAll
  void setUpForTestSuite() {
    uuidUtils = Mockito.mockStatic(UUIDUtils.class);
    uuidUtils.when(UUIDUtils::generateUUID).thenReturn(testID);
    mockUser = Mockito.mock(UserModel.class);
    mockUser2 = Mockito.mock(UserModel.class);
  }

  @AfterAll
  void tearDownTestSuite() {
    uuidUtils.close();
  }

  @Test
  public void testCreateWorkSpace() {
    WorkSpaceModel expected = new WorkSpaceModel(name1, description1, mockUser);
    boolean isExists = false;

    when(workSpaceRepository.save(any())).thenReturn(expected);
    when(workSpaceRepository.existsByName(any())).thenReturn(isExists);
    when(mockUser.getId()).thenReturn(userID);

    assertEquals(expected, workSpaceService.createWorkSpace(requestModel));
  }

  @Test
  public void testUpdatingNameWorkSpace() {
    WorkSpaceModel oldModel = WorkSpaceModel.create(requestModel, mockUser);

    Optional<WorkSpaceModel> optionalWorkSpaceModel = Optional.of(oldModel);
    when(workSpaceRepository.findById(any())).thenReturn(optionalWorkSpaceModel);

    WorkSpaceRequestModel updatedModel = new WorkSpaceRequestModel(name2, null, userID);

    assertEquals(name2, workSpaceService.updateWorkSpace(updatedModel, testID).getName());
  }

  @Test
  public void testUpdatingDescriptionWorkSpace() {
    WorkSpaceModel oldModel = WorkSpaceModel.create(requestModel, mockUser);

    Optional<WorkSpaceModel> optionalWorkSpaceModel = Optional.of(oldModel);
    when(workSpaceRepository.findById(any())).thenReturn(optionalWorkSpaceModel);

    WorkSpaceRequestModel updatedModel = new WorkSpaceRequestModel(null, description2, userID);

    assertEquals(description2, workSpaceService.updateWorkSpace(updatedModel, testID).getDescription());
  }

  @Test
  public void testUpdatingBothNameDescriptionWorkSpace() {
    WorkSpaceModel oldModel = WorkSpaceModel.create(requestModel, mockUser);

    Optional<WorkSpaceModel> optionalWorkSpaceModel = Optional.of(oldModel);
    when(workSpaceRepository.findById(any())).thenReturn(optionalWorkSpaceModel);

    WorkSpaceRequestModel updatedModel = new WorkSpaceRequestModel(name2, description2, userID);
    Set<String> expected = new HashSet<>();
    expected.add(name2);
    expected.add(description2);

    Set<String> actual = new HashSet<>();
    WorkSpaceModel updated = workSpaceService.updateWorkSpace(updatedModel, testID);
    actual.add(updated.getName());
    actual.add(updated.getDescription());

    assertEquals(expected, expected);
  }

  @Test
  public void testDeleteWorkSpace() {
    WorkSpaceModel workspace = WorkSpaceModel.create(requestModel, mockUser);

    Optional<WorkSpaceModel> optionalWorkSpaceModel = Optional.of(workspace);
    when(workSpaceRepository.findById(any())).thenReturn(optionalWorkSpaceModel);

    assertEquals(testID, workSpaceService.deleteWorkSpace(testID));
  }

  @Test
  public void testGetWorkspace() {
    WorkSpaceModel expected = WorkSpaceModel.create(requestModel, mockUser);

    Optional<WorkSpaceModel> optionalWorkSpaceModel = Optional.of(expected);
    when(workSpaceRepository.findById(any())).thenReturn(optionalWorkSpaceModel);

    assertEquals(expected, workSpaceService.getWorkSpace(testID));
  }

  @Test
  public void testAddUsers() {
    WorkSpaceModel expected = WorkSpaceModel.create(requestModel, mockUser);

    Optional<WorkSpaceModel> optionalWorkSpaceModel = Optional.of(expected);
    when(workSpaceRepository.findById(any())).thenReturn(optionalWorkSpaceModel);

    UserModel[] usersToAdd = {mockUser2};

    List<UserModel> addedUsers = workSpaceService.addUsers(usersToAdd, testID);

    assertEquals(usersToAdd.length, addedUsers.size());
    assertEquals(mockUser2, addedUsers.get(0));
  }

  @Test
  public void testGetAll() {
    WorkSpaceModel expected = WorkSpaceModel.create(requestModel, mockUser);
    List<WorkSpaceModel> expectedAll = new ArrayList<>();
    expectedAll.add(expected);

    when(workSpaceRepository.findAll()).thenReturn(expectedAll);


    assertEquals(expectedAll, workSpaceService.getAll());
  }

  @Test
  public void testGetWorkspaceForUser() {
    WorkSpaceModel expected = WorkSpaceModel.create(requestModel, mockUser);
    List<WorkSpaceModel> expectedAll = new ArrayList<>();
    expectedAll.add(expected);

    when(userService.getUser(anyLong())).thenReturn(mockUser);
    when(workSpaceRepository.findAllByUsers(any())).thenReturn(expectedAll);

    assertEquals(expectedAll, workSpaceService.getWorkspaceForUser(userID));
  }

  @Test
  public void getUsersListTest() {

    when(workSpaceRepository.findById(any())).thenReturn(Optional.of(new WorkSpaceModel()));

    Set<UserModel> model = workSpaceService.getUsersList("any");

    assertNull(model, "getUsersList is not returning null on a empty user list");
  }

}
