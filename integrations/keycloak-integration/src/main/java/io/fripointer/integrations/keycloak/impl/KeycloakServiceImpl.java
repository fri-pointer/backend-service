package io.fripointer.integrations.keycloak.impl;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.mjamsek.auth.keycloak.client.KeycloakClient;
import com.mjamsek.rest.dto.EntityList;
import com.mjamsek.rest.exceptions.NotFoundException;
import io.fripointer.integrations.keycloak.KeycloakService;
import io.fripointer.integrations.keycloak.apis.KeycloakApi;
import io.fripointer.integrations.keycloak.apis.ForbiddenMapper;
import io.fripointer.integrations.keycloak.apis.NotFoundMapper;
import io.fripointer.integrations.keycloak.apis.Query;
import io.fripointer.integrations.keycloak.lib.User;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class KeycloakServiceImpl implements KeycloakService {
    
    private KeycloakApi keycloakApi;
    private String realm;
    
    @PostConstruct
    private void init() {
        ConfigurationUtil configUtil = ConfigurationUtil.getInstance();
        String keycloakUrl = configUtil.get("keycloak.auth-server-url").orElseThrow();
        this.realm = configUtil.get("keycloak.realm").orElseThrow();
        
        this.keycloakApi = RestClientBuilder
            .newBuilder()
            .baseUri(URI.create(keycloakUrl))
            .register(NotFoundMapper.class)
            .register(ForbiddenMapper.class)
            .build(KeycloakApi.class);
    }
    
    @Override
    public EntityList<User> queryUsers(String query, Integer limit, Integer offset) {
        
        Query queryBean = new Query();
        queryBean.setQuery(query);
        queryBean.setSimple(false);
        queryBean.setLimit(limit);
        queryBean.setOffset(offset);
        
        List<User> users = keycloakApi.getUsers(this.realm, queryBean);
        int userCount = keycloakApi.getUsersCount(this.realm, queryBean);
        
        return new EntityList<>(users, userCount);
    }
    
    @Override
    public Optional<User> getUser(String userId) {
        try {
            User user = keycloakApi.getUser(this.realm, userId);
            return Optional.of(user);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public void createUser(User user) {
        keycloakApi.createUser(this.realm, user);
    }
    
    @Override
    public void updateUser(User user, String userId) {
        
        getUser(userId).ifPresent(existingUser -> {
            
            updateUserFields(existingUser, user);
    
            try {
                KeycloakClient
                    .callKeycloak(token -> keycloakApi.updateUser(this.realm, userId, existingUser));
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
    }
    
    @Override
    public void deleteUser(String userId) {
        getUser(userId).ifPresent(user -> {
            user.setEnabled(false);
            updateUser(user, userId);
        });
    }
    
    private void updateUserFields(User existingUser, User newValue) {
        
        existingUser.setEnabled(newValue.isEnabled());
        // TODO:
    }
}
