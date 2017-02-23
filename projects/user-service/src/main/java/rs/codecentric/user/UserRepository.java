package rs.codecentric.user;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends org.springframework.data.repository.Repository<User, Long> {

    Optional<User> findOne(Long id);

    <S extends User> S save(S user);

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAll();

}
