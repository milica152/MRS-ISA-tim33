package com.tim33.isa.repository;

import com.tim33.isa.model.Destinacija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinacijaRepository extends JpaRepository<Destinacija, Long> {

    Destinacija findByGrad(String grad);
}
