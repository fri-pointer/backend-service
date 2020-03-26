package io.fripointer.lib;

import java.util.List;

public class Faculty extends BaseType {

    private String name;
    private String abbreviation;
    private String description;
    private String location;
    private List<StudentProgram> studentPrograms;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<StudentProgram> getStudentPrograms() {
        return studentPrograms;
    }

    public void setStudentPrograms(List<StudentProgram> studentPrograms) {
        this.studentPrograms = studentPrograms;
    }
}
