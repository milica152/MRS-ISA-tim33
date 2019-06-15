package com.tim33.isa.repository;

import com.tim33.isa.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    Service findByNaziv(String name);
}
