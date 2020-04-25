package io.fripointer.lib.processing;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProcessedImage {
    
    private final String mimeType;
    
    private final String base64;
    
    private static final Pattern mimeTypePattern = Pattern.compile("data:(.+?)[;,]");
    
    public ProcessedImage(String base64Source) {
        int commaIndex = base64Source.indexOf(',');
        this.base64 = base64Source.substring(commaIndex + 1);
        String imageMeta = base64Source.substring(0, commaIndex);
        Matcher matcher = mimeTypePattern.matcher(imageMeta);
        matcher.find();
        this.mimeType = matcher.group(1);
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public String getBase64() {
        return base64;
    }
    
    public String getExtension() {
        return this.mimeType.split("/")[1];
    }
}
