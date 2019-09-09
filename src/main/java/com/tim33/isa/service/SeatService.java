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



    private int getMaxRow(Let flight , List<Sediste> all){
        int max = 0;
        for(Sediste s : all){
            if(s.getNumberOfRow()>max){
                max = s.getNumberOfRow();
            }
        }
        return max;
    }


    public Sediste[][] findAllFromPlane(long idFlight) {
        Let flight = repositoryF.findById(idFlight);
        List<Sediste> all = repository.findAllByFlightId(idFlight);


        int rows = getMaxRow(flight, all);
        int seats_per_column = flight.getPlane().getSeatsPerColumn();
        int columns = flight.getPlane().getColumnNumber()*seats_per_column;
        Sediste[][] result = new Sediste[rows][columns + flight.getPlane().getColumnNumber() - 1];



        for(Sediste s : all){
            result[s.getNumberOfRow()-1][s.getColumnNumber() + (s.getColumnNumber()-1)/(seats_per_column)-1] = s;
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


    public Sediste findByRowAndColumnAndFlight(int row, int column, long idFlight) {
        return repository.findByNumberOfRowAndColumnNumberAndFlightId(row, column, idFlight);
    }
}
