package io.fripointer.persistence;

import javax.persistence.*;

@Entity
@Table(name = "file")
public class FileEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "file_type")
    private String fileType;

    @Column(name = "file_extension")
    private String fileExtension;

    @ManyToOne
    @JoinColumn(name = "shared_content_id")
    private SharedContentEntity sharedContent;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
}
