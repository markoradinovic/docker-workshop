package rs.codecentric.comment;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

public class User {

    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String username;

    @Field(index = FieldIndex.not_analyzed, type = FieldType.String)
    private String email;

    @Field(index = FieldIndex.analyzed, type = FieldType.String)
    private String fullName;

    public User() {
    }

    public User(String username, String email, String fullName) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equal(username, user.username) && Objects.equal(email, user.email)
                && Objects.equal(fullName, user.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, email, fullName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("username", username).add("email", email).add("fullName", fullName)
                .toString();
    }
}