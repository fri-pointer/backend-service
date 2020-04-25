package io.fripointer.services;

import io.fripointer.lib.dto.UploadSignature;
import io.fripointer.lib.dto.UploadSignatureRequest;

import java.io.File;

public interface UploadService {
    
    UploadSignature generateSignature(UploadSignatureRequest signatureRequest);
    
    String uploadFile(File file, String mimeType, String fileName);
}
