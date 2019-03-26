package com.tim33.isa.repository;

import com.tim33.isa.model.Vozilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VozilaRepository extends JpaRepository<Vozilo, Long> {

    @Query(value = "select * from vozila where rentacar_id = :idRentACara and tipVozila = :tipVozila", nativeQuery = true)
    List<Vozilo> findAllWithFilter(@Param("idRentACara") long idRentACara, @Param("tipVozila") int tipVozila);

}