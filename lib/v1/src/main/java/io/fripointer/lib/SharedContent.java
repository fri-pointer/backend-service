package io.fripointer.lib;

import java.util.List;

public class SharedContent extends BaseType {

    private String title;
    private String description;
    private List<File> files;

    // TODO: List of comments

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
