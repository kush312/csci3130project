package ca.dal.Maverick.config;

import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;

public interface AuthenticationProviders {

    String AUTHENTICATE_HEADER_STRING = "Authorization";

    Authentication getAuthentication(HttpServletRequest request);

}
