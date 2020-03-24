package io.fripointer.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "shared_content")
public class SharedContentEntity extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private CourseEntity course;

    @OneToMany(mappedBy = "sharedContent")
    private List<FileEntity> files;


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
}
