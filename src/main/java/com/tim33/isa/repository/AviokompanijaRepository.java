package com.tim33.isa.repository;

import com.tim33.isa.model.Aviokompanija;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AviokompanijaRepository  extends JpaRepository<Aviokompanija, Long> {

    List<Aviokompanija> findAll();

    Aviokompanija findById(long id);

    Aviokompanija findByNaziv(String naziv);

    void deleteById(long id);


}
