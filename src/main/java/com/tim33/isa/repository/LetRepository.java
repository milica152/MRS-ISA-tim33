package com.tim33.isa.repository;

import com.tim33.isa.dto.filter.FilterFlight;
import com.tim33.isa.model.Let;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LetRepository  extends JpaRepository<Let, Long> {
    List<Let> findAll();

    List<Let> findAllByAviokompanijaId(long idAviocomp);

    Let findById(long id);
    List<Let> findAllByAviokompanijaId(long idAviocomp);
}
