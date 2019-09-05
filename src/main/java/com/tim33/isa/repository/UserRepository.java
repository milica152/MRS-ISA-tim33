package com.tim33.isa.repository;
import com.tim33.isa.model.User;
import com.tim33.isa.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRolesContains(UserRole role);

//    User findByToken(String token);

    @Query(value = "select * from users u where u.id =(select t.user from verification_tokens t where t.token = ?1)", nativeQuery = true)
    User findByToken(String token);
}
