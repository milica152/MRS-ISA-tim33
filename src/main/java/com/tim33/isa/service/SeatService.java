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

        private Sediste findByNumberOfRowAndColumnNumberAndAvionId(int row_num, int column_num, long id){
            return repository.findByNumberOfRowAndColumnNumberAndAvionId(row_num, column_num, id);
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
                    Sediste seat = findByNumberOfRowAndColumnNumberAndAvionId(i,column_counter,id);
                    result[i-1][j-1] = seat;
                }

            }
            column_counter = 0;
        }

        return result;
    }


    public void deleteById(Long idDel) {
        repository.delete(findById(idDel));
    }
}
