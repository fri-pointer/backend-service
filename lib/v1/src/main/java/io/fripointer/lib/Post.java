package io.fripointer.lib;

public class Post extends BaseType {

    private String title;
    private String content;

    // TODO: List of comments

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
