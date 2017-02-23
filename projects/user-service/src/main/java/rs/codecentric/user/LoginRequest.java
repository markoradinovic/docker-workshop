package rs.codecentric.user;


import com.google.common.base.Objects;
import org.hibernate.validator.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginRequest that = (LoginRequest) o;
        return Objects.equal(username, that.username) &&
                Objects.equal(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username, password);
    }
}
