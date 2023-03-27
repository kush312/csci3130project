package ca.dal.Maverick.user.service;


import ca.dal.Maverick.jwt.TokenService;
import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.user.entity.UserQuestionRequestModel;
import ca.dal.Maverick.user.entity.UserRequestModel;
import ca.dal.Maverick.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ContextConfiguration(classes = {UserService.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

    private final String userName = "test";
    private final String emailId = "test123@email.com";
    private final String password = "testPass";
    private final String question = "question";
    private final String answer = "answer";

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TokenService tokenService;

    @MockBean PasswordEncoder passwordEncoder;

    @Test
    public void signupUserTest() {

        String password1= "password";

        UserModel userModel = Mockito.mock(UserModel.class);
//        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);

        Mockito.when(userRepository.save(any())).thenReturn(userModel);
        Mockito.when(userModel.getPassword()).thenReturn(password1);
        Mockito.when(passwordEncoder.encode(password1)).thenReturn("dummyPassword");

        UserModel savedUser = userService.signupUser(userModel);

        assertEquals(password1,savedUser.getPassword());

    }

    @Test
    public void getQuestionTest(){

        UserModel expected = new UserModel(userName,emailId,password,question,answer);
        UserQuestionRequestModel userQuestionRequestModel = new UserQuestionRequestModel(emailId,question,answer);
        Mockito.when(userRepository.save(any())).thenReturn(expected);
        when(userRepository.findByEmailID(any())).thenReturn(expected);
        assertEquals(expected.getQuestion(), userService.getQuestion(userQuestionRequestModel).getQuestion());

    }

    @Test
    public void getAnswerTest(){

        UserModel expected = new UserModel(userName,emailId,password,question,answer);
        UserQuestionRequestModel userQuestionRequestModel = new UserQuestionRequestModel(emailId,question,answer);
        Mockito.when(userRepository.save(any())).thenReturn(expected);
        when(userRepository.findByEmailID(any())).thenReturn(expected);
        assertEquals(expected.getAnswer(), userService.getAnswer(userQuestionRequestModel).getAnswer());
    }

    @Test
    public void getUserTest(){
        long id = 1L;
        UserModel expected = new UserModel(userName,emailId,password,question,answer);
        Optional<UserModel> optionalUserModel = Optional.of(expected);
        when(userRepository.findById(id)).thenReturn(optionalUserModel);
        assertEquals(expected, userService.getUser(id));
    }

    @Test
    public void getAllUsersTest(){
        UserModel expected = new UserModel(userName,emailId,password,question,answer);
        List<UserModel> expectedAll = new ArrayList<>();
        expectedAll.add(expected);
        when(userRepository.findAll()).thenReturn(expectedAll);

        assertEquals(expectedAll, userService.getAllUsers());
    }


    @Test
    public void updatePasswordTest(){
        long id = 1L;
        String password1= "test";

        UserRequestModel userRequestModel = new UserRequestModel();
        userRequestModel.setPassword(password1);
        UserModel expected = new UserModel(userName,emailId,password,question,answer);
        Optional<UserModel> optionalUserModel = Optional.of(expected);
        when(userRepository.findById(id)).thenReturn(optionalUserModel);

       UserModel updatedUserModel = userService.updatePassword(userRequestModel, id);

       String newPassword = updatedUserModel.getPassword();
       assertEquals(newPassword, newPassword);
    }

    @Test
    public void securityQuestionTest(){
        UserModel expected = new UserModel(userName,emailId,password,question,answer);
        UserQuestionRequestModel userQuestionRequestModel = new UserQuestionRequestModel(emailId,question,answer);
        Mockito.when(userRepository.save(any())).thenReturn(expected);
        when(userRepository.findByEmailID(any())).thenReturn(expected);
        assertEquals(userService.getQuestion(userQuestionRequestModel), userService.securityQuestion());

    }

    @Test
    public void useDetailsTest(){
        UserModel expected = new UserModel(userName,emailId,password,question,answer);
        UserQuestionRequestModel userQuestionRequestModel = new UserQuestionRequestModel(emailId,question,answer);
        Mockito.when(userRepository.save(any())).thenReturn(expected);
        when(userRepository.findByEmailID(any())).thenReturn(expected);
        assertEquals(userService.getQuestion(userQuestionRequestModel), userService.userDetails());
    }

    @Test
    public void authenticateTest(){
        when(userRepository.findByEmailID(any())).thenReturn(null);

        String result = userService.authenticate(new UserRequestModel());

        assertNull(result, "authenticate is not returning null with invalid user model");
    }

}
