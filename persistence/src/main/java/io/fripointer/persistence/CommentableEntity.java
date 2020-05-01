package io.fripointer.persistence;

import javax.persistence.*;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class CommentableEntity extends BaseEntity {
    
    @OneToMany(mappedBy = "parent")
    protected List<CommentEntity> comments;
    
    public List<CommentEntity> getComments() {
        return comments;
    }
    
    public void setComments(List<CommentEntity> comments) {
        this.comments = comments;
    }
}
