package com.tim33.isa.repository;

import com.tim33.isa.dto.filter.FilterPretrageRCServisa;
import com.tim33.isa.model.RentACar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentACarRepository extends JpaRepository<RentACar, Long> {

    RentACar findByNaziv(String naziv);

    @Query(value = "SELECT rc.* FROM rc_servis rc " +
                   "LEFT JOIN filijale f ON f.rentacar_id = rc.id " +
                   "WHERE " +
                         "rc.naziv LIKE CONCAT('%', :#{#filter.nazivServisa}, '%') AND " +
                         "f.grad LIKE CONCAT('%', :#{#filter.adresaServisa}, '%') " +
                   "GROUP BY rc.id", nativeQuery = true)
    List<RentACar> findAllWithFilter(@Param("filter") FilterPretrageRCServisa filter);
}
