package io.fripointer.services.impl;

import com.kumuluz.ee.logs.LogManager;
import com.kumuluz.ee.logs.Logger;
import io.fripointer.config.S3Config;
import io.fripointer.lib.File;
import io.fripointer.lib.dto.UploadSignature;
import io.fripointer.lib.dto.UploadSignatureRequest;
import io.fripointer.services.FileService;
import io.fripointer.services.UploadService;
import io.fripointer.utils.FileUtil;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Configuration;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.ServerSideEncryption;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.HttpHeaders;
import java.net.URI;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class S3UploadService implements UploadService {
    
    private static final Logger LOG = LogManager.getLogger(S3UploadService.class.getName());
    
    @Inject
    private S3Config s3Config;
    
    @Inject
    private FileService fileService;
    
    private S3Presigner s3Presigner;
    
    @PostConstruct
    private void init() {
        AwsCredentials awsCredentials = AwsBasicCredentials
            .create(s3Config.getAccessKeyId(), s3Config.getSecretAccessKey());
        
        S3Configuration conf = S3Configuration
            .builder()
            .accelerateModeEnabled(true)
            .build();
        
        this.s3Presigner = S3Presigner
            .builder()
            .region(Region.EU_CENTRAL_1)
            .endpointOverride(URI.create(s3Config.getUploadEndpoint()))
            .serviceConfiguration(conf)
            .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
            .build();
    }
    
    @Override
    public UploadSignature generateSignature(UploadSignatureRequest signatureRequest) {
    
        String key = FileUtil.timestampFilename(signatureRequest.getFilename());
        String contentType = signatureRequest.getContentType();
        
        createFileStub(key, contentType);
        
        LOG.info("Generating presign request for file '{}' of type '{}'", key, contentType);
        
        PutObjectRequest uploadRequest = PutObjectRequest.builder()
            .bucket(s3Config.getBucket())
            .key(key)
            .serverSideEncryption(ServerSideEncryption.AES256)
            .contentType(contentType)
            .acl(ObjectCannedACL.PUBLIC_READ)
            .build();
    
        PutObjectPresignRequest uploadPresignRequest = PutObjectPresignRequest
            .builder()
            .putObjectRequest(uploadRequest)
            .signatureDuration(Duration.ofMinutes(30))
            .build();
    
        PresignedPutObjectRequest signedRequest = s3Presigner.presignPutObject(uploadPresignRequest);
        
        UploadSignature response = new UploadSignature();
        
        response.setKey(key);
        response.setExpires(signedRequest.expiration());
        response.setUrl(signedRequest.url().toString());
        
        Map<String, String> requiredHeaders = new HashMap<>();
        requiredHeaders.put("X-Amz-Acl", ObjectCannedACL.PUBLIC_READ.toString());
        requiredHeaders.put("X-Amz-Server-Side-Encryption", ServerSideEncryption.AES256.toString());
        requiredHeaders.put(HttpHeaders.CONTENT_TYPE, contentType);
        response.setRequiredHeaders(requiredHeaders);
        
        // TODO: save to DB publicEndpoint + / + key
        
        return response;
    }
    
    private void createFileStub(String key, String contentType) {
        File file = new File();
        file.setName(key);
        file.setKey(key);
        file.setFileExtension(FileUtil.getFileExtension(key));
        
        String location = s3Config.getPublicEndpoint() + "/" + key;
        file.setLocation(location);
        file.setMimeType(contentType);
        
        fileService.createFileStub(file);
    }
}
