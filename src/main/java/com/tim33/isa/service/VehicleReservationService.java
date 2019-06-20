package com.tim33.isa.service;

import com.tim33.isa.model.VehicleReservation;
import com.tim33.isa.model.Vozilo;
import com.tim33.isa.repository.VehicleReservationRepository;
import com.tim33.isa.repository.VozilaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleReservationService {

    @Autowired
    VehicleReservationRepository reservationRepository;

    @Autowired
    VozilaRepository vehicleRepository;

    public VehicleReservation save(VehicleReservation novaRezervacija) {
        return reservationRepository.save(novaRezervacija);
    }

    public VehicleReservation update(VehicleReservation novaRezervacija) {
        return reservationRepository.save(novaRezervacija);
    }

    public List<VehicleReservation> findAll() {
        return reservationRepository.findAll();
    }

    public VehicleReservation findById(Long id) {
        return reservationRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) { reservationRepository.delete(findById(id)); }

    public VehicleReservation addVehicleToReservation(Vozilo vehicle, VehicleReservation reservation){
        reservation.getVozila().add(vehicle);

        double price = 0;
        for (Vozilo v : reservation.getVozila()) {
            price = price + v.getCena();
        }

        reservation.setPrice(price);
        return reservationRepository.save(reservation);
    }

    public VehicleReservation deleteVehicleFromReservation(Vozilo vehicle, Long reservationId){
        VehicleReservation reservation = findById(reservationId);

        double price = 0;
        for (Vozilo v: reservation.getVozila()) {
            if (v.getId() == vehicle.getId()) {
                reservation.getVozila().remove(v);
                break;
            }
        }

        for (Vozilo v : reservation.getVozila()) {
            price = price + v.getCena();
        }

        reservation.setPrice(price);
        save(reservation);
        return reservation;
    }
}
