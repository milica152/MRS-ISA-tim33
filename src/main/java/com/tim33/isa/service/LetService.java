package com.tim33.isa.service;

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
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
        return repository.findById(id).orElse(null);
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

            if(!(noviLetStr.getVreme_sletanja().equalsIgnoreCase(""))){
                // konvertovanje datuma povratka ako je povratni let
                datumIVremeSletanja = df.parse(datumIVremeSletanja_str);
                Calendar kalendarS = Calendar.getInstance();
                kalendarS.clear();
                kalendarS.setTime(datumIVremeSletanja);
                kalendarS.add(Calendar.HOUR, 2);
                datumIVremeSletanja = kalendarS.getTime();
                noviLet.setVremePovratka(datumIVremeSletanja);
                try{
                    noviLet.setDuzinaPovratak(Integer.parseInt(noviLetStr.getDuzina_povratak()));
                }catch(Exception ex){
                    return "Duration of return must be positive number!";
                }
                if(noviLet.getDuzinaPovratak()<0){
                    return "Duration of return must be positive number!";
                }


            }else{
                //ako nije:
                noviLet.setVremePovratka(null);
                noviLet.setDuzinaPovratak(0);
            }

        } catch (ParseException e) {
            return "Nevalidan format datuma!";
        }


        //provere
        try{
            noviLet.setDuzinaPolazak(Integer.parseInt(noviLetStr.getDuzina_polazak()));
        }catch(Exception ex){
            return "Duration of departure must be positive number!";
        }
        if(noviLet.getDuzinaPolazak()<0){
            return "Duration of departure must be positive number!";
        }


        try{
            noviLet.setCena(Double.parseDouble(noviLetStr.getCena()));
        }catch(Exception ex){
            return "Cost must be positive number!";
        }
        if(noviLet.getCena()<0){
            return "Cost must be positive number!";
        }
        if(noviLetStr.getTip().equalsIgnoreCase("ROUND_TRIP")){
            if(noviLet.getVremePolaska().after(noviLet.getVremePovratka()) || noviLet.getVremePolaska().equals(noviLet.getVremePovratka())){
                return "Departure must be before return!";
            }
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

        noviLet.setTipPuta(TipPuta.valueOf(noviLetStr.getTip()));
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
            // tip
            if(!(criteria.getType().trim().equals(""))){
                if(!(current.getTipPuta().name().replace('_', '-').equalsIgnoreCase(criteria.getType()))){
                    it.remove();
                    continue;
                }
            }

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

            // datum povratka
            if(criteria.getType().equalsIgnoreCase("round-trip")){   //pogledaj jos format !!
                if(!(criteria.getDateTo().trim().equals(""))){
                    DateFormat df3 = new SimpleDateFormat("dd-MM-yyyy");
                    String date = df3.format(current.getVremePovratka());
                    if(!(criteria.getDateTo().equals(date))){
                        it.remove();
                        continue;
                    }

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
}
