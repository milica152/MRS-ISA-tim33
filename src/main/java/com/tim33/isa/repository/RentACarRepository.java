package com.tim33.isa.repository;

import com.tim33.isa.model.RentACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentACarRepository extends JpaRepository<RentACar, Long> {

    RentACar findByNaziv(String naziv);

}
