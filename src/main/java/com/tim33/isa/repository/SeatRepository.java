package com.tim33.isa.repository;

import com.tim33.isa.model.Sediste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository  extends JpaRepository<Sediste, Long> {
    List<Sediste> findAll();
    Sediste findById(long id);
    List<Sediste> findAllByAvionId(long idPlane);
    Sediste findByNumberOfRowAndColumnNumberAndAvionId(int row_num, int column_num, long id);
}
