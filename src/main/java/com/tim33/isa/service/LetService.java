package com.tim33.isa.service;

import com.tim33.isa.model.Let;
import com.tim33.isa.model.LetZaDodavanje;
import com.tim33.isa.repository.AviokompanijaRepository;
import com.tim33.isa.repository.LetRepository;
import com.tim33.isa.repository.LokacijaPresedanjaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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


    public String checkAdding(LetZaDodavanje noviLetStr, String aviokompanijaID){
        //utility
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datumIVremePoletanja_str = noviLetStr.getDatum_poletanja()+ " " + noviLetStr.getVreme_poletanja()+ ":00";
        String datumIVremeSletanja_str = noviLetStr.getDatum_sletanja() + " " + noviLetStr.getVreme_sletanja() + ":00";
        Date datumIVremePoletanja = new Date();
        Date datumIVremeSletanja = new Date();

        try {
            // konvertovanje datuma poletanja
            datumIVremePoletanja = df.parse(datumIVremePoletanja_str);
            Calendar kalendarP = Calendar.getInstance();
            kalendarP.clear();
            kalendarP.setTime(datumIVremePoletanja);
            kalendarP.add(Calendar.HOUR, 2);
            datumIVremePoletanja = kalendarP.getTime();

            // konvertovanje datuma sletanja
            datumIVremeSletanja = df.parse(datumIVremeSletanja_str);
            Calendar kalendarS = Calendar.getInstance();
            kalendarS.clear();
            kalendarS.setTime(datumIVremeSletanja);
            kalendarS.add(Calendar.HOUR, 2);
            datumIVremeSletanja = kalendarS.getTime();
            System.out.println(datumIVremeSletanja.getHours());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Let noviLet = new Let();

        //provere
        try{
            noviLet.setCena(Double.parseDouble(noviLetStr.getCena()));
        }catch(Exception ex){
            return "Cost must be positive number!";
        }
        if(noviLet.getCena()<0){
            return "Cost must be positive number!";
        }
        System.out.println(noviLetStr.getAviokompanija_id());
        noviLet.setAviokompanija(repositoryA.findById(Long.parseLong(noviLetStr.getAviokompanija_id())));

        try{
            noviLet.setOcena(Double.parseDouble(noviLetStr.getOcena()));
        }catch (Exception ex){
            return "Rate must be positive number!";
        }
        if(noviLet.getOcena()<0){
            return "Rate must be positive number!";
        }
        if(repositoryLP.findByNazivAerodroma(noviLetStr.getOdredisni_aerodrom_id())  != null){
            noviLet.setOdredisniAerodrom(repositoryLP.findByNazivAerodroma(noviLetStr.getOdredisni_aerodrom_id()));
        }else{
            return "Departure airport must be existing aiport!";
        }
        if(repositoryLP.findByNazivAerodroma(noviLetStr.getPolazni_aerodrom_id()) != null){
            noviLet.setPolazniAerodrom(repositoryLP.findByNazivAerodroma(noviLetStr.getPolazni_aerodrom_id()));
        }else{
            return "Arrival airport must be existing aiport!";
        }
        noviLet.setVremePoletanja(datumIVremePoletanja);
        noviLet.setVremeSletanja(datumIVremeSletanja);


        if(noviLet.getVremePoletanja().after(noviLet.getVremeSletanja()) || noviLet.getVremePoletanja().equals(noviLet.getVremeSletanja())){
            return "Departure must be before arrival!";
        }
        try{
            noviLet.setDuzinaPutovanja((int) ((noviLet.getVremeSletanja().getTime() - noviLet.getVremePoletanja().getTime()) / (1000 * 60)));
            System.out.println(noviLet.getVremeSletanja().getTime() + "-" + noviLet.getVremePoletanja().getTime() + "=" + noviLet.getDuzinaPutovanja());
        }catch(Exception ex){
            return "Wrong date!";
        }
        repository.save(noviLet);
        return "true";

    }

    public List<Let> findAllFromAviocompany(long idAviocomp) {
        return repository.findAllByAviokompanijaId(idAviocomp);
    }


}
