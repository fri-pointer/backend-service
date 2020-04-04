package io.fripointer.lib.dto;

import java.time.Instant;
import java.util.Map;

public class UploadSignature {
    
    private Instant expires;
    
    private String url;
    
    private String key;
    
    private Map<String, String> requiredHeaders;
    
    public Instant getExpires() {
        return expires;
    }
    
    public void setExpires(Instant expires) {
        this.expires = expires;
    }
    
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public Map<String, String> getRequiredHeaders() {
        return requiredHeaders;
    }
    
    public void setRequiredHeaders(Map<String, String> requiredHeaders) {
        this.requiredHeaders = requiredHeaders;
    }
}
