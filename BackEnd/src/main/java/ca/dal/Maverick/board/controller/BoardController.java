package ca.dal.Maverick.board.controller;

import ca.dal.Maverick.board.model.BoardModel;
import ca.dal.Maverick.board.model.BoardRequestModel;
import ca.dal.Maverick.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/board")
public class BoardController {

  @Autowired
  BoardService boardService;

  @PostMapping(path = "/create", consumes = "application/json")
  public BoardModel createBoard(@RequestBody BoardRequestModel newBoard) {
    return boardService.createBoard(newBoard);
  }

  @DeleteMapping("/delete/{id}")
  public String deleteBoard(@PathVariable String id) {
    return boardService.deleteBoard(id);
  }

  @PutMapping("/update/{id}")
  public BoardModel updateBoard(@RequestBody BoardRequestModel updatedBoard, @PathVariable String id) {
    return boardService.updateBoard(updatedBoard, id);
  }

  @GetMapping("/getBoard/{boardID}")
  public BoardModel getBoard(@PathVariable String boardID) {
    return boardService.getBoard(boardID);
  }

  @GetMapping("/get/{id}")
  public List<BoardModel> getBoardsForWorkspace(@PathVariable String id) {
    return boardService.getBoardsForWorkspace(id);
  }
}
