package ca.dal.Maverick.user.service;

import ca.dal.Maverick.jwt.TokenService;
import ca.dal.Maverick.user.entity.UserQuestionRequestModel;
import ca.dal.Maverick.user.entity.UserRequestModel;
import ca.dal.Maverick.utils.Utils;
import ca.dal.Maverick.user.entity.UserModel;
import ca.dal.Maverick.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private String question;
    private String securityQuestion;
    private UserModel newUser;
    private String answer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel signupUser(UserModel userModel){
        String encodedPassword = passwordEncoder.encode(userModel.getPassword());
        userModel.setPassword(encodedPassword);
        return userRepository.save(userModel);
    }

    public String authenticate(UserRequestModel user){
        UserModel auth = userRepository.findByEmailID(user.getEmailID());
        if(Utils.isNull(auth)) {return null;}
        return passwordEncoder.matches(user.getPassword(),auth.getPassword()) ? tokenService.generateToken(auth.getId()) : null;
    }


    public UserModel getQuestion(UserQuestionRequestModel model){
        UserModel user = userRepository.findByEmailID(model.getEmailID());
        question = user.getQuestion();
        newUser = user;
        return user;
    }

    public UserModel securityQuestion(){
        return newUser;
    }
    public UserModel userDetails(){
        return newUser;
    }

    public UserModel getAnswer(UserQuestionRequestModel model){

        UserModel user = userRepository.findByEmailID(model.getEmailID());
        answer = user.getAnswer();
        if(model.getAnswer().equals(answer)) {
            return user;
        }else{
            return null;
        }
    }

    public UserModel getUser(long userID) {
        Optional<UserModel> userModelOptional = userRepository.findById(userID);

        return userModelOptional.orElse(null);
    }
    
    @CrossOrigin
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    public UserModel updatePassword(UserRequestModel userRequestModel, Long id){
        UserModel userModel;
        Optional<UserModel> optionalUserModel = userRepository.findById(id);

        if(optionalUserModel.isPresent()){
            userModel = optionalUserModel.get();
        }else{
            return null;
        }
        if(userRequestModel.getPassword()!=null){
            String encodedPassword = passwordEncoder.encode(userRequestModel.getPassword());
            userModel.setPassword(encodedPassword);
        }
        userRepository.save(userModel);
        return userModel;
    }
}
