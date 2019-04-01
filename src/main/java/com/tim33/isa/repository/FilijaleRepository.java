package com.tim33.isa.repository;

import com.tim33.isa.model.Filijala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FilijaleRepository extends JpaRepository<Filijala, Long>, JpaSpecificationExecutor<Filijala> {

}
