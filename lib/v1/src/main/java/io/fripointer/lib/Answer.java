package io.fripointer.lib;

public class Answer extends BaseType {

    private String content;
    private Boolean accepted;

    // TODO: List of comments;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

}
