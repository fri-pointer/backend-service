package io.fripointer.integrations.keycloak;

import com.mjamsek.rest.dto.EntityList;
import com.mjamsek.rest.exceptions.NotFoundException;
import io.fripointer.integrations.keycloak.lib.User;

import java.util.Optional;

/**
 * Service for managing Keycloak resources
 *
 * @author Miha Jamsek
 */
public interface KeycloakService {
    
    /**
     * Query users or retrieve a paginated list of users
     *
     * @param query  search string in username, first or last name, or email
     * @param limit  max number of returned results
     * @param offset starting point of user list
     * @return list of users, with total count
     */
    EntityList<User> queryUsers(String query, Integer limit, Integer offset);
    
    /**
     * Retrieve user info
     *
     * @param userId UUID user id
     * @return user info
     */
    Optional<User> getUser(String userId);
    
    /**
     * Creates new user on Keycloak server
     *
     * @param user data for new user
     */
    void createUser(User user);
    
    /**
     * Method that retrieves user info, sets selected fields and updates user
     *
     * @param user   user data to update existing data
     * @param userId UUID user id
     * @throws NotFoundException if user not found
     */
    void updateUser(User user, String userId) throws NotFoundException;
    
    /**
     * Method that updates user info with whatever data you provide
     *
     * @param user   Complete user data to replace existing data
     * @param userId UUID user id
     * @throws NotFoundException if user not found
     * @apiNote Potentially dangerous method! Use with caution.
     */
    void replaceUser(User user, String userId) throws NotFoundException;
    
    /**
     * Disables user
     *
     * @param userId UUID user id
     * @throws NotFoundException if user not found
     */
    void deleteUser(String userId) throws NotFoundException;
    
}
