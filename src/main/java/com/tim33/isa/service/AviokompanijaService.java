package com.tim33.isa.service;

import com.tim33.isa.model.Aviokompanija;
import com.tim33.isa.model.LokacijaPresedanja;
import com.tim33.isa.model.RequestWrapper;
import com.tim33.isa.repository.AviokompanijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class AviokompanijaService {
    @Autowired
    AviokompanijaRepository repository;
    @Autowired
    LokacijaPresedanjaService destser;

    public Aviokompanija save(Aviokompanija noviProfil){
        // Manipulacija profilom...
        return repository.save(noviProfil);
    }

    public Aviokompanija update(Aviokompanija noviProfil){
        // Manipulacija profilom...
        return repository.save(noviProfil);
    }

    public List<Aviokompanija> findAll(){
        return repository.findAll();
    }

    public Aviokompanija findById(long id){
        return repository.findById(id);

    }
    public Aviokompanija findByNaziv(String naziv){
        return repository.findByNaziv(naziv);
    }

    public String checkAK(Aviokompanija ak){
        Aviokompanija a = new Aviokompanija();
        a.setNaziv(ak.getNaziv());
        a.setAdresa(ak.getAdresa());
        a.setOpis(ak.getOpis());
        a.setDestinations(ak.getDestinations());
        a.setKarteZaBrzu(ak.getKarteZaBrzu());
        a.setAdmins(ak.getAdmins());


        if (ak.getNaziv().isEmpty()||ak.getAdresa().isEmpty()||ak.getOpis().isEmpty()||ak.getDestinations().isEmpty()){
            return "All fields are required!";
        }
        if(ak.getOpis().length()<50){
            return "Description must have at least 50 characters!";
        }

        if (findByNaziv(ak.getNaziv()) != null){
            return "Name already taken!";
        }
        repository.save(a);
        /*for(LokacijaPresedanja d:ak.getDestinations()){
            d.getAirline().add(a);
            destser.save(d);

        }*/
        return "true";
    }

    public void deleteById(long id) { repository.delete(findById(id)); }

}
