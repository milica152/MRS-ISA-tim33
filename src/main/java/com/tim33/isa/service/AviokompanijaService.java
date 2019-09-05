package com.tim33.isa.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.tim33.isa.dto.filter.SearchAirline;
import com.tim33.isa.model.Aviokompanija;
import com.tim33.isa.model.Destinacija;
import com.tim33.isa.model.Let;
import com.tim33.isa.repository.AviokompanijaRepository;
import com.tim33.isa.repository.DestinacijaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class AviokompanijaService {
    @Autowired
    AviokompanijaRepository repository;
    @Autowired
    DestinacijaService destser;

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
        for(Destinacija d:ak.getDestinations()){
            d.setAirline(a);
            destser.save(d);

        }
        return "true";
    }

    public void deleteById(long id) { repository.delete(findById(id)); }

    public List<Aviokompanija> searchAK (SearchAirline params){
        List<Aviokompanija> result = repository.findAll();

        Iterator<Aviokompanija> it = result.iterator();

        Aviokompanija current;
        if(params.getName()== null){
            params.setName("");
        }
        if(params.getCity()== null){
            params.setCity("");
        }

        while (it.hasNext()) {
            current = it.next();

            System.out.println(current.getNaziv() + "?c" + params.getName());
            System.out.println(current.getAdresa() + "?c" + params.getCity());
            if (!(params.getName().trim().equals(""))) {
                if (!(current.getNaziv().toLowerCase().contains(params.getName().toLowerCase()))) {
                    it.remove();
                    continue;
                }
            }
            if (!(params.getCity().trim().equals(""))) {
                if (!(current.getAdresa().toLowerCase().contains(params.getCity().toLowerCase()))) {
                    it.remove();
                }
            }

        }

        return result;
    }


}
