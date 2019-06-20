package com.tim33.isa.repository;

import com.tim33.isa.model.VehicleReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleReservationRepository extends JpaRepository<VehicleReservation, Long> {

}
