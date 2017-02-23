package rs.codecentric.user;


import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotBlank;

public class UserAccount {

    private String id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "E-mail is required")
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(username, that.username) &&
                Objects.equal(email, that.email) &&
                Objects.equal(password, that.password) &&
                Objects.equal(firstName, that.firstName) &&
                Objects.equal(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, username, email, password, firstName, lastName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("username", username)
                .add("email", email)
                .add("password", password)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .toString();
    }
}
