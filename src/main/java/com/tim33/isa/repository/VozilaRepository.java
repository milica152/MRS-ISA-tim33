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

//    @Query(value = "select * from vozila where rentacar_id = :idRentACara and tipVozila = :tv", nativeQuery = true)
//    List<Vozilo> findAllWithFilter(@Param("idRentACara") long idRentACara, @Param("mp") String mestoPreuzimanja,
//                                   @Param("mv") String mestoVracanja, @Param("dp") LocalDate datumPreuzimanja,
//                                   @Param("dv") LocalDate datumVracanja, @Param("tv") int tipVozila,
//                                   @Param("bp") int brojPutnika);

    @Query(value = "SELECT v.* FROM baza.vozila v WHERE v.rentacar_id = :idRentACara and v.tip_vozila = :#{#filter.tipVozila}", nativeQuery = true)
    List<Vozilo> findAllWithFilter(@Param("idRentACara") long idRentACara, @Param("filter")FilterPretrageVozila filter);

}