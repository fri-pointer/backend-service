package io.fripointer.integrations.keycloak;

import com.mjamsek.rest.exceptions.UnauthorizedException;

import java.util.Optional;

public interface UserService {
    
    /**
     * Returns true, if user has completed after-registration setup (has present attribute setupCompleted)
     * @return true, if user has completed after-registration setup
     * @throws UnauthorizedException If there is no provided access token
     */
    boolean userCompletedSetup() throws UnauthorizedException;
    
    /**
     * Retrieves user attribute's value if it is present in provided access token
     * @param attributeName name of attribute in access token
     * @return attribute value
     * @throws UnauthorizedException If there is no provided access token
     */
    Optional<Object> getUserAttribute(String attributeName) throws UnauthorizedException;
    
    /**
     * Checks, whether user has rights to moderate selected course
     * @param courseId course id
     * @return true, if user has moderating rights for course with given id
     * @throws UnauthorizedException If there is no provided access token
     */
    boolean isModeratorOf(String courseId) throws UnauthorizedException;
    
}
