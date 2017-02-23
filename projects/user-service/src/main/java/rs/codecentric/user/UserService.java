package rs.codecentric.user;

import com.google.common.collect.Maps;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.codecentric.error.ApiException;
import rs.codecentric.error.SecurityException;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String jwtKey;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            @Value("${jwt.key}") String jwtKey) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtKey = jwtKey;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createUserAccount(UserAccount userAccount) {

        userRepository.findByUsername(userAccount.getUsername()).ifPresent(u -> {
            throw new ApiException("User with username '" + u.getUsername() + "' already exists.");
        });

        userRepository.findByEmail((userAccount.getEmail())).ifPresent(u -> {
            throw new ApiException("User with e-mail '" + u.getEmail() + "' already exists.");
        });

        User user = new User(
                userAccount.getUsername(),
                userAccount.getEmail(),
                userAccount.getFirstName(),
                userAccount.getLastName());

        user.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        User createdUser =  userRepository.save(user);
        LOG.debug(createdUser.toString());
    }

    public Map<String, String> login(LoginRequest loginRequest) {

        return userRepository.findByUsername(loginRequest.getUsername())
                .map(user -> matchPassword(user, loginRequest.getPassword()))
                .map(this::buildToken)
                .orElseThrow(() -> new SecurityException("User not found"));
    }

    private User matchPassword(User user, String password) {

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new SecurityException("Invalid password");
        }
        return user;
    }

    private Map<String, String> buildToken(User user) {
        Map<String, String> result = Maps.newHashMap();
        String jwt = Jwts.builder()
                .setSubject(user.getUsername())
                .claim("email", user.getEmail())
                .claim("firstName", user.getFirstName())
                .claim("lastName", user.getLastName())
                .setIssuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)))
                .setExpiration(Date.from(LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.UTC)))
                .signWith(SignatureAlgorithm.HS512, jwtKey).compact();
        result.put("jwt", jwt);
        return result;

    }

}
