package io.fripointer.services;

import io.fripointer.lib.ActionCompleted;
import io.fripointer.lib.dto.UserSetupRequest;

public interface SetupService {
    
    ActionCompleted completeUserSetup(UserSetupRequest request);
    
}
