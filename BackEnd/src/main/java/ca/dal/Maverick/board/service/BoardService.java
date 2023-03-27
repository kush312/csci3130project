package ca.dal.Maverick.board.service;

import ca.dal.Maverick.board.model.BoardModel;
import ca.dal.Maverick.board.model.BoardRequestModel;
import ca.dal.Maverick.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BoardService {

  @Autowired
  BoardRepository boardRepository;

  public BoardModel createBoard(BoardRequestModel newBoard) {
    if (
        newBoard.getBoard_name().isEmpty()
    ) {
      return null;
    }

    return boardRepository.save(BoardModel.create(newBoard));
  }

  public BoardModel getBoard(String id) {
    Optional<BoardModel> optionalBoardModel = boardRepository.findById(id);

    return optionalBoardModel.orElse(null);
  }

  public String deleteBoard(String id) {
    BoardModel boardModel = getBoard(id);

    if (boardModel != null) {
      boardRepository.delete(boardModel);
    } else {
      return null;
    }



    return boardModel.getId();
  }

  public BoardModel updateBoard(BoardRequestModel newBoard, String id) {
    BoardModel oldBoard = getBoard(id);

    if (newBoard.getBoard_name() != null) {
      oldBoard.setBoard_name(newBoard.getBoard_name());
      oldBoard.setLastUpdated(OffsetDateTime.now());
    }

    if (newBoard.getDescription() != null) {
      oldBoard.setDescription(newBoard.getDescription());
      oldBoard.setLastUpdated(OffsetDateTime.now());
    }

    boardRepository.save(oldBoard);

    return oldBoard;
  }

  public List<BoardModel> getBoardsForWorkspace(String workspaceID) {
    return boardRepository.findByWorkspaceID(workspaceID);
  }
}
