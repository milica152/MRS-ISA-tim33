package com.tim33.isa.repository;

import com.tim33.isa.model.Let;
import com.tim33.isa.model.LokacijaPresedanja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LokacijaPresedanjaRepository extends JpaRepository<LokacijaPresedanja, Long> {
    LokacijaPresedanja findById(String id);
    LokacijaPresedanja findByNazivAerodroma(String naziv);
    List<LokacijaPresedanja> findAll();
}
