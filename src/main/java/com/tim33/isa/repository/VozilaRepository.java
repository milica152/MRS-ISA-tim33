package com.tim33.isa.repository;

import com.tim33.isa.dto.filter.FilterPretrageVozila;
import com.tim33.isa.model.Vozilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface VozilaRepository extends JpaRepository<Vozilo, Long>, JpaSpecificationExecutor<Vozilo> {

    @Query(value = "SELECT v.* FROM vozila v WHERE v.rentacar_id = :idRentACara and v.tip_vozila = :#{#filter.tipVozila}", nativeQuery = true)
    List<Vozilo> findAllWithFilter(@Param("idRentACara") long idRentACara, @Param("filter")FilterPretrageVozila filter);

}