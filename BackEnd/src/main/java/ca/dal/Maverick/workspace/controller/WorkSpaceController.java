package ca.dal.Maverick.workspace.controller;

import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.workspace.model.WorkSpaceModel;
import ca.dal.Maverick.workspace.model.WorkSpaceRequestModel;
import ca.dal.Maverick.workspace.service.WorkSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/workspace")
public class WorkSpaceController {

  @Autowired
  private WorkSpaceService workSpaceService;

  @PostMapping(path = "/create", consumes = "application/json", produces = "application/json")
  public WorkSpaceModel createWorkSpace(@RequestBody WorkSpaceRequestModel workSpaceRequest) {
    return workSpaceService.createWorkSpace(workSpaceRequest);
  }

  @GetMapping("/{id}")
  public WorkSpaceModel getWorkSpace(@PathVariable String id) {
    return workSpaceService.getWorkSpace(id);
  }

  @PutMapping(path = "/update/{id}", consumes = "application/json", produces = "application/json")
  public WorkSpaceModel updateWorkSpace(@RequestBody WorkSpaceRequestModel updateWorkSpaceRequest, @PathVariable String id) {
    return workSpaceService.updateWorkSpace(updateWorkSpaceRequest, id);
  }

  @PutMapping(path = "/addUsers/{id}", consumes = "application/json", produces = "application/json")
  public List<UserModel> addUsers(@RequestBody UserModel[] usersToAdd, @PathVariable String id) {
    return workSpaceService.addUsers(usersToAdd, id);
  }

  @DeleteMapping(path = "/delete/{id}")
  public String deleteWorkSpace(@PathVariable String id) {
    return workSpaceService.deleteWorkSpace(id);
  }

  @GetMapping(path = "/getAll", produces = "application/json")
  public List<WorkSpaceModel> getAllWorkspaces() {
    return workSpaceService.getAll();
  }

  @GetMapping(path = "/get/{id}", produces = "application/json")
  public List<WorkSpaceModel> getWorkspacesForUser(@PathVariable Long id) {
    return workSpaceService.getWorkspaceForUser(id);
  }

  @GetMapping("/getusers/{id}")
  public Set<UserModel>getUsers(@PathVariable String id){
    return workSpaceService.getUsersList(id);
  }


}
