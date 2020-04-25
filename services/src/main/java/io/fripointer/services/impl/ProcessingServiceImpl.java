package io.fripointer.services.impl;

import io.fripointer.lib.processing.ProcessedImage;
import io.fripointer.services.ProcessingService;
import io.fripointer.services.UploadService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.UUID;

@ApplicationScoped
public class ProcessingServiceImpl implements ProcessingService {
    
    @Inject
    private UploadService uploadService;
    
    @Override
    public String processImageInHtml(String html) {
        Document htmlDocument = Jsoup.parse(html);
        Elements imageElements = htmlDocument.select("img");
        
        imageElements.forEach(imageElem -> {
            
            String base64ImageSource = imageElem.attr("src");
            ProcessedImage image = new ProcessedImage(base64ImageSource);
            
            try {
                
                byte[] imageBytes = Base64.getDecoder().decode(image.getBase64());
                File tempFile = File.createTempFile("temp-file-base", "." + image.getExtension());
                
                try (OutputStream os = new FileOutputStream(tempFile)) {
                    os.write(imageBytes);
                }
    
                String fileName = "comments/comment-" +  UUID.randomUUID() + "." + image.getExtension();
                String imageUrl = uploadService.uploadFile(tempFile, image.getMimeType(), fileName);
                imageElem.attr("src", imageUrl);
                tempFile.delete();
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        
        return htmlDocument.toString();
    }
}
