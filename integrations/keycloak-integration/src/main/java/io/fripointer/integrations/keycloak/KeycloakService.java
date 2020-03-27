package io.fripointer.integrations.keycloak;

import com.mjamsek.rest.dto.EntityList;
import com.mjamsek.rest.exceptions.NotFoundException;
import io.fripointer.integrations.keycloak.lib.User;

import java.util.Optional;

public interface KeycloakService {

    EntityList<User> queryUsers(String query, Integer limit, Integer offset);
    
    Optional<User> getUser(String userId) throws NotFoundException;
    
    void createUser(User user);
    
    void updateUser(User user, String userId);
    
    void deleteUser(String userId);

}
