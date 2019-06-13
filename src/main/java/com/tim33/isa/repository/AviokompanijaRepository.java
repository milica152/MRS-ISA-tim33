package com.tim33.isa.repository;

import com.tim33.isa.model.Aviokompanija;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AviokompanijaRepository  extends JpaRepository<Aviokompanija, Long> {

    Aviokompanija findByNaziv(String naziv);

    Aviokompanija findById(long id);

    void deleteById(long id);
}
