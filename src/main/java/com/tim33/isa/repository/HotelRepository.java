package com.tim33.isa.repository;

import com.tim33.isa.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Hotel findByNaziv(String naziv);
}
