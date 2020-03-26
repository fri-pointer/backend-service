package io.fripointer.lib;

import java.util.List;

public class Question extends BaseType {

    private String title;
    private String content;
    private List<Answer> answers;

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

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
