package com.tim33.isa.service;

import com.tim33.isa.dto.filter.FilterFlight;
import com.tim33.isa.dto.filter.SearchFlight;
import com.tim33.isa.model.*;
import com.tim33.isa.repository.AviokompanijaRepository;
import com.tim33.isa.repository.LetRepository;
import com.tim33.isa.repository.LokacijaPresedanjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class LetService {
    @Autowired
    LetRepository repository;
    @Autowired
    AviokompanijaRepository repositoryA;
    @Autowired
    LokacijaPresedanjaRepository repositoryLP;

    public Let save(Let noviLet){
        // Manipulacija letom...
        return repository.save(noviLet);
    }

    public Let update(Let noviLet) {
        // Manipulacija letom...
        return repository.save(noviLet);
    }

    public List<Let> findAll() {
        return repository.findAll();
    }

    public Let findById(long id) {
        return repository.findById(id);
    }


    public String checkAdding(LetZaDodavanje noviLetStr){
        //utility
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String datumIVremePoletanja_str = noviLetStr.getDatum_poletanja()+ " " + noviLetStr.getVreme_poletanja()+ ":00";
        String datumIVremeSletanja_str = noviLetStr.getDatum_sletanja() + " " + noviLetStr.getVreme_sletanja() + ":00";
        Date datumIVremePoletanja;
        Date datumIVremeSletanja;
        Let noviLet = new Let();


        try {
            // konvertovanje datuma poletanja
            datumIVremePoletanja = df.parse(datumIVremePoletanja_str);
            Calendar kalendarP = Calendar.getInstance();
            kalendarP.clear();
            kalendarP.setTime(datumIVremePoletanja);
            kalendarP.add(Calendar.HOUR, 2);
            datumIVremePoletanja = kalendarP.getTime();
            noviLet.setVremePolaska(datumIVremePoletanja);

            datumIVremeSletanja = df.parse(datumIVremeSletanja_str);
            Calendar kalendarS = Calendar.getInstance();
            kalendarS.clear();
            kalendarS.setTime(datumIVremeSletanja);
            kalendarS.add(Calendar.HOUR, 2);
            datumIVremeSletanja = kalendarS.getTime();
            noviLet.setVremeDolaska(datumIVremeSletanja);

        } catch (ParseException e) {
            return "Nevalidan format datuma!";
        }

        try{
            noviLet.setCena(Double.parseDouble(noviLetStr.getCena()));
        }catch(Exception ex){
            return "Cost must be positive number!";
        }
        if(noviLet.getCena()<0){
            return "Cost must be positive number!";
        }
            if(noviLet.getVremePolaska().after(noviLet.getVremeDolaska()) || noviLet.getVremePolaska().equals(noviLet.getVremeDolaska())){
                return "Departure must be before return!";
            }


        System.out.println(noviLetStr.getOdredisni_aerodrom_id());
        System.out.println(noviLetStr.getPolazni_aerodrom_id());

        if(repositoryLP.findByNazivAerodroma(noviLetStr.getOdredisni_aerodrom_id())  != null){
            noviLet.setOdredisniAerodrom(repositoryLP.findByNazivAerodroma(noviLetStr.getOdredisni_aerodrom_id()));
        }else{
            return "Arrival airport must be existing aiport!";
        }
        if(repositoryLP.findByNazivAerodroma(noviLetStr.getPolazni_aerodrom_id()) != null){
            noviLet.setPolazniAerodrom(repositoryLP.findByNazivAerodroma(noviLetStr.getPolazni_aerodrom_id()));
        }else{
            return "Departure airport must be existing aiport!";
        }
        if(noviLet.getOdredisniAerodrom().getId() == noviLet.getPolazniAerodrom().getId()){
            return "Departure and arrival must be at different airports!";
        }

        noviLet.setKlasa(KlasaLeta.valueOf(noviLetStr.getKlasa()));
        noviLet.setAviokompanija(repositoryA.findById(Long.parseLong(noviLetStr.getAviokompanija_id())));
        noviLet.setOcena(0.0);

        repository.save(noviLet);
        return "true";

    }

    public List<Let> findAllFromAviocompany(long idAviocomp) {
        return repository.findAllByAviokompanijaId(idAviocomp);
    }


    public List<Let> searchFlight(SearchFlight criteria){
        //int passangers = Integer.parseInt(criteria.getNoPassengers());  --  kad budu sedista i avion
        List<Let> result = repository.findAll();
        Iterator<Let> it = result.iterator();

        Let current;

        while (it.hasNext()) {
            current = it.next();

            //klasa
            if(!(criteria.getKlasa().trim().equals(""))){
                if(!(criteria.getKlasa().equalsIgnoreCase(current.getKlasa().name()))){
                    it.remove();
                    continue;
                }
            }

            //odredisni aer.
            if(!(criteria.getArrivalAirport().trim().equals(""))){
                if(!(criteria.getArrivalAirport().equals(current.getOdredisniAerodrom().getNazivAerodroma()))){
                    it.remove();
                    continue;
                }
            }

            // datum polaska
            if(!(criteria.getDateFrom().trim().equals(""))){
                DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
                String date = df2.format(current.getVremePolaska());
                if(!(criteria.getDateFrom().equals(date))){
                    it.remove();
                    continue;
                }
            }


            // polazni aer.
            if(!(criteria.getDepartureAirport().trim().equalsIgnoreCase(""))){
                if(!(criteria.getDepartureAirport().equalsIgnoreCase(current.getPolazniAerodrom().getNazivAerodroma()))){
                    it.remove();
                }
            }
        }
        return result;
    }

    public void deleteById(Long idDel) {
        repository.delete(findById(idDel));
    }

    public List<Let> filter(FilterFlight params) {
        List<Let> returnFlights = new ArrayList<>();

        for(Integer let : params.getFlights()){
            Let current = repository.findById(Integer.toUnsignedLong(let));
            if(!(current.getCena()> params.getMinPrice() && current.getCena() < params.getMaxPrice())){
                continue;
            }
            if(!(params.getAirline().equalsIgnoreCase("any"))){
                if(!(current.getAviokompanija().getNaziv().equalsIgnoreCase(params.getAirline()))){
                    continue;
                }
            }
            if(!(current.getDuzina() <= params.getDuration())){
                continue;
            }
            returnFlights.add(current);
        }

        return returnFlights;
    }
}
