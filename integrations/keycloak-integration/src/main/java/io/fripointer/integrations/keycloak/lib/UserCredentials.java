package io.fripointer.integrations.keycloak.lib;

import javax.json.bind.annotation.JsonbProperty;

public class UserCredentials {
    
    @JsonbProperty("type")
    private Type type;
    
    @JsonbProperty("value")
    private String value;
    
    @JsonbProperty("temporary")
    private boolean temporary;
    
    public Type getType() {
        return type;
    }
    
    public void setType(Type type) {
        this.type = type;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public boolean isTemporary() {
        return temporary;
    }
    
    public void setTemporary(boolean temporary) {
        this.temporary = temporary;
    }
    
    enum Type {
        PASSWORD("password");
        
        private String value;
        
        Type(String value) {
            this.value = value;
        }
        
        public String value() {
            return this.value;
        }
    }
}
