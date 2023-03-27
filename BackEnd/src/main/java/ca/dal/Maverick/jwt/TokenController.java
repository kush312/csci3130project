package ca.dal.Maverick.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;


@RestController
@CrossOrigin
@RequestMapping("/token")
public class TokenController {

  @Autowired
  TokenService tokenService;

  @GetMapping("/getUserID")
  public Map<String, String> getUserID(@RequestHeader HttpHeaders headers){
    return Collections.singletonMap(
        "userID", tokenService.getUserId(headers.getFirst(HttpHeaders.AUTHORIZATION))
    );
  }
}
