package io.fripointer.utils;

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
    
}
