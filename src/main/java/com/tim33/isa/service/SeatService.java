package com.tim33.isa.service;

import com.tim33.isa.model.Let;
import com.tim33.isa.model.Sediste;
import com.tim33.isa.repository.LetRepository;
import com.tim33.isa.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatService {
    @Autowired
    SeatRepository repository;
    @Autowired
    LetRepository repositoryF;

    public Sediste save(Sediste newSeat){
        // Manipulacija sedistem...
        return repository.save(newSeat);
    }

    public Sediste update(Sediste newSeat) {
        // Manipulacija sedistem...
        return repository.save(newSeat);
    }

    public List<Sediste> findAll() {
        return repository.findAll();
    }

    public Sediste findById(long id) {
        return repository.findById(id);
    }


    public Sediste[][] findAllFromPlane(long idFlight) {

        Let flight = repositoryF.findById(idFlight);

        long id = flight.getPlane().getId();
        int rows = flight.getPlane().getNumberOfRows();
        int seats_per_column = flight.getPlane().getSeatsPerColumn();
        int columns = flight.getPlane().getColumnNumber()*seats_per_column;
        Sediste[][] result = new Sediste[rows][columns + flight.getPlane().getColumnNumber() - 1];
        int column_counter = 0;


        //rasporedjujemo po nizu
        for(int i = 1; i <= rows; i ++){
            for(int j = 1; j<=columns + flight.getPlane().getColumnNumber() - 1;j++){
                if((j)%(seats_per_column+1)==0){   //ako je ovo prolaz,
                    result[i-1][j-1] = null;    //nema sedista
                }else {
                    column_counter++;
                    Sediste seat = findByNumberOfRowAndColumnNumberAndFlightId(i,column_counter,idFlight);
                    result[i-1][j-1] = seat;
                }

            }
            column_counter = 0;
        }

        return result;
    }

    private Sediste findByNumberOfRowAndColumnNumberAndFlightId(int i, int column_counter, long id) {
        return repository.findByNumberOfRowAndColumnNumberAndFlightId(i, column_counter, id);
    }


    public void deleteById(Long idDel) {
        repository.delete(findById(idDel));
    }

    public void generateSeats(Let flight) {
        int rows = flight.getPlane().getNumberOfRows(), columns = flight.getPlane().getColumnNumber();
        int seatsPerColumn = flight.getPlane().getSeatsPerColumn();


        for(int i = 1; i <= rows; i++ ){
            for(int j = 1 ; j <= columns*seatsPerColumn; j++){
                Sediste s = new Sediste();
                s.setReserved(false);
                s.setColumnNumber(j);
                s.setFlight(flight);
                s.setNumberOfRow(i);
                repository.save(s);
            }
        }
    }
}
