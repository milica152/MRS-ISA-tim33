package com.tim33.isa.repository;

import com.tim33.isa.model.UserVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVerificationTokenRepository extends JpaRepository<UserVerificationToken, Long> {
}
