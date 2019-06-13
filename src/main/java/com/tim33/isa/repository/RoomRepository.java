package com.tim33.isa.repository;

import com.tim33.isa.model.Hotel;
import com.tim33.isa.model.Soba;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Soba, Long>, JpaSpecificationExecutor<Soba> {

    Soba findByRoomNo(Integer roomNo);

    @Query(value = "select * from sobe r where r.hotel = ?2 and r.room_no=?1", nativeQuery = true)
    Soba findHotelRoomByNo(Integer roomNo, Hotel hotel);

    @Query(value = "select * from sobe r where r.hotel = ?1", nativeQuery = true)
    List<Soba> findHotelRooms(Hotel hotel);

    @Query(value = "select count(*) from sobe r where r.tip_sobe = ?1 and r.hotel=?2", nativeQuery = true)
    int noOfRoomType(Integer type, Hotel hotel);

    @Query(value="select * from sobe r where r.tip_sobe=?1 and r.hotel=?2 and r.cena_nocenja between ?3 and ?4", nativeQuery = true)
    List<Soba> findRoomsByTypePrice(Integer type, Hotel hotel, double priceFrom, double priceTo);

}
