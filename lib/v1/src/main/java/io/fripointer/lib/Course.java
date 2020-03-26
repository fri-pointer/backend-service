package io.fripointer.lib;

import java.util.List;

public class Course extends BaseType {

    private String name;
    private String abbreviation;
    private Integer year;
    private String owner;
    private List<Post> posts;
    private List<Question> questions;
    private List<SharedContent> sharedContents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<SharedContent> getSharedContents() {
        return sharedContents;
    }

    public void setSharedContents(List<SharedContent> sharedContents) {
        this.sharedContents = sharedContents;
    }
}
