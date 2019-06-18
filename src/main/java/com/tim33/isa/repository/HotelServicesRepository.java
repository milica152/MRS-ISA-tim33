package com.tim33.isa.repository;

import com.tim33.isa.model.UslugeHotela;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelServicesRepository extends JpaRepository<UslugeHotela,Long> {

    @Query(value ="select uh.* from usluge_hotela uh where uh.naziv=?1 and uh.hotel_id=?2" , nativeQuery = true)
    UslugeHotela findByNazivAndHotel(String name, Long hotelId);

    @Query(value = "select * from usluge_hotela uh where uh.hotel_id=?1", nativeQuery = true)
    List<UslugeHotela> findAllHotelServices(Long hotelId);
}
