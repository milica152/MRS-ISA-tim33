package com.tim33.isa.repository;

import com.tim33.isa.model.Let;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetRepository  extends JpaRepository<Let, Long> {

}