package ca.dal.Maverick.board;

import ca.dal.Maverick.board.model.BoardModel;
import ca.dal.Maverick.board.model.BoardRequestModel;
import ca.dal.Maverick.board.repository.BoardRepository;
import ca.dal.Maverick.board.service.BoardService;
import ca.dal.Maverick.utils.UUIDUtils;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {BoardService.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardServiceTest {
  private final String name1 = "Test";
  private final String name2 = "Test2";
  private final String description1 = "Test description";
  private final String description2 = "Updated description";
  private final String testID = "dummy_id";
  private final String workspaceID = "workspace_id";
  private final BoardRequestModel requestModel = new BoardRequestModel(name1, description1, workspaceID);

  @Autowired
  private BoardService boardService;

  @MockBean
  private BoardRepository boardRepository;
  private MockedStatic<UUIDUtils> uuidUtils;

  @BeforeAll
  void setUpForTestSuite() {
    uuidUtils = Mockito.mockStatic(UUIDUtils.class);
    uuidUtils.when(UUIDUtils::generateUUID).thenReturn(testID);
  }

  @AfterAll
  void tearDownTestSuite() {
    uuidUtils.close();
  }

  @Test
  public void testCreateBoard() {
    BoardModel expected = new BoardModel(name1, description1, workspaceID);

    when(boardRepository.save(any())).thenReturn(expected);

    assertEquals(expected, boardService.createBoard(requestModel));
  }

  @Test
  public void testGetBoard() {
    BoardModel expected = new BoardModel(name1, description1, workspaceID);
    Optional<BoardModel> optionalBoardModel = Optional.of(expected);

    when(boardRepository.findById(testID)).thenReturn(optionalBoardModel);

    assertEquals(expected, boardService.getBoard(testID));
  }

  @Test
  public void testDeleteBoard() {
    BoardModel expected = new BoardModel(name1, description1, workspaceID);

    Optional<BoardModel> optionalBoardModel = Optional.of(expected);
    when(boardRepository.findById(testID)).thenReturn(optionalBoardModel);

    assertEquals(testID, boardService.deleteBoard(testID));
  }

  @Test
  public void testUpdateBothNameAndDescriptionBoard() {
    BoardRequestModel updateRequest = new BoardRequestModel(name2, description2, workspaceID);
    BoardModel oldModel = new BoardModel(name1, description1, workspaceID);

    Optional<BoardModel> optionalBoardModel = Optional.of(oldModel);
    when(boardRepository.findById(testID)).thenReturn(optionalBoardModel);

    BoardModel updatedBoard = boardService.updateBoard(updateRequest, testID);

    String newName = updatedBoard.getBoard_name();
    String newDescription = updatedBoard.getDescription();

    assertEquals(name2, newName);
    assertEquals(description2, newDescription);
  }

  @Test
  public void testUpdateNameBoard() {
    BoardRequestModel updateRequest = new BoardRequestModel();
    updateRequest.setBoard_name(name2);
    BoardModel oldModel = new BoardModel(name1, description1, workspaceID);

    Optional<BoardModel> optionalBoardModel = Optional.of(oldModel);
    when(boardRepository.findById(testID)).thenReturn(optionalBoardModel);

    BoardModel updatedBoard = boardService.updateBoard(updateRequest, testID);

    String newName = updatedBoard.getBoard_name();
    String newDescription = updatedBoard.getDescription();

    assertEquals(name2, newName);
    assertEquals(description1, newDescription);
  }

  @Test
  public void testUpdateDescriptionBoard() {
    BoardRequestModel updateRequest = new BoardRequestModel();
    updateRequest.setDescription(description2);
    BoardModel oldModel = new BoardModel(name1, description1, workspaceID);

    Optional<BoardModel> optionalBoardModel = Optional.of(oldModel);
    when(boardRepository.findById(testID)).thenReturn(optionalBoardModel);

    BoardModel updatedBoard = boardService.updateBoard(updateRequest, testID);

    String newName = updatedBoard.getBoard_name();
    String newDescription = updatedBoard.getDescription();

    assertEquals(name1, newName);
    assertEquals(description2, newDescription);
  }

  @Test
  public void testGetBoardsForWorkspace() {
    BoardModel expected = new BoardModel(name1, description1, workspaceID);
    List<BoardModel> expectedAll = new ArrayList<>();
    expectedAll.add(expected);

    when(boardRepository.findByWorkspaceID(testID)).thenReturn(expectedAll);

    assertEquals(expectedAll, boardService.getBoardsForWorkspace(testID));
  }
}
