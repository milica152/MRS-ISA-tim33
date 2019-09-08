package com.tim33.isa.repository;

import com.tim33.isa.dto.filter.FilterPretrageVozila;
import com.tim33.isa.model.Vozilo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VozilaRepository extends JpaRepository<Vozilo, Long>, JpaSpecificationExecutor<Vozilo> {

    List<Vozilo> findAllByRentACarId(long idRentACara);

    @Query(value = "SELECT v.* FROM vozila v " +
                   "WHERE " +
                        "v.rentacar_id = :idRentACara AND " +
                        "(:#{#filter.tipVozila} = 0 OR v.tip_vozila = :#{#filter.tipVozila}) AND " +
                        "(:#{#filter.brojPutnika} = 0 OR v.broj_mesta = :#{#filter.brojPutnika}) AND " +
                        "(:#{#filter.cenaDo} = 0 OR (v.cena >= :#{#filter.cenaOd} AND v.cena <= :#{#filter.cenaDo}))"
            , nativeQuery = true)
    List<Vozilo> findAllWithFilter(@Param("idRentACara") long idRentACara, @Param("filter")FilterPretrageVozila filter);

}