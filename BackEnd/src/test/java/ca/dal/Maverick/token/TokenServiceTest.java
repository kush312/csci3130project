package ca.dal.Maverick.token;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import ca.dal.Maverick.jwt.TokenService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ContextConfiguration(classes = {TokenService.class})
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TokenServiceTest {

    static HttpServletRequest httpRequest = mock(HttpServletRequest.class);
    static TokenService mockService = mock(TokenService.class);

    @BeforeAll
    static void init() {
        when(httpRequest.getHeader(anyString())).thenReturn("test Header");
        when(mockService.getAuthenticationUser(anyString())).thenReturn("Ardavan");
    }

    @Test
    public void getAuthenticationReturnNull(){

        TokenService service = new TokenService();
        Authentication test = service.getAuthentication("");

        assertNull(test, "getAuthentication is not returning null in case of empty string");
    }

    @Test
    public  void extractJWTReturnNUll() {

        TokenService service = new TokenService();
        String result = service.extractJWT("");

        assertNull(result, "extractJWT is not returning null with an empty token");
    }

    @Test
    public void extractJWTReturnNull() {

        TokenService service = new TokenService();
        String result = service.extractJWT("");

        assertNull(result, "extractJWT is not returning null with an empty token");
    }

    @Test
    public void extractJWTReturnNUll2() {

        TokenService service = new TokenService();
        String result = service.extractJWT("test");

        assertNull(result, "extractJWT is not returning null with an invalid token");
    }

    @Test
    public void extractJWTReturn() {

        TokenService service = new TokenService();
        String result = service.extractJWT("test test2");

        assertEquals("test2", result, "extractJWT is not returning second value of token");
    }

    @Test
    public void getAlgorithm(){

        String password = "something";

        String santa = Algorithm.HMAC256(password).toString();

        TokenService service = new TokenService();

        assertEquals(santa, service.getAlgorithm(password).toString(), "getAlgorithm is not returning correct algorithm");
    }

    @Test
    public void getTokenFromRequestReturn() {

        TokenService service = new TokenService();

        assertEquals("test Header", service.getTokenFromRequest(httpRequest));
    }

    @Test
    public void getAuthenticationTest() {

        Authentication result = mockService.getAuthentication("test");

        //assertNotNull(result);
    }
}
