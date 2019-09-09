package com.tim33.isa.service;

import com.tim33.isa.dto.filter.FilterFlight;
import com.tim33.isa.dto.filter.SearchFlight;
import com.tim33.isa.model.*;
import com.tim33.isa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
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
    @Autowired
    FlightReservationRepository repositoryFR;
    @Autowired
    AirplaneRepository airplaneRepository;
    @Autowired
    SeatRepository seatRepository;
    @Autowired
    SeatService seatService;

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

        for(Let f : findAll()){
            if(f.getSifra().equalsIgnoreCase(noviLetStr.getSifra())){
                return "Flight with that code already exists!";
            }
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

        for(Avion a : airplaneRepository.findAll()){
            if(a.getACode().equalsIgnoreCase(noviLetStr.getAirplane_code())){
                noviLet.setPlane(a);
                break;
            }
        }

        if(noviLet.getPlane() == null){
            return "Airplane code must be valid!";
        }


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

        long duzinamilisekunde = noviLet.getVremeDolaska().getTime() - noviLet.getVremePolaska().getTime();
        long duzinaminuti = duzinamilisekunde/ (60 * 1000);
        String duzinastr = Long.toString(duzinaminuti);
        int duzina = Integer.parseInt(duzinastr);
        noviLet.setDuzina(duzina);

        noviLet.setKlasa(KlasaLeta.valueOf(noviLetStr.getKlasa()));
        noviLet.setAviokompanija(repositoryA.findById(Long.parseLong(noviLetStr.getAviokompanija_id())));
        noviLet.setOcena(0.0);
        noviLet.setSifra(noviLetStr.getSifra().toUpperCase());
        repository.save(noviLet);
        seatService.generateSeats(repository.findBySifra(noviLet.getSifra()));
        return "true";

    }

    public List<Let> findAllFromAviocompany(long idAviocomp) {
        return repository.findAllByAviokompanijaId(idAviocomp);
    }

    private static Date getZeroTimeDate(Date fecha) {
        Date res = fecha;
        Calendar calendar = Calendar.getInstance();

        calendar.setTime( fecha );
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        res = calendar.getTime();

        return res;
    }

    public int[] findAllDaily(long idAviocomp, String dateStr){
        DateFormat df = new SimpleDateFormat("dd-MM-yy");
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int cnt0 = 0;
        int cnt1 = 0;
        int cnt2 = 0;
        int cnt3 = 0;
        int cnt4 = 0;
        int cnt5 = 0;
        int cnt6 = 0;
        int cnt7 = 0;
        int cnt8 = 0;
        int cnt9 = 0;
        int cnt10 = 0;
        int cnt11 = 0;
        int cmp;

        List<Let> all = findAllFromAviocompany(idAviocomp);    //svi letovi ove aviokompanije
        for (Let f : all){    //za svaki let
            List<FlightReservation> reservations = repositoryFR.findAllByFlightId(f.getId());  //sve rezervacije za taj let
            for(FlightReservation fr : reservations){     //izlistaj te rezervacije

                //jer baza dodaje 2 sata
                Calendar cal = Calendar.getInstance();
                cal.setTime(fr.getTime());
                cal.add(Calendar.HOUR, -2);
                Date reservationTime = cal.getTime();

                cmp = getZeroTimeDate(date).compareTo(getZeroTimeDate(reservationTime));
                if(cmp == 0){    //ako je let rezervisan danas
                    if(reservationTime.getHours() < 2 && reservationTime.getHours() >= 0){
                        cnt0 += 1;
                    }
                    if(reservationTime.getHours() < 4 && reservationTime.getHours() >= 2){
                        cnt1 += 1;
                    }
                    if(reservationTime.getHours() < 6 && reservationTime.getHours() >= 4){
                        cnt2 += 1;
                    }
                    if(reservationTime.getHours() < 8 && reservationTime.getHours() >= 6){
                        cnt3 += 1;
                    }
                    if(reservationTime.getHours() < 10 && reservationTime.getHours() >= 8){
                        cnt4 += 1;
                    }
                    if(reservationTime.getHours() < 12 && reservationTime.getHours() >= 10){
                        cnt5 += 1;
                    }
                    if(reservationTime.getHours() < 14 && reservationTime.getHours() >= 12){
                        cnt6 += 1;
                    }
                    if(reservationTime.getHours() < 16 && reservationTime.getHours() >= 14){
                        cnt7 += 1;
                    }
                    if(reservationTime.getHours() < 18 && reservationTime.getHours() >= 16){
                        cnt8 += 1;
                    }
                    if(reservationTime.getHours() < 20 && reservationTime.getHours() >= 18){
                        cnt9 += 1;
                    }
                    if(reservationTime.getHours()< 22 && reservationTime.getHours() >= 20){
                        cnt10 += 1;
                    }
                    if(reservationTime.getHours() <=23  && reservationTime.getHours() >= 22){
                        cnt11 += 1;
                    }
                }

            }
        }

        return new int[]{cnt0,cnt1,cnt2,cnt3,cnt4,cnt5,cnt6,cnt7,cnt8,cnt9,cnt10,cnt11};


    }

    private static Date subtractDays(int numDays, Date curr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(curr);
        cal.add(Calendar.DATE, 0-numDays);
        return cal.getTime();
    }

    private static Date subtractTwoHours(Date curr){
        Calendar cal = Calendar.getInstance();
        cal.setTime(curr);
        cal.add(Calendar.HOUR, -2);
        return cal.getTime();
    }

    public int[] findWeeklyReport(long idAviocomp) {
        int cnt0 = 0;
        int cnt1 = 0;
        int cnt2 = 0;
        int cnt3 = 0;
        int cnt4 = 0;
        int cnt5 = 0;
        int cnt6 = 0;
        Date today = new Date();
        int cmp;

        List<Let> all = findAllFromAviocompany(idAviocomp);    //svi letovi ove aviokompanije
        for (Let f : all){    //za svaki let
            List<FlightReservation> reservations = repositoryFR.findAllByFlightId(f.getId());  //sve rezervacije za taj let
            for(FlightReservation fr : reservations){     //izlistaj te rezervacije
                //jer baza dodaje 2 sata
                Date reservationTime = subtractTwoHours(fr.getTime());

                if(getZeroTimeDate(reservationTime).compareTo(getZeroTimeDate(subtractDays(0, today))) == 0){
                    cnt6 += 1;
                }
                else if(getZeroTimeDate(reservationTime).compareTo(getZeroTimeDate(subtractDays(1, today))) == 0){
                    cnt5 += 1;
                }
                else if(getZeroTimeDate(reservationTime).compareTo(getZeroTimeDate(subtractDays(2, today))) == 0){
                    cnt4 += 1;
                }
                else if(getZeroTimeDate(reservationTime).compareTo(getZeroTimeDate(subtractDays(3, today))) == 0){
                    cnt3 += 1;
                }
                else if(getZeroTimeDate(reservationTime).compareTo(getZeroTimeDate(subtractDays(4, today))) == 0){
                    cnt2 += 1;
                }
                else if(getZeroTimeDate(reservationTime).compareTo(getZeroTimeDate(subtractDays(5, today))) == 0){
                    cnt1 += 1;
                }
                else if(getZeroTimeDate(reservationTime).compareTo(getZeroTimeDate(subtractDays(6, today))) == 0){
                    cnt0 += 1;
                }
            }
        }

        return new int[]{cnt0,cnt1,cnt2,cnt3,cnt4,cnt5,cnt6};
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

    public int[] findMonthlyReport(long idAviocomp) {
        Date today = new Date();
        int year = today.getYear();
        int month = today.getMonth();
        if(month==1){
            month = 13;
        }
        YearMonth yearMonthObject = YearMonth.of(year,month-1);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        int counter = 0;
        int[] lista = new int[daysInMonth];

        List<Let> all = findAllFromAviocompany(idAviocomp);    //svi letovi ove aviokompanije
        for(int i = daysInMonth-1; i > 0; i--){
            for(Let f : all){
                //traze se sve rezervacije koje su ovog dana meseca i godine
                List<FlightReservation> reservations = repositoryFR.findAllByFlightId(f.getId());  //sve rezervacije za taj let
                for(FlightReservation fr : reservations){
                    Date reservationTime = subtractTwoHours(fr.getTime());
                    if(getZeroTimeDate(reservationTime).compareTo(getZeroTimeDate(subtractDays(i, today))) == 0) {
                        counter++;
                    }
                }
            }
            //kad se nadje koliko je rez u tom danu
            lista[daysInMonth-1-i] = counter;
            counter = 0;
        }
        return lista;
    }

    public Double findIncomeReport(long idAviocomp, String dates_str){
        String[] dates = (dates_str.substring(0,dates_str.length()-1)).split("\\+");

        Double count = 0.0;
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        Date prvi = new Date();
        Date drugi = new Date();

        try {
             prvi = df.parse(dates[0] + " 00:00:00");
            drugi = df.parse(dates[1] + " 00:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<Let> all = findAllFromAviocompany(idAviocomp);
            for(Let f : all) {
                List<FlightReservation> reservations = repositoryFR.findAllByFlightId(f.getId());  //sve rezervacije za taj let
                for (FlightReservation fr : reservations) {
                    if((getZeroTimeDate(subtractTwoHours(fr.getTime())).before(drugi) || getZeroTimeDate(subtractTwoHours(fr.getTime())).compareTo(drugi)==0) && (getZeroTimeDate(subtractTwoHours(fr.getTime())).after(prvi) || getZeroTimeDate(subtractTwoHours(fr.getTime())).compareTo(prvi)==0)){
                        count += fr.getPrice();
                    }

                }
            }
            return count;
    }
}
