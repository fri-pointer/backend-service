package io.fripointer.integrations.keycloak.impl;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import com.mjamsek.auth.keycloak.client.KeycloakClient;
import com.mjamsek.rest.dto.EntityList;
import com.mjamsek.rest.exceptions.NotFoundException;
import io.fripointer.integrations.keycloak.KeycloakService;
import io.fripointer.integrations.keycloak.apis.KeycloakApi;
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
            .build(KeycloakApi.class);
    }
    
    @Override
    public EntityList<User> queryUsers(String query, Integer limit, Integer offset) {
        
        Query queryBean = new Query();
        queryBean.setQuery(query);
        queryBean.setSimple(false);
        queryBean.setLimit(limit);
        queryBean.setOffset(offset);
        
        List<User> users = KeycloakClient
            .callKeycloak(token -> keycloakApi.getUsers(this.realm, queryBean, "Bearer " + token));
        
        int userCount = KeycloakClient
            .callKeycloak(token -> keycloakApi.getUsersCount(this.realm, queryBean, "Bearer " + token));
        
        return new EntityList<>(users, userCount);
    }
    
    @Override
    public Optional<User> getUser(String userId) {
        try {
            User user = KeycloakClient
                .callKeycloak(token -> keycloakApi.getUser(this.realm, userId, "Bearer " + token));
            return Optional.of(user);
        } catch (NotFoundException e) {
            return Optional.empty();
        }
    }
    
    @Override
    public void createUser(User user) {
        // TODO:
    }
    
    @Override
    public void updateUser(User user, String userId) {
        // TODO:
    }
    
    @Override
    public void deleteUser(String userId) {
        getUser(userId).ifPresent(user -> {
            user.setEnabled(false);
            updateUser(user, userId);
        });
    }
}
