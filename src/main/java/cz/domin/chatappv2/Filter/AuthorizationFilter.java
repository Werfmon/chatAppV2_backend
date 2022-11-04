package cz.domin.chatappv2.Filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import cz.domin.chatappv2.Constants.SecurityConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String tokenFromWS = request.getServletPath();
        log.info(tokenFromWS);
        if(request.getServletPath().equals("/login")) {
            doFilter(request, response, filterChain);
            return;
        }
        if(authHeader == null || !authHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            log.error("Bad token");
            doFilter(request, response, filterChain);
        } else {
            String jwtToken = authHeader.replace(SecurityConstants.TOKEN_PREFIX, "");

            Algorithm algorithm = Algorithm.HMAC256(SecurityConstants.SECRET.getBytes());

            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            try {
                DecodedJWT decodedJWT = jwtVerifier.verify(jwtToken);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, new ArrayList<>());
                SecurityContextHolder.getContext().setAuthentication(authToken);
                doFilter(request, response, filterChain);

            } catch (JWTVerificationException jwtVerificationException) {
                log.error("AuthorizationFilter, JWT error>> " + jwtVerificationException.getLocalizedMessage());
                doFilter(request, response, filterChain);
            } catch (IOException | ServletException exception) {
                log.error("AuthorizationFilter, Filter chain error>> " + exception.getLocalizedMessage());
                doFilter(request, response, filterChain);
            }
        }
    }
}
