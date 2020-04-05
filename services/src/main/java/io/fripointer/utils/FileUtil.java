package io.fripointer.utils;

import java.sql.Timestamp;

public class FileUtil {
    
    public static String getFileExtension(String filename) {
        // handle edge cases
        if (filename.endsWith(".tar.gz")) {
            return "tar.gz";
        }
        
        // if extension is present, return it, otherwise return empty string
        int i = filename.lastIndexOf('.');
        if (i > 0) {
            return filename.substring(i + 1);
        }
        return "";
    }
    
    public static String timestampFilename(String filename) {
        
        if (filename.startsWith("/")) {
            filename = filename.substring(1);
        }
    
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int i = filename.lastIndexOf("/");
        if (i > 0) {
            String path = filename.substring(0, i);
            String file = filename.substring(i + 1);
            return path + "/" + timestamp.getTime() + "-" + file;
        }
        
        return timestamp.getTime() + "-" + filename;
    }
    
}
