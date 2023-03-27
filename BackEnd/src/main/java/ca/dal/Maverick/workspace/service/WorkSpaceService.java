package ca.dal.Maverick.workspace.service;

import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.user.service.UserService;
import ca.dal.Maverick.workspace.model.WorkSpaceModel;
import ca.dal.Maverick.workspace.model.WorkSpaceRequestModel;
import ca.dal.Maverick.workspace.repository.WorkSpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Service
public class WorkSpaceService {

  @Autowired
  WorkSpaceRepository workSpaceRepository;

  @Autowired
  UserService userService;


  public WorkSpaceModel createWorkSpace(WorkSpaceRequestModel model) {
    UserModel user = userService.getUser(model.getUserID());
    WorkSpaceModel newWorkspace = WorkSpaceModel.create(model, user);
    return workSpaceRepository.save(newWorkspace);
  }

  public WorkSpaceModel updateWorkSpace(WorkSpaceRequestModel updateModel, String workspaceID) {

    WorkSpaceModel workSpaceModel;
    Optional<WorkSpaceModel> optionalWorkSpaceModel = workSpaceRepository.findById(workspaceID);

    if (optionalWorkSpaceModel.isPresent()) {
      workSpaceModel = optionalWorkSpaceModel.get();
    } else {
      return null;
    }

    if (updateModel.getName() != null) {
      workSpaceModel.setName(updateModel.getName());
      workSpaceModel.setLastUpdated(OffsetDateTime.now());
    }

    if (updateModel.getDescription() != null) {
      workSpaceModel.setDescription(updateModel.getDescription());
      workSpaceModel.setLastUpdated(OffsetDateTime.now());
    }

    workSpaceRepository.save(workSpaceModel);

    return workSpaceModel;
  }

  public String deleteWorkSpace(String id) {

    WorkSpaceModel workSpaceModel;
    Optional<WorkSpaceModel> optionalWorkSpaceModel = workSpaceRepository.findById(id);

    if (optionalWorkSpaceModel.isPresent()) {
      workSpaceModel = optionalWorkSpaceModel.get();
    } else {
      return null;
    }

    workSpaceRepository.delete(workSpaceModel);

    return workSpaceModel.getId();
  }

  public WorkSpaceModel getWorkSpace(String id) {
    Optional<WorkSpaceModel> optionalWorkSpaceModel = workSpaceRepository.findById(id);

    return optionalWorkSpaceModel.orElse(null);
  }

  public List<WorkSpaceModel> getAll() {
    return workSpaceRepository.findAll();
  }

  public List<WorkSpaceModel> getWorkspaceForUser(Long userID) {
    UserModel user = userService.getUser(userID);

    return workSpaceRepository.findAllByUsers(user);
  }

  public List<UserModel> addUsers(UserModel[] usersToAdd, String id) {
    Optional<WorkSpaceModel> optionalWorkSpaceModel = workSpaceRepository.findById(id);
    WorkSpaceModel workSpaceModel;

    if (optionalWorkSpaceModel.isPresent()) {
      workSpaceModel = optionalWorkSpaceModel.get();
    } else {
      return null;
    }

    ArrayList<UserModel> addedUsers = new ArrayList<>();

    for (UserModel user : usersToAdd) {
      if (user == null) {
        return null;
      }
      addedUsers.add(user);
    }

    Set<UserModel> currentUsers = workSpaceModel.getUsers();
    currentUsers.addAll(addedUsers);

    workSpaceRepository.save(workSpaceModel);

    return addedUsers;
  }


  public Set<UserModel> getUsersList(String id) {
    Optional<WorkSpaceModel> optionalWorkSpaceModel = workSpaceRepository.findById(id);
    WorkSpaceModel workSpaceModel;
    if (optionalWorkSpaceModel.isPresent()) {
      workSpaceModel = optionalWorkSpaceModel.get();
    } else {
      return null;
    }
    return workSpaceModel.getUsers();
  }

}
