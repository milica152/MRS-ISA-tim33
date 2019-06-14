package com.tim33.isa.service;

import com.tim33.isa.model.Hotel;
import com.tim33.isa.model.HotelReservation;
import com.tim33.isa.model.RequestHotelServices;
import com.tim33.isa.model.UslugeHotela;
import com.tim33.isa.repository.HotelServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServicesService {

    @Autowired
    HotelServicesRepository repository;
    @Autowired
    HotelService hotelService;

    public UslugeHotela save(UslugeHotela novaUsluga) {
        return repository.save(novaUsluga);
    }

    public UslugeHotela update(UslugeHotela novaUsluga) {
        return repository.save(novaUsluga);
    }


    public List<UslugeHotela> findAllHotelServices(Long hotelId) {
        return repository.findAllHotelServices(hotelId);
    }

    public UslugeHotela findById(long id) {
        return repository.findById(id).orElse(null);
    }

    public UslugeHotela findByNameAndHotel(String name, Long hotelId){return repository.findByNazivAndHotel(name, hotelId);}
    public void deleteById(long id) { repository.delete(findById(id)); }

    public void addServicesToHotel(RequestHotelServices request, Long HotelId){

        Hotel hotel = hotelService.findById(HotelId);
        if(request.getAirportTransferPrice()!=null && request.getAirportTransferPrice()==-1){
            UslugeHotela usluga = findByNameAndHotel("Airport Transfer", HotelId);
            repository.delete(usluga);
        }else if(request.getAirportTransferPrice()!=null){
            UslugeHotela usluga = findByNameAndHotel("Airport Transfer", HotelId);
            if(usluga==null){
                usluga = new UslugeHotela();
            }
            usluga.setCena(request.getAirportTransferPrice());
            usluga.setHotel(hotel);
            usluga.setNaziv("Airport Transfer");
            repository.save(usluga);
        }
        if(request.getParkingLotPrice()!=null && request.getParkingLotPrice()==-1){
            UslugeHotela usluga = findByNameAndHotel("Parking Lot", HotelId);
            repository.delete(usluga);
        }else if(request.getParkingLotPrice()!=null){
            UslugeHotela usluga = findByNameAndHotel("Parking Lot", HotelId);
            if(usluga==null){
                usluga = new UslugeHotela();

            }
            usluga.setCena(request.getParkingLotPrice());
            usluga.setHotel(hotel);
            usluga.setNaziv("Parking Lot");
            repository.save(usluga);
        }
        if(request.getPoolPrice()!=null && request.getPoolPrice()==-1){
            UslugeHotela usluga = findByNameAndHotel("Pool Access", HotelId);
            repository.delete(usluga);
        }else if(request.getPoolPrice()!=null){
            UslugeHotela usluga = findByNameAndHotel("Pool Access", HotelId);
            if(usluga==null){
                usluga = new UslugeHotela();
            }
            usluga.setCena(request.getPoolPrice());
            usluga.setHotel(hotel);
            usluga.setNaziv("Pool Access");
            repository.save(usluga);
        }
        if(request.getRestaurantPrice()!=null && request.getRestaurantPrice()==-1){
            UslugeHotela usluga = findByNameAndHotel("Restaurant", HotelId);
            repository.delete(usluga);
        }else if(request.getRestaurantPrice()!=null){
            UslugeHotela usluga = findByNameAndHotel("Restaurant", HotelId);
            if(usluga==null){
                usluga = new UslugeHotela();
            }
            usluga.setCena(request.getRestaurantPrice());
            usluga.setHotel(hotel);
            usluga.setNaziv("Restaurant");
            repository.save(usluga);
        }
        if(request.getRoomServicePrice()!=null && request.getRoomServicePrice()==-1){
            UslugeHotela usluga = findByNameAndHotel("Room Service", HotelId);
            repository.delete(usluga);
        }else if(request.getRoomServicePrice()!=null){
            UslugeHotela usluga = findByNameAndHotel("Room Service", HotelId);
            if(usluga==null){
                usluga = new UslugeHotela();
            }
            usluga.setCena(request.getRoomServicePrice());
            usluga.setHotel(hotel);
            usluga.setNaziv("Room Service");
            repository.save(usluga);
        }
        if(request.getWellnessPrice()!=null && request.getWellnessPrice()==-1){
            UslugeHotela usluga = findByNameAndHotel("Wellness", HotelId);
            repository.delete(usluga);
        }else if(request.getWellnessPrice()!=null){
            UslugeHotela usluga = findByNameAndHotel("Wellness", HotelId);
            if(usluga==null){
                usluga = new UslugeHotela();
            }
            usluga.setCena(request.getWellnessPrice());
            usluga.setHotel(hotel);
            usluga.setNaziv("Wellness");
            repository.save(usluga);
        }
        if(request.getSpaPrice()!=null && request.getSpaPrice()==-1){
            UslugeHotela usluga = findByNameAndHotel("Spa", HotelId);
            repository.delete(usluga);
        }else if(request.getSpaPrice()!=null){
            UslugeHotela usluga = findByNameAndHotel("Spa", HotelId);
            if(usluga==null){
                usluga = new UslugeHotela();
            }
            usluga.setCena(request.getSpaPrice());
            usluga.setHotel(hotel);
            usluga.setNaziv("Spa");
            repository.save(usluga);
        }
        if(request.getWiFiPrice()!=null && request.getWiFiPrice()==-1){
            UslugeHotela usluga = findByNameAndHotel("WiFi", HotelId);
            repository.delete(usluga);
        }else if(request.getWiFiPrice()!=null){
            UslugeHotela usluga = findByNameAndHotel("WiFi", HotelId);
            if(usluga==null){
                usluga = new UslugeHotela();
            }
            usluga.setCena(request.getWiFiPrice());
            usluga.setHotel(hotel);
            usluga.setNaziv("WiFi");
            repository.save(usluga);
        }


    }



}
