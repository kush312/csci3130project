package ca.dal.Maverick.user.controller;

import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.user.entity.UserQuestionRequestModel;
import ca.dal.Maverick.user.entity.UserRequestModel;
import ca.dal.Maverick.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.Collections;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping("/save")
    public UserModel signupUser(@RequestBody UserModel userModel) {
        return userService.signupUser(userModel);
    }

    @CrossOrigin
    @PostMapping("/authentication")
    public Map<String, String> authenticate(@RequestBody UserRequestModel user) {
        return Collections.singletonMap("token", userService.authenticate(user));
    }

    @CrossOrigin
    @PostMapping("/forgotpassword")
    public UserModel forgotPassword(@RequestBody UserQuestionRequestModel userModel) {
        return userService.getQuestion(userModel);
    }

    @CrossOrigin
    @PostMapping("/checkanswer")
    public UserModel checkSecurityAnswer(@RequestBody UserQuestionRequestModel userModel){
        return userService.getAnswer(userModel);
    }

    @CrossOrigin
    @GetMapping("/getAll")
    public List<UserModel> getAllUsers() {
        return userService.getAllUsers();
    }

    @CrossOrigin
    @GetMapping("/getSecurityQuestion")
    public UserModel getQuestions(){
        return userService.securityQuestion();
    }

    @CrossOrigin
    @GetMapping("/getuserdetails")
    public UserModel getUserDetails(){
        return userService.userDetails();
    }

    @CrossOrigin
    @PutMapping(path = "/updatepassword/{id}", consumes = "application/json", produces = "application/json")
    public UserModel updatePassword(@RequestBody UserRequestModel userRequestModelModel, @PathVariable Long id){
        return userService.updatePassword(userRequestModelModel, id);
    }
}





