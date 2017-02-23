package rs.codecentric.comment;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDateTime;

@Document(indexName = "comments", type = "Comment", shards = 1, replicas = 0, refreshInterval = "-1")
public class Comment {

    @Id
    private String id;

    @Field(index = FieldIndex.not_analyzed)
    private String topicId;

    @Field(index = FieldIndex.analyzed)
    private String comment;

    @Field(type = FieldType.Object)
    private User commentedBy;

    @Field(type = FieldType.Date, format = DateFormat.date_time_no_millis)
    private LocalDateTime commentedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getCommentedBy() {
        return commentedBy;
    }

    public void setCommentedBy(User commentedBy) {
        this.commentedBy = commentedBy;
    }

    public LocalDateTime getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(LocalDateTime commentedAt) {
        this.commentedAt = commentedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Comment comment1 = (Comment) o;
        return Objects.equal(id, comment1.id) && Objects.equal(topicId, comment1.topicId)
                && Objects.equal(comment, comment1.comment) && Objects.equal(commentedBy, comment1.commentedBy)
                && Objects.equal(commentedAt, comment1.commentedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, topicId, comment, commentedBy, commentedAt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("topicId", topicId).add("comment", comment)
                .add("commentedBy", commentedBy).add("commentedAt", commentedAt).toString();
    }
}