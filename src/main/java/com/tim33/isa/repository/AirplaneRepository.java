package com.tim33.isa.repository;

import com.tim33.isa.model.Avion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface AirplaneRepository extends JpaRepository<Avion, Long> {
    List<Avion> findAll();

    Avion findById(long id);

    Avion findByACode(String code);

    void deleteById(long id);


}
