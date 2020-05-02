package io.fripointer.integrations.keycloak.impl;

import com.mjamsek.auth.keycloak.context.AuthContext;
import com.mjamsek.rest.exceptions.UnauthorizedException;
import io.fripointer.integrations.keycloak.UserService;
import io.fripointer.integrations.keycloak.common.ClaimConst;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequestScoped
public class UserServiceImpl implements UserService {
    
    @Inject
    private AuthContext authContext;
    
    @Override
    public boolean userCompletedSetup() throws UnauthorizedException {
        return getUserAttribute(ClaimConst.SETUP_COMPLETED)
            .map(claim -> (Boolean) claim)
            .orElse(false);
    }
    
    @Override
    public Optional<Object> getUserAttribute(String attributeName) throws UnauthorizedException {
        if (!authContext.isAuthenticated()) {
            throw new UnauthorizedException("user.error.unauthorized");
        }
        Map<String, Object> claims = authContext.getClaims();
        return Optional.ofNullable(claims.get(attributeName));
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public boolean isModeratorOf(String courseId) throws UnauthorizedException {
        return getUserAttribute(ClaimConst.MODERATOR_OF)
            .map(claim -> {
                List<String> moderatedCourses = (List<String>) claim;
                return moderatedCourses
                    .stream()
                    .anyMatch(courseEntry -> courseEntry.equals(courseId));
            })
            .orElse(false);
    }
}
