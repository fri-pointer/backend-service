package io.fripointer.integrations.keycloak.lib;

import javax.json.bind.annotation.JsonbProperty;

public class UserCredentials {

    @JsonbProperty("type")
    private String type;
    
    @JsonbProperty("value")
    private String value;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
}
