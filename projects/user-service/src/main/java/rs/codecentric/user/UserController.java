package rs.codecentric.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, String> login(@RequestBody @Validated LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping(path = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping(path = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void createUserAccount(@RequestBody @Validated UserAccount userAccount) {
        userService.createUserAccount(userAccount);
    }

}
