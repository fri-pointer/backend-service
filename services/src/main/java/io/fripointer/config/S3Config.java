package io.fripointer.config;

import com.kumuluz.ee.configuration.cdi.ConfigBundle;
import com.kumuluz.ee.configuration.cdi.ConfigValue;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@ConfigBundle("upload.s3")
public class S3Config {
    
    @ConfigValue("bucket")
    private String bucket;
    
    @ConfigValue("upload-endpoint")
    private String uploadEndpoint;
    
    @ConfigValue("public-endpoint")
    private String publicEndpoint;
    
    @ConfigValue("access-key-id")
    private String accessKeyId;
    
    @ConfigValue("secret-access-key")
    private String secretAccessKey;
    
    public String getBucket() {
        return bucket;
    }
    
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    
    public String getUploadEndpoint() {
        return uploadEndpoint;
    }
    
    public void setUploadEndpoint(String uploadEndpoint) {
        this.uploadEndpoint = uploadEndpoint;
    }
    
    public String getAccessKeyId() {
        return accessKeyId;
    }
    
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }
    
    public String getSecretAccessKey() {
        return secretAccessKey;
    }
    
    public void setSecretAccessKey(String secretAccessKey) {
        this.secretAccessKey = secretAccessKey;
    }
    
    public String getPublicEndpoint() {
        return publicEndpoint;
    }
    
    public void setPublicEndpoint(String publicEndpoint) {
        this.publicEndpoint = publicEndpoint;
    }
}
