package io.fripointer.persistence;

import javax.persistence.*;

@Entity
@Table(name = "files")
@NamedQueries({
    @NamedQuery(name = FileEntity.FIND_BY_KEY, query = "SELECT f FROM FileEntity f WHERE f.key = :key")
})
public class FileEntity extends BaseEntity {
    
    public static final String FIND_BY_KEY = "FileEntity.findByKey";

    @Column(name = "name")
    private String name;
    
    @Column(name = "key", unique = true)
    private String key;

    @Column(name = "location")
    private String location;

    @Column(name = "mime_type")
    private String mimeType;

    @Column(name = "file_extension")
    private String fileExtension;

    @Column(name = "uploaded")
    private boolean uploaded;
    
    @ManyToOne
    @JoinColumn(name = "shared_content_id")
    private SharedContentEntity sharedContent;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getMimeType() {
        return mimeType;
    }
    
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
    
    public String getFileExtension() {
        return fileExtension;
    }
    
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }
    
    public SharedContentEntity getSharedContent() {
        return sharedContent;
    }
    
    public void setSharedContent(SharedContentEntity sharedContent) {
        this.sharedContent = sharedContent;
    }
    
    public boolean isUploaded() {
        return uploaded;
    }
    
    public void setUploaded(boolean uploaded) {
        this.uploaded = uploaded;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
}
