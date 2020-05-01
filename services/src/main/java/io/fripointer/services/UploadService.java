package io.fripointer.services;

import io.fripointer.lib.dto.UploadSignature;
import io.fripointer.lib.dto.UploadSignatureRequest;

public interface UploadService {
    
    UploadSignature generateSignature(UploadSignatureRequest signatureRequest);
    
    String uploadFile(byte[] bytes, String mimeType, String fileName);
}
