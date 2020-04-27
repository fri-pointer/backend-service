package io.fripointer.services.impl;

import com.mjamsek.auth.keycloak.context.AuthContext;
import com.mjamsek.rest.exceptions.NotFoundException;
import com.mjamsek.rest.services.Validator;
import io.fripointer.integrations.keycloak.KeycloakService;
import io.fripointer.integrations.keycloak.lib.User;
import io.fripointer.lib.ActionCompleted;
import io.fripointer.lib.dto.UserSetupRequest;
import io.fripointer.persistence.BaseEntity;
import io.fripointer.persistence.FacultyEntity;
import io.fripointer.persistence.UniversityEntity;
import io.fripointer.persistence.constants.PersistenceConst;
import io.fripointer.services.SetupService;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.WebApplicationException;
import java.util.Optional;

@ApplicationScoped
public class SetupServiceImpl implements SetupService {
    
    private static final String UNIVERSITY_ATTR = "university_id";
    private static final String FACULTY_ATTR = "faculty_id";
    
    @PersistenceContext(unitName = PersistenceConst.MAIN_JPA_UNIT)
    private EntityManager em;
    
    @Inject
    private KeycloakService keycloakService;
    
    @Inject
    private Validator validator;
    
    @Inject
    private AuthContext authContext;
    
    @Override
    public ActionCompleted completeUserSetup(UserSetupRequest request) {
        
        validator.assertNotNull(request.getUniversityId());
        validator.assertNotNull(request.getFacultyId());
        
        getEntity(request.getFacultyId(), FacultyEntity.class).orElseThrow(() -> new NotFoundException("faculty.error.not-found"));
        getEntity(request.getUniversityId(), UniversityEntity.class).orElseThrow(() -> new NotFoundException("university.error.not-found"));
        
        updateKeycloakAttributes(request);
        
        return new ActionCompleted("OK", "Setup completed!");
    }
    
    private void updateKeycloakAttributes(UserSetupRequest request) {
        try {
            User user = keycloakService.getUser(authContext.getId())
                .orElseThrow(() -> new NotFoundException("user.error.not-found"));
            
            user.getAttributes().put(UNIVERSITY_ATTR, request.getUniversityId());
            user.getAttributes().put(FACULTY_ATTR, request.getFacultyId());
            
            keycloakService.replaceUser(user, authContext.getId());
        } catch (WebApplicationException e) {
            e.printStackTrace();
        }
    }
    
    private Optional<BaseEntity> getEntity(String entityId, Class<? extends BaseEntity> entityType) {
        return Optional.ofNullable(em.find(entityType, entityId));
    }
    
}
